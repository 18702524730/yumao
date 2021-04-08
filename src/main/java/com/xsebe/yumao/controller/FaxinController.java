package com.xsebe.yumao.controller;

import com.google.gson.JsonObject;
import com.xsebe.api.docking.faxin.fxservice.log.BusinessLogger;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.OriginalWorks;
import com.xsebe.yumao.model.OriginalWorksVO;
import com.xsebe.yumao.model.UserFxServiceConfig;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.repository.OriginalWorksRepository;
import com.xsebe.yumao.service.FxServiceService;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.WebUtility;
import org.apache.commons.codec.CharEncoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/faxin")
public final class FaxinController {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);
    private static final Logger FX_SERVICE_BUSINESS_LOG = LogManager.getLogger(BusinessLogger.class);

    @Value("#{settings.upload_absolute_path}")
    private String uploadAbsolutePath;

    @Autowired
    private UserService userService;
    @Autowired
    private OriginalWorksService originalWorksService;
    @Autowired
    private OriginalWorksRepository originalWorksRepository;
    @Autowired
    private FxServiceService fxServiceService;

    private String getNewfilename() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @RequestMapping("/notify-url.html")
    public @ResponseBody JsonObject openNotifyUrl(final HttpServletRequest req) throws UnsupportedEncodingException {
        String evidId = WebUtility.getParameter("evidId", CharEncoding.UTF_8, req);
        String testifyEvidId = WebUtility.getParameter("testifyEvidId", CharEncoding.UTF_8, req);
        String appId = WebUtility.getParameter("appId", CharEncoding.UTF_8, req);
        String signTime = WebUtility.getParameter("signTime", CharEncoding.UTF_8, req);
        String sign = WebUtility.getParameter("sign", CharEncoding.UTF_8, req);
        
        FX_SERVICE_BUSINESS_LOG.info("来自【法信公正云】的回调： 作品保管通知");
        FX_SERVICE_BUSINESS_LOG.info("回调参数：evidId={}, testifyEvidId={}, appId={}, signTime={}, sign={}", evidId, testifyEvidId,
                appId, signTime, sign);

        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("resultCode", "E00000");
        respJSON.addProperty("resultDesc", "");

        if (DataUtility.isEmptyString(evidId, true) || DataUtility.isEmptyString(sign, true)) {
            respJSON.addProperty("resultCode", "E00012");
            respJSON.addProperty("resultDesc", "无效数据");
            return respJSON;
        }

        try {
            JsonObject httpRequestJSON = new JsonObject();
            httpRequestJSON.addProperty("evidId", evidId);
            httpRequestJSON.addProperty("testifyEvidId", testifyEvidId);
            httpRequestJSON.addProperty("appId", appId);
            httpRequestJSON.addProperty("signTime", signTime);
            fxServiceService.verify(httpRequestJSON, sign);
        } catch (YumaoException ex) {
            FX_SERVICE_BUSINESS_LOG.warn("验签失败：{},{}", ex.getHappenCode(), ex.getHappenMessage());
            respJSON.addProperty("resultCode", "E00012");
            respJSON.addProperty("resultDesc", "验签失败");
            return respJSON;
        }

        OriginalWorks originalWorks;
        try {
            originalWorks = originalWorksService.getByFxServiceConfig(evidId);
        } catch (YumaoException ex) {
            respJSON.addProperty("resultCode", "E00012");
            respJSON.addProperty("resultDesc", "");
            return respJSON;
        }
        if (DataUtility.isNull(originalWorks)) {
            respJSON.addProperty("resultCode", "E00012");
            respJSON.addProperty("resultDesc", "");
            return respJSON;
        }

        if (OriginalWorks.PROTECTION_STATUS_PROTECTING == originalWorks.getProtectionStatus()) {
            // 已经保管，直接跳过。
            return respJSON;
        }

        List<String> evidIds = new ArrayList<String>();
        evidIds.add(evidId);
        try {
            // 更新为保管状态
            INFO_LOG.info("作品保管成功更新保管状态开始："+evidId);
            originalWorksRepository.updateProtectingProtectionStatus(originalWorks.getId(), OriginalWorks.PROTECTION_STATUS_PROTECTING, new Date());
        } catch (Throwable ex) {
            ERROR_LOG.info("作品保管成功更新保管状态失败，需要重试："+evidId);
            respJSON.addProperty("resultCode", "E00012");
            respJSON.addProperty("resultDesc", "");
            ex.printStackTrace();
            return respJSON;
        }

        //下载保管函
        originalWorksService.addCredentialFile(originalWorks, evidIds);

//  下载保管函拧出来，定时器获取，定时器处理失败订单
//        try {
//            INFO_LOG.info("作品保管成功下载保管函：" + evidId);
//            APP100070Response response = fxServiceService.downloadVoucherImg(originalWorks.getId(), evidIds);
//            // 建立图片和OriginalWorks的关联关系
//            OriginalWorksCredentialFile originalWorksCredentialFile = new OriginalWorksCredentialFile();
//            originalWorksCredentialFile.setId(IDUtility.createID());
//            originalWorksCredentialFile.setOriginalWorks(originalWorks);
//            originalWorksCredentialFile.setName("credential.jpg");
//            String newFilename = getNewfilename();
//            originalWorksCredentialFile.setUri("credential-files/" + newFilename);
//            originalWorksCredentialFile.setCategory(OriginalWorksCredentialFile.CATEGORY_NOTARY);
//            originalWorksRepository.addCredentialFile(originalWorksCredentialFile);
//            byte[] credentialFileBytes = Base64.decodeBase64(response.getVoucherImg());
//            FileOutputStream out = null;
//            try {
//                out = new FileOutputStream(uploadAbsolutePath + "credential-files" + File.separatorChar + newFilename);
//                out.write(credentialFileBytes);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            } finally {
//                try {
//                    if (out != null) {
//                        out.close();
//                    }
//                } catch (Exception ee) {
//                    ee.printStackTrace();
//                }
//            }
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
        return respJSON;
    }

    // 公证云：0：未审核 1：待修改 2：打款中 4：打款成功 5：打款失败 6：实名失败 9：实名成功
    // 预猫：认证状态。1，认证成功；2，认证失败；3，未审核（企业）；4，待修改（企业）；5，打款中（企业）；6，打款成功（企业）；7，打款失败（企业）
    private int getStatus(final String auditState) {
        if ("0".equals(auditState)) {
            return UserRealnameAuth.STATUS_AUDIT_WAITING;
        }
        if ("1".equals(auditState)) {
            return UserRealnameAuth.STATUS_MODIFY_WAITING;
        }
        if ("2".equals(auditState)) {
            return UserRealnameAuth.STATUS_REMITTANCE_WAITING;
        }
        if ("4".equals(auditState)) {
            return UserRealnameAuth.STATUS_REMITTANCE_SUCCESS;
        }
        if ("5".equals(auditState)) {
            return UserRealnameAuth.STATUS_REMITTANCE_FAILURE;
        }
        if ("6".equals(auditState)) {
            return UserRealnameAuth.STATUS_AUTH_FAILURE;
        }
        if ("9".equals(auditState)) {
            return UserRealnameAuth.STATUS_AUTH_SUCCESS;
        }
        return UserRealnameAuth.STATUS_AUTH_FAILURE;
    }

    /**
     * 2.5.13. 企业用户实名认证审核通过回调通知接口。
     */
    @RequestMapping("/realname-auth/enterprise/audit-notify-url.html")
    public @ResponseBody JsonObject openEnterpriseRealnameAuthAuditNotifyUrl(final HttpServletRequest req) throws UnsupportedEncodingException {
        String userId = WebUtility.getParameter("userId", CharEncoding.UTF_8, req);
        String auditState = WebUtility.getParameter("auditState", CharEncoding.UTF_8, req);
        String failType = WebUtility.getParameter("failType", CharEncoding.UTF_8, req);
        String signTime = WebUtility.getParameter("signTime", CharEncoding.UTF_8, req);
        String sign = WebUtility.getParameter("sign", CharEncoding.UTF_8, req);
        
        FX_SERVICE_BUSINESS_LOG.info("来自【法信公正云】的回调： 企业用户实名认证审核回调通知");
        FX_SERVICE_BUSINESS_LOG.info("回调参数：userId={}, auditState={}, failType={}, signTime={}, sign={}", userId,
                auditState, failType, signTime, sign);

        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("resultCode", "E00000");
        respJSON.addProperty("resultDesc", "");

        if (DataUtility.isEmptyString(userId, true) || DataUtility.isEmptyString(auditState, true) || DataUtility.isEmptyString(sign, true)) {
            return respJSON;
        }
        // 验证签名是否合法
        try {
            JsonObject httpRequestJSON = new JsonObject();
            httpRequestJSON.addProperty("userId", userId);
            httpRequestJSON.addProperty("auditState", auditState);
            httpRequestJSON.addProperty("failType", failType);
            httpRequestJSON.addProperty("signTime", signTime);
            fxServiceService.verify(httpRequestJSON, sign);

        } catch (YumaoException e) {
            FX_SERVICE_BUSINESS_LOG.warn("验签失败：{},{}", e.getHappenCode(), e.getHappenMessage());
            return respJSON;
        }

        try {
            UserFxServiceConfig config = userService.getFxServiceConfigByFxUserId(userId);
            // 1. 签名验证
            // TODO 这里实现签名验证过程...
            // 2. 保存审核状态
            // 0：未审核 1：待修改 2：打款中 4：打款成功 5：打款失败 6：实名失败 9：实名成功
            userService.updateRealnameAuthEnterpriseAuditStatus(userId, getStatus(auditState), failType);
        } catch (YumaoException ex) {
        }

        return respJSON;
    }


    /**
     * 处理下载失败的存证函
     */
    @RequestMapping("/addCredentialFile.html")
    public void addCredentialFile() {
        List<OriginalWorksVO> originalWorksVOList = originalWorksRepository.selectAllFailureWorksForCredentialFile();
        if (originalWorksVOList != null && originalWorksVOList.size() > 0) {
            for (OriginalWorksVO originalWorksVO : originalWorksVOList) {
                OriginalWorks originalWorks = new OriginalWorks();
                copyProperties(originalWorksVO, originalWorks);
                List<String> evidIds = new ArrayList<String>();
                evidIds.add(originalWorksVO.getFxEvidId());
                originalWorksService.addCredentialFile(originalWorks, evidIds);
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}