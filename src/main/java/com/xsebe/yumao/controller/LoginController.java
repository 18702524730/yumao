package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;

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

import com.google.gson.JsonObject;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.ShiroUtility;
import com.xsebe.yumao.utility.WebUtility;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class LoginController {

    @Autowired
    private UserService userService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../login.html
     */
    @RequestMapping("/login.html")
    public String openLogin(@RequestParam(value = "return_mapping", required = false) final String returnMapping, final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);
        
        String _returnMapping = WebUtility.getReturnMapping("login.html", returnMapping, "user/account.html");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();

        // 2. 业务功能
        if (ShiroUtility.isAuthenticated(currentUser)) {
            return "redirect:/" + _returnMapping;
        }
        req.setAttribute("return_mapping", _returnMapping);
        return "login";
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../login.json
     */
    @PostMapping(path = "/login.json")
    public @ResponseBody JsonObject doLogin(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (ShiroUtility.isAuthenticated(currentUser)) {
            // respJSON.addProperty("code", -1);
            // respJSON.addProperty("message", "已登录，无需重复登录");
            return respJSON;
        }

        // 2. 业务功能
        String account = reqJSON.getAsJsonPrimitive("account").getAsString();
        String loginPassword = reqJSON.getAsJsonPrimitive("login_password").getAsString();
        int userType = reqJSON.getAsJsonPrimitive("user_type").getAsInt();
        try {
            userService.login(account, loginPassword, userType);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }
        return respJSON;
    }
}