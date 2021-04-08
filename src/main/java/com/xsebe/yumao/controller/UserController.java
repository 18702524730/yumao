package com.xsebe.yumao.controller;

import com.google.gson.JsonObject;
import com.xsebe.api.utility.DataDesensitUtility;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.DateFormatUtility;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.yumao.DateConstants;
import com.xsebe.yumao.SessionConstants;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Statement;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.service.CommonService;
import com.xsebe.yumao.service.StatementService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.service.impl.CommonServiceImpl.SmsCategory;
import com.xsebe.yumao.utility.ShiroUtility;
import com.xsebe.yumao.utility.VertifycodeUtility;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @Description:TODO
 * @date: 2018年8月14日 上午8:49:56
 * @ClassName: 周伯通
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StatementService statementService;
    @Value("#{sms.verifycode_expiry_minutes}")
    private int smsVerifycodeExpiryMinutes;

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    private JsonObject toSessionVerifycode(final String username, final String mobile, final String verifycode) {
        JsonObject sessionVerifycodeJSON = new JsonObject();
        if (DataUtility.isNotEmptyString(username, true)) {
            sessionVerifycodeJSON.addProperty("username", username);
        }
        if (DataUtility.isNotEmptyString(mobile, true)) {
            sessionVerifycodeJSON.addProperty("mobile", mobile);
        }
        if (DataUtility.isNotEmptyString(verifycode, true)) {
            sessionVerifycodeJSON.addProperty("verifycode", verifycode);
        }
        sessionVerifycodeJSON.addProperty("create_time_millis", System.currentTimeMillis());
        return sessionVerifycodeJSON;
    }

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------
    @RequestMapping("/account.html")
    public String openAccount(final HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=user/account.html";
        }
        User current = (User) subject.getPrincipal();

        String url = "account/account-basic";
        req.setAttribute("user", current);
        UserRealnameAuth realnameAuth = null;
        try {
            realnameAuth = userService.getRealnameAuth(current);
        } catch (YumaoException e) {
        }
        if (DataUtility.isNotNull(realnameAuth) && realnameAuth.getStatus() == UserRealnameAuth.STATUS_AUTH_SUCCESS) {
            req.setAttribute("hasRealName", true);
            if (current.getUserType() == User.USER_TYPE_PERSONAL) {
                req.setAttribute("realName", realnameAuth.getRealname());
                req.setAttribute("idNo", DataDesensitUtility.getIdnumber(realnameAuth.getIdcard()));
            } else if (current.getUserType() == User.USER_TYPE_ENTERPRISE) {
                req.setAttribute("entName", realnameAuth.getEntName());
            }
        }
        if (current.getUserType() == User.USER_TYPE_ENTERPRISE) {
            url = "account/account-enterprise";
            current.setMobile(current.getEntContactsMobile());
            req.setAttribute("entContactsRealname", current.getEntContactsRealname());
            req.setAttribute("entUniCreditCode", DataDesensitUtility.getIdnumber(current.getEntUniCreditCode()));
        }
        try {
            boolean hasServiceOpened = userOrderService.hasServiceOpened(current);
            if (hasServiceOpened) {
                UserOrderServicePricePackage openOrderServicePricePackage = userOrderService.getOpenOrderServicePricePackage(current);
                if (DataUtility.isNotNull(openOrderServicePricePackage)) {
                    req.setAttribute("endTime", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, openOrderServicePricePackage.getEndTime()));
                }
            }
            req.setAttribute("hasServiceOpened", hasServiceOpened);
            List<Statement> getsByUserId = statementService.getsByUserId(current, current.getId());
            if(DataUtility.isNotNull(getsByUserId) && getsByUserId.size() > 0){
                req.setAttribute("hasStatement", true);
                req.setAttribute("statement", getsByUserId.get(0));
            }
        } catch (YumaoException e) {

        }

        return url;
    }
    
    /**
     * 
     * @Title: 进入忘记密码
     * @Description: TODO
     * @return String
     * @date 2018年8月13日上午9:33:34
     */
    @RequestMapping("/forgetPass.html")
    public String forgetPass() {
        return "forget/forgetPass";
    }

    /**
     * @Title: 获取验证码
     * @Description: TODO
     * @param reqJSON
     * @return JsonObject
     * @date 2018年8月14日上午11:29:19
     */
    @PostMapping(path = "/send-ververifycode-state.json")
    public @ResponseBody JsonObject getVerification(@RequestBody final JsonObject reqJSON) {
        // 初始化参数
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        Session session = ShiroUtility.getSession(currentUser);
        // 2.业务功能
        try {
            ShiroUtility.removeSessionAttribute(session, SessionConstants.KEY_FORGET_PASSWORD_VERIFYCODE);
            String verifycode = VertifycodeUtility.randomLen(4, VertifycodeUtility.NUMBERS);
            User user = userService.getMobileByUsername(JsonUtility.getString("account", reqJSON),
                    JsonUtility.getInt("userType", reqJSON), UUID.randomUUID().toString(),
                    verifycode, String.valueOf(smsVerifycodeExpiryMinutes));
            String mobileForDisplay = null;
            String mobile = null;
            if (User.USER_TYPE_PERSONAL == user.getUserType()) {
                mobile = user.getMobile();
                mobileForDisplay = user.getMobileForDisplay();
            } else {
                mobile = user.getEntContactsMobile();
                mobileForDisplay = user.getEntContactsMobileForDisplay();
            }
            respJson.addProperty("mobileForDisplay", mobileForDisplay);
            String username = JsonUtility.getString("account", reqJSON);
            ShiroUtility.setSessionAttribute(session, SessionConstants.KEY_FORGET_PASSWORD_VERIFYCODE, toSessionVerifycode(username, mobile, verifycode));
        } catch (YumaoException ex) {
            respJson.addProperty("code", ex.getHappenCode());
            respJson.addProperty("message", ex.getHappenMessage());
        }

        return respJson;
    }

    /**
     * @Title: 判断验证码是否正确
     * @Description: TODO
     * @param reqJson
     * @return JsonObject
     * @date 2018年8月14日上午11:29:39
     */
    @RequestMapping(path = "/forget-toupdate-pass.json")
    public @ResponseBody JsonObject toUpdatePass(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        Session session = ShiroUtility.getSession(currentUser);
        JsonObject sessionVerifycodeJSON = (JsonObject) ShiroUtility.getSessionAttribute(session, SessionConstants.KEY_FORGET_PASSWORD_VERIFYCODE);
        try {
            userService.toUpdateUserPass(sessionVerifycodeJSON, JsonUtility.getString("account", reqJson), JsonUtility.getString("verifycode", reqJson),
                    JsonUtility.getInt("user_type", reqJson));
        } catch (YumaoException ex) {
            respJson.addProperty("code", ex.getHappenCode());
            respJson.addProperty("message", ex.getHappenMessage());
        }
        return respJson;
    }

    /**
     * 修改手机号-获取验证码
     */
    @PostMapping("/send-update-mobile-code.json")
    public @ResponseBody JsonObject sendcode(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        try {
            ShiroUtility.removeSessionAttribute(session, SessionConstants.KEY_UPDATE_MOBILE_VERIFYCODE);
            String verifycode = VertifycodeUtility.randomLen(4, VertifycodeUtility.NUMBERS);
            String newMobile = JsonUtility.getString("new_mobile", reqJson);
            // 发送验证码
            commonService.sendSms(UUID.randomUUID().toString(), newMobile, SmsCategory.VERIFYCODE, verifycode, String.valueOf(smsVerifycodeExpiryMinutes));
            // 把相关信息放到session中
            ShiroUtility.setSessionAttribute(session, SessionConstants.KEY_UPDATE_MOBILE_VERIFYCODE, toSessionVerifycode(null, newMobile, verifycode));
        } catch (YumaoException ex) {
            respJson.addProperty("code", ex.getHappenCode());
            respJson.addProperty("message", ex.getHappenMessage());
        }

        return respJson;
    }

    /**
     * 修改手机号-验证验证码
     */
    @RequestMapping(path = "/check-update-mobile-code.json")
    public @ResponseBody JsonObject checkUpdateMobileCode(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        Session session = ShiroUtility.getSession(currentUser);
        JsonObject sessionVerifycodeJSON = (JsonObject) ShiroUtility.getSessionAttribute(session, SessionConstants.KEY_UPDATE_MOBILE_VERIFYCODE);
        String mobile = JsonUtility.getString("mobile", reqJson);
        String verifycode = JsonUtility.getString("verifycode", reqJson);
        try {
            // 验证验证码
            commonService.checkVerifycode(sessionVerifycodeJSON, mobile, verifycode);
        } catch (YumaoException ex) {
            respJson.addProperty("code", ex.getHappenCode());
            respJson.addProperty("message", ex.getHappenMessage());
        }
        return respJson;
    }

    /**
     * 修改手机号
     */
    @RequestMapping(path = "/update-mobile.json")
    public @ResponseBody JsonObject updateMobile(@RequestBody JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        Subject subject = SecurityUtils.getSubject();
        User current = (User) subject.getPrincipal();

        String mobile = JsonUtility.getString("mobile", reqJson);

        try {
            userService.updateMobile(current, mobile);
        } catch (YumaoException e) {
            respJson.addProperty("code", e.getHappenCode());
            respJson.addProperty("message", e.getHappenMessage());
        }

        return respJson;
    }

    /**
     * @Title: 进入修改密码
     * @Description: TODO
     * @return String
     * @date 2018年8月14日上午11:30:08
     */
    @RequestMapping(path = "/into-update.html")
    public String intoUpdate(@RequestParam(value = "return_mapping", required = false) final String returnMapping, final HttpServletRequest req) {

        // 1、操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        Session session = ShiroUtility.getSession(currentUser);
        JsonObject sessionVerifycodeJSON = (JsonObject) ShiroUtility.getSessionAttribute(session, SessionConstants.KEY_FORGET_PASSWORD_VERIFYCODE);

        // 2、业务功能
        if (DataUtility.isEmptyString(JsonUtility.getString("username", sessionVerifycodeJSON), true)
                || DataUtility.isEmptyString(JsonUtility.getString("mobile", sessionVerifycodeJSON), true)) {
            System.out.println(JsonUtility.getString("username", sessionVerifycodeJSON));
            System.out.println(JsonUtility.getString("mobile", sessionVerifycodeJSON));
            return "forget/forgetPass";
        } else {
            return "forget/updatePass";
        }
    }

    /**
     * @Title: 修改密码
     * @Description: TODO
     * @param reqJson
     * @return JsonObject
     * @date 2018年8月14日上午11:30:26
     */
    @PostMapping(path = "/update-password.json")
    public @ResponseBody JsonObject updatePass(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        // 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        Session session = ShiroUtility.getSession(currentUser);
        JsonObject sessionVerifycodeJSON = (JsonObject) ShiroUtility.getSessionAttribute(session, SessionConstants.KEY_FORGET_PASSWORD_VERIFYCODE);
        String username = JsonUtility.getString("username", sessionVerifycodeJSON);
        try {
            userService.updateUserPassByIdOrUsernameOrMobile(username, username, username, JsonUtility.getString("updatePassword", reqJson),
                    JsonUtility.getString("affirmUpdatePassword", reqJson));
        } catch (YumaoException ex) {
            respJson.addProperty("code", ex.getHappenCode());
            respJson.addProperty("message", ex.getHappenMessage());
        }
        return respJson;
    }

}
