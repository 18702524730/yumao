package com.xsebe.yumao.service.impl;

import com.google.gson.JsonObject;
import com.xsebe.api.docking.faxin.fxservice.*;
import com.xsebe.api.docking.faxin.fxservice.excaption.FxServiceException;
import com.xsebe.api.docking.faxin.fxservice.response.APP100050Response;
import com.xsebe.api.docking.faxin.fxservice.response.APP100070Response;
import com.xsebe.api.docking.faxin.fxservice.response.UserIdAndPublicKeyResponse;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.service.FxServiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FxServiceServiceImpl implements FxServiceService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Value("#{fxService.uri}")
    private String fxServiceUri;
    @Value("#{fxService.app_id}")
    private String fxServiceAppId;
    @Value("#{fxService.key}")
    private String fxServiceKey;
    @Value("#{fxService.register_version}")
    private String fxServiceRegisterVersion;
    @Value("#{fxService.realname_auth_version}")
    private String fxServiceRealnameAuthVersion;
    @Value("#{fxService.submit_protect_version}")
    private String fxServiceSubmitProtectVersion;
    @Value("#{fxService.download_voucher_img_version}")
    private String fxServiceDownloadVoucherImgVersion;

    @Override
    public UserIdAndPublicKeyResponse register(final String businessId, final UserType userType, final String mobile, final String name,
            final CardType cardType, final String cardNo, final String enterpriseContactsName, final String enterpriseContactsPhone,
            final String enterpriseBusinessLicenseId) throws YumaoException {
        try {
            UserIdAndPublicKeyResponse response = FxServiceAPI.requestAPP100080(businessId, fxServiceUri, fxServiceRegisterVersion, fxServiceAppId,
                    fxServiceKey, userType, mobile, name, cardType.getType(), cardNo, enterpriseContactsName, enterpriseContactsPhone,
                    enterpriseBusinessLicenseId);
            return response;
        } catch (Throwable ex) {
            if (ex instanceof FxServiceException) {
                if (FxServiceConstants.RESULTCODE_E00018.equalsIgnoreCase(((FxServiceException) ex).getResultCode())) {
                    throw new YumaoException(111, "已存在该机构信用代码的第三方用户");
                }
                String resultDesc = ((FxServiceException) ex).getResultDesc();
                if (resultDesc.contains("已经提交实名认证信息，请勿重新提交")) {
                    // 目前暂未处理
                }
            }
            throw new YumaoException(666, "无法完成注册操作，请稍候再试", ex);
        }
    }

    @Override
    public void enterpriseRealnameAuth(final String businessId, final EnterpriseIdType idType, final String idCode, final String userId, final String inAcctNo,
            final String inAcctName, final String inAcctBankName, final String inAcctBankNo, final String idPic) throws YumaoException {
        try {
            FxServiceAPI.requestAPP100031(businessId, fxServiceUri, fxServiceRealnameAuthVersion, fxServiceAppId, fxServiceKey, idType, idCode, userId,
                    inAcctNo, inAcctName, inAcctBankName, inAcctBankNo, idPic);
        } catch (Throwable ex) {
            if (ex instanceof FxServiceException) {
                if (ex.getMessage().equals("已经提交实名认证信息，请勿重新提交")) {
                    throw new YumaoException(666, ex.getMessage());
                }
                if (ex.getMessage().equals("该对公账户已被绑定，请更换对公账户")) {
                    throw new YumaoException(666, ex.getMessage());
                }
            }
            throw new YumaoException(666, "无法完成注册操作，请稍候再试", ex);
        }
    }

    @Override
    public void enterpriseRealnameAuthValid(final String businessId, final String userId, final String validCode, final String orderAmount)
            throws YumaoException {
        try {
            FxServiceAPI.requestAPP100040(businessId, fxServiceUri, fxServiceRealnameAuthVersion, fxServiceAppId, fxServiceKey, userId, validCode, orderAmount);
        } catch (Throwable ex) {
            if (ex instanceof FxServiceException) {
                if (ex.getMessage().matches("您输入的验证码或汇款金额有误，请重新输入，您还有\\d+次校验机会")) {
                    throw new YumaoException(661, ex.getMessage());
                }
            }
            throw new YumaoException(666, "无法完成验证操作，请稍候再试", ex);
        }
    }

    @Override
    public APP100050Response submitProtect(final String businessId, final String fxFileKey, final String fxUserId, final String fxUserPublicKey,
            final String username, final String realname, final String idcard, final UserType userType, final String worksId, final String worksName,
            final String worksCategory, final String fileUri, final String forFaxinFileUri, final String fileName, final Date creationTime)
            throws YumaoException {
        try {
            return FxServiceAPI.requestAPP100050(businessId, fxServiceUri, fxServiceSubmitProtectVersion, fxServiceAppId, fxServiceKey, fxFileKey,
                    fxUserPublicKey, fxUserId, username, realname, idcard, userType, worksId, worksName, worksCategory, fileUri, forFaxinFileUri, fileName,
                    creationTime, null);
        } catch (Throwable ex) {
            throw new YumaoException(666, "无法保管，请稍候再试", ex);
        }
    }

    @Override
    public APP100070Response downloadVoucherImg(final String businessId, final List<String> evidIds) throws YumaoException {
        try {
            return FxServiceAPI.requestAPP100070(businessId, fxServiceUri, fxServiceDownloadVoucherImgVersion, fxServiceAppId, fxServiceKey, evidIds);
        } catch (Throwable ex) {

            throw new YumaoException(666, "无法下载图片，请稍候再试", ex);
        }
    }

    @Override
    public void verify(final JsonObject httpRequestJSON, final String sign) throws YumaoException {
        if (!new SignBuilder(httpRequestJSON, fxServiceKey).verify(sign)) {
            ERROR_LOG.warn("验签失败：{}", httpRequestJSON);
            throw new YumaoException(666, "验证失败");
        }
    }
}
