package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.CharEncoding;
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
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.service.OpinionService;
import com.xsebe.yumao.utility.ShiroUtility;
import com.xsebe.yumao.utility.WebUtility;

/**
 * @Description:用户意见反馈controller
 * @date: 2018年8月2日 下午4:15:40
 * @ClassName: 周伯通
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class OpinionController {
    @Autowired
    private OpinionService opinionService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    @RequestMapping("/opinion-feedback.html")
    public String openOpinion(@RequestParam(value = "return_mapping", required = false) final String returnMapping, final HttpServletRequest req) {

        String _returnMapping = WebUtility.getReturnMapping("opinion-feedback.html", returnMapping, "original-works/index.html");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == ShiroUtility.isAuthenticated(currentUser)) {
            return "redirect:/login.html?return_mapping="
                    + URLDeEncodeUtility.encode("opinion-feedback.html?return_mapping=" + returnMapping, CharEncoding.UTF_8);
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        req.setAttribute("return_mapping", _returnMapping);
        return "opinion-feedback";
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    @PostMapping(path = "/addOpinion.json")
    public @ResponseBody JsonObject addOpinion(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == ShiroUtility.isAuthenticated(currentUser)) {
            respJSON.addProperty("code", -1);
            respJSON.addProperty("message", "未登录");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        String opinionTitle = JsonUtility.getString("opinionTitle", reqJSON);
        String opinionContent = JsonUtility.getString("opinionContent", reqJSON);
        String opinionEmail = JsonUtility.getString("opinionEmail", reqJSON);
        String opinionTelphone = JsonUtility.getString("opinionTelphone", reqJSON);

        try {
            opinionService.addOpinion(current, opinionTitle, opinionContent, opinionEmail, opinionTelphone);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }
        return respJSON;
    }
}
