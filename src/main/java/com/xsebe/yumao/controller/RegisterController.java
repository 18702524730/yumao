package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.yumao.SessionConstants;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.ShiroUtility;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class RegisterController {

    @Autowired
    private UserService userService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../register.html
     */
    @RequestMapping("/register.html")
    public String openRegister(final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (ShiroUtility.isAuthenticated(currentUser)) {
            return "redirect:/index.html";
        }
        return "register";
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../register.json
     */
    @PostMapping(path = "/register.json")
    public @ResponseBody JsonObject doRegister(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        Session session = ShiroUtility.getSession(currentUser);
        JsonObject sessionVerifycodeJSON = (JsonObject) ShiroUtility.getSessionAttribute(session, SessionConstants.KEY_REGISTER_VERIFYCODE);

        // 2. 业务功能
        String username = JsonUtility.getString("username", reqJSON);
        String loginPassword = JsonUtility.getString("login_password", reqJSON);
        String affirmLoginPassword = JsonUtility.getString("affirm_login_password", reqJSON);
        int userType = JsonUtility.getInt("user_type", reqJSON);
        String mobile = JsonUtility.getString("mobile", reqJSON);
        String entUniCreditCode = JsonUtility.getString("ent_uni_credit_code", reqJSON);
        String entContactsRealname = JsonUtility.getString("ent_contacts_realname", reqJSON);
        String entContactsMobile = JsonUtility.getString("ent_contacts_mobile", reqJSON);
        String verifycode = JsonUtility.getString("mobile_verifycode", reqJSON);
//        String username = reqJSON.getAsJsonPrimitive("username").getAsString();
//        String loginPassword = reqJSON.getAsJsonPrimitive("login_password").getAsString();
//        String affirmLoginPassword = reqJSON.getAsJsonPrimitive("affirm_login_password").getAsString();
//        int userType = reqJSON.getAsJsonPrimitive("user_type").getAsInt();
//        String mobile = reqJSON.getAsJsonPrimitive("mobile").getAsString();
//        String entUniCreditCode = reqJSON.getAsJsonPrimitive("ent_uni_credit_code").getAsString();
//        String entContactsRealname = reqJSON.getAsJsonPrimitive("ent_contacts_realname").getAsString();
//        String entContactsMobile = reqJSON.getAsJsonPrimitive("ent_contacts_mobile").getAsString();
//        String verifycode = reqJSON.getAsJsonPrimitive("mobile_verifycode").getAsString();
//        boolean agreement = reqJSON.getAsJsonPrimitive("agreement").getAsBoolean();
        try {
            userService.register(sessionVerifycodeJSON, username, loginPassword, affirmLoginPassword, userType, mobile, entUniCreditCode, entContactsRealname,
                    entContactsMobile, verifycode, true);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }
        if (JsonUtility.getInt("code", respJSON) == 0) {
            // 注册成功，则直接登录
            try {
                userService.login(username, loginPassword, userType);
            } catch (Throwable ex) {
                // 出异常不要紧，让它自己登录。
            }
        }
        return respJSON;
    }
}