package com.xsebe.yumao.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Dict;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserFxServiceConfig;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.service.DictService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.WebUtility;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class RealnameAuthController {

    @Autowired
    private DictService dictService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserOrderService userOrderService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../realname-auth.html
     */
    @RequestMapping("/realname-auth.html")
    public String openRealnameAuth(@RequestParam(value = "return_mapping", required = false) final String returnMapping,
            @RequestParam(value = "re_auth", required = false) final String reAuth, final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);

        // 默认跳转到开通成功页面
        String _returnMapping = WebUtility.getReturnMapping("realname-auth.html", returnMapping, "service/open-success.html");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=realname-auth.html";
        }
        User current = (User) currentUser.getPrincipal();
        try {
            if (false == userOrderService.hasServiceOpened(current)) {// 如果没有“可用”的商品，去购买
                return "redirect:/service/open.html?return_mapping=realname-auth.html&step=1";
            }
        } catch (YumaoException e) {
            return "redirect:/status-code/500.html";
        }

        // 2. 业务功能
        UserRealnameAuth userRealnameAuth;
        String url = "real-name";
        if (User.USER_TYPE_ENTERPRISE == current.getUserType()) {
            url = "real-name-enterprise";
        } else {
            url = "real-name";
        }
        try {
            userRealnameAuth = userService.getRealnameAuth(current);

            if (DataUtility.isNotNull(userRealnameAuth)) {
                // 实名认证成功，跳转到认证结果页面
                if (UserRealnameAuth.STATUS_AUTH_SUCCESS == userRealnameAuth.getStatus()) {
                    return "redirect:/" + _returnMapping;
                }

                if (User.USER_TYPE_ENTERPRISE == current.getUserType()) {
                    // 企业名称
                    try {
                        UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
                        if (DataUtility.isNotNull(realnameAuth)) {
                            if (UserRealnameAuth.STATUS_AUTH_SUCCESS == realnameAuth.getStatus()) {
                                req.setAttribute("entName", realnameAuth.getEntName());
                            }
                        }
                    } catch (YumaoException e) {
                    }
                    // 审核相关的都导航到实名认证流程页面
                    if (UserRealnameAuth.STATUS_AUDIT_WAITING == userRealnameAuth.getStatus() || (UserRealnameAuth.STATUS_REMITTANCE_WAITING == userRealnameAuth.getStatus())
                            || (UserRealnameAuth.STATUS_REMITTANCE_FAILURE == userRealnameAuth.getStatus())) {
                        return "redirect:/real-name-processing.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                    }
                    // 打款成功之后，需要用户填写打款金额和验证码。直接到验证页面
                    if (UserRealnameAuth.STATUS_REMITTANCE_SUCCESS == userRealnameAuth.getStatus()) {
                        return "redirect:/real-name-validate.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                    }
                    // 遇到企业待修改状态，表示用户需要自行修改内容，重新提交实名请求
                    if ("true".equalsIgnoreCase(reAuth)) {
                        ;
                    } else {
                        if ((UserRealnameAuth.STATUS_MODIFY_WAITING == userRealnameAuth.getStatus())) {
                            return "redirect:/real-name-processing.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                        }
                        // 实名认证失败，导航到结果页。展示实名失败，用户可以选择重新进行实名认证。
                        if ((UserRealnameAuth.STATUS_AUTH_FAILURE == userRealnameAuth.getStatus())) {
                            return "redirect:/real-name-processing.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                        }
                    }
                }

            }
            if (User.USER_TYPE_ENTERPRISE == current.getUserType()) {
                List<Dict> dicts = dictService.getsByType(UserRealnameAuth.ENT_BANK_NO_DICT_TYPE);
                req.setAttribute("entBankNoDicts", dicts);
            }
        } catch (YumaoException ex) {
        }
        req.setAttribute("return_mapping", _returnMapping);
        req.setAttribute("userType", current.getUserType());
        return url;
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../realname-auth-processing.html
     */
    @RequestMapping("/real-name-processing.html")
    public String openRealnameAuthProcessing(@RequestParam(value = "return_mapping", required = false) final String returnMapping, final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);

        String _returnMapping = WebUtility.getReturnMapping("real-name-processing.html", returnMapping, "realname-auth.html");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        // 个人用户没有实名认证流程，直接跳转到实名认证页，让实名认证页决策
        if (User.USER_TYPE_PERSONAL == current.getUserType()) {
            return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
        }
        UserRealnameAuth userRealnameAuth;
        try {
            userRealnameAuth = userService.getRealnameAuth(current);

            if (DataUtility.isNotNull(userRealnameAuth)) {
                int status = userRealnameAuth.getStatus();
                String caption;
                String message;
                if (UserRealnameAuth.STATUS_AUDIT_WAITING == status || (UserRealnameAuth.STATUS_REMITTANCE_WAITING == status)
                        || (UserRealnameAuth.STATUS_REMITTANCE_FAILURE == status)) {
                    switch (status) {
                    case UserRealnameAuth.STATUS_AUDIT_WAITING:
                        caption = "等待审核";
                        break;
                    case UserRealnameAuth.STATUS_REMITTANCE_WAITING:
                        caption = "等待审核";
                        break;
                    case UserRealnameAuth.STATUS_REMITTANCE_FAILURE:
                        caption = "打款失败";
                        break;
                    default:
                        caption = "等待审核";
                        break;
                    }
                    message = "审核时间为1-2个工作日，不含法定节假日，<a href='user/account.html' style='font-size: 16px; color: #4D72FE'>点击此处返回</a>";
                } else if (UserRealnameAuth.STATUS_AUTH_FAILURE == status || (UserRealnameAuth.STATUS_MODIFY_WAITING == status)) {
                    caption = "认证失败";
                    message = "原因：" + userRealnameAuth.getFailureCause();
                    req.setAttribute("failRealName", true);
                } else {
                    return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                }
                req.setAttribute("caption", caption);
                req.setAttribute("message", message);
            } else {
                // 没查询到，则企业用户尚未请求实名。跳转到实名认证页面
                return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
            }
        } catch (YumaoException ex) {
        }
        return "real-name-processing";
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../realname-auth-result.html
     */
    @RequestMapping("/realname-auth-result.html")
    public String openRealnameAuthResult(@RequestParam(value = "return_mapping", required = false) final String returnMapping, final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);

        String _returnMapping = WebUtility.getReturnMapping("realname-auth-result.html", returnMapping, "realname-auth.html");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        boolean authedSuccess = true;
        String caption = "认证成功";
        String message = "恭喜您，实名认证结果一致";
        try {
            UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);

            if (DataUtility.isNotNull(userRealnameAuth)) {
                if (UserRealnameAuth.STATUS_AUTH_SUCCESS == userRealnameAuth.getStatus()) {
                    ;
                } else if (UserRealnameAuth.STATUS_AUTH_FAILURE == userRealnameAuth.getStatus() || (UserRealnameAuth.STATUS_MODIFY_WAITING == userRealnameAuth.getStatus())) {
                    authedSuccess = false;
                    caption = "认证失败";
                    message = "原因：" + userRealnameAuth.getFailureCause();
                } else {
                    return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                }
            } else {
                return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
            }
        } catch (YumaoException ex) {
        }
        req.setAttribute("authed_success", authedSuccess);
        req.setAttribute("auth_caption", caption);
        req.setAttribute("auth_message", message);
        return "realname-auth-result";
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../realname-auth-validate.html
     */
    @RequestMapping("/real-name-validate.html")
    public String openRealnameAuthValidate(@RequestParam(value = "return_mapping", required = false) final String returnMapping, final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);

        String _returnMapping = WebUtility.getReturnMapping("real-name-validate.html", returnMapping, "realname-auth.html");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        return "real-name-verification";
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../realname-auth.json
     */
    @PostMapping(path = "/realname-auth.json")
    public @ResponseBody JsonObject doRealnameAuth(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        String realname = JsonUtility.getString("realname", reqJSON);
        String idcard = JsonUtility.getString("idcard", reqJSON);
        String bankCardNo = JsonUtility.getString("bank_card_no", reqJSON);
        String bankReservedMobile = JsonUtility.getString("bank_reserved_mobile", reqJSON);
        String entName = JsonUtility.getString("ent_name", reqJSON);
        String entBankName = JsonUtility.getString("ent_bank_name", reqJSON);
        String entBankNoDictValue = JsonUtility.getString("ent_bank_no_dict_value", reqJSON);
        String entBankPublicAccountNo = JsonUtility.getString("ent_bank_public_account_no", reqJSON);
        String entIdPicture = "";
        try {
            UserFxServiceConfig userFxServiceConfig = null;
            if (User.USER_TYPE_ENTERPRISE == current.getUserType()) {
                userFxServiceConfig = userService.realnameAuthQyBeforeRegister(current, entName);
            }

            userService.realnameAuth(current, realname, idcard, bankCardNo, bankReservedMobile, entName, entBankName, entBankNoDictValue, entBankPublicAccountNo, entIdPicture,
                    userFxServiceConfig);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        } catch (Throwable ex) {
            respJSON.addProperty("code", 666);
            respJSON.addProperty("message", "暂时无法实名，请联系管理员");
        }
        return respJSON;
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../realname-auth-qy.json
     */
    @RequestMapping("/realname-auth-qy.json")
    public @ResponseBody JsonObject doRealnameAuthQy(@RequestParam("ent_name") final String entName, @RequestParam("ent_bank_name") final String entBankName,
            @RequestParam("ent_bank_no_dict_value") final String entBankNoDictValue, @RequestParam("ent_bank_public_account_no") final String entBankPublicAccountNo,
            final MultipartHttpServletRequest req) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        Map<String, MultipartFile> fileMap = req.getFileMap();
        if (DataUtility.isNotNull(fileMap) && (false == fileMap.isEmpty())) {
            MultipartFile value;
            for (Entry<String, MultipartFile> file : fileMap.entrySet()) {
                value = file.getValue();
                byte[] bb;
                try {
                    bb = value.getBytes();
                } catch (IOException ex) {
                    respJSON.addProperty("code", 666);
                    respJSON.addProperty("message", "证件图片上传失败，请重新提交请求");
                    return respJSON;
                }
                try {
                    UserFxServiceConfig userFxServiceConfig = userService.realnameAuthQyBeforeRegister(current, entName);

                    userService.realnameAuth(current, "", "", "", "", entName, entBankName, entBankNoDictValue, entBankPublicAccountNo, Base64.encodeBase64String(bb),
                            userFxServiceConfig);
                } catch (YumaoException ex) {
                    respJSON.addProperty("code", ex.getHappenCode());
                    respJSON.addProperty("message", ex.getHappenMessage());
                } catch (Throwable ex) {
                    respJSON.addProperty("code", 666);
                    respJSON.addProperty("message", "暂时无法实名，请联系管理员");
                }
            }
        } else {
            respJSON.addProperty("code", 666);
            respJSON.addProperty("message", "请重新提交文件");
        }
        return respJSON;
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../realname-auth-qy-validate.json
     */
    @RequestMapping("/realname-auth-qy-validate.json")
    public @ResponseBody JsonObject doRealnameAuthQyValidate(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        String vcode = JsonUtility.getString("vcode", reqJSON);
        String amount = JsonUtility.getString("amount", reqJSON);
        try {
            userService.realnameAuthValidate(current, vcode, amount);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }
        return respJSON;
    }
}
