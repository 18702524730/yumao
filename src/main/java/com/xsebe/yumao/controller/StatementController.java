package com.xsebe.yumao.controller;

import com.google.gson.JsonObject;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Statement;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.service.StatementService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.ShiroUtility;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/statement")
public final class StatementController {

    @Autowired
    private StatementService statementService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrderService userOrderService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    @RequestMapping("/download.html")
    public String openDownload(final HttpServletRequest req, final HttpServletResponse resp) {
        InputStream input = null;
        try {
            OutputStream output = resp.getOutputStream();

            Subject currentUser = SecurityUtils.getSubject();


            if (ShiroUtility.isAuthenticated(currentUser)) {
                User loginUser = (User) currentUser.getPrincipal();

                // 2. 业务功能
                try {
                    UserRealnameAuth realnameAuth = userService.getRealnameAuth(loginUser);
                    if (false == userOrderService.hasServiceOpened(loginUser)) {// 如果没有“可用”的商品，去购买
//                        resp.getWriter().print("未找到声明函");
                        return "redirect:/service/open.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8) + "&step=1";
                    } else if (DataUtility.isNull(realnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != realnameAuth.getStatus())) {// 如果有商品，但是没有实名，去实名
//                        resp.getWriter().print("未找到声明函");
                        return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8);
                    }
                } catch (YumaoException ex) {
                    // TODO 数据库操作相关异常会到此
//            return "redirect:/status-code/500.html";
                }

                List<Statement> statements = statementService.getsByUserId(loginUser, loginUser.getId());
                if (statements != null && false == statements.isEmpty()) {
                    Statement statement = statements.get(0);

                    resp.setHeader("Content-Disposition", "attachment;filename=" + URLDeEncodeUtility.encode(statement.getFileName(), CharEncoding.UTF_8));

                    input = new ByteArrayInputStream(Hex.decode(statement.getFileContentHex()));
                    IOUtils.copy(input, output);
                } else {
                    resp.getWriter().print("未找到声明函");
                }
            } else {
                resp.getWriter().print("未登录系统");
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(input!=null){
                    input.close();
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../add.json
     */
    @PostMapping(path = "/add.json")
    public @ResponseBody JsonObject doAdd(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == ShiroUtility.isAuthenticated(currentUser)) {
            respJSON.addProperty("code", -2);
            respJSON.addProperty("message", "您还未登录");
            return respJSON;
        }
        User loginUser = (User) currentUser.getPrincipal();

        // 2. 业务功能
        try {
            UserRealnameAuth realnameAuth = userService.getRealnameAuth(loginUser);
            if (false == userOrderService.hasServiceOpened(loginUser)) {// 如果没有“可用”的商品，去购买
                respJSON.addProperty("code", -3);
                respJSON.addProperty("message", "您还未开通服务，请先开通");
                return respJSON;
//                return "redirect:/service/open.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8) + "&step=1";
            } else if (DataUtility.isNull(realnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != realnameAuth.getStatus())) {// 如果有商品，但是没有实名，去实名
                respJSON.addProperty("code", -4);
                respJSON.addProperty("message", "您还未实名");
                return respJSON;
//                return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8);
            }
        } catch (YumaoException ex) {
            // TODO 数据库操作相关异常会到此
//            return "redirect:/status-code/500.html";
        }


        // 2. 业务功能
        String stopName = reqJSON.getAsJsonPrimitive("stop_name").getAsString();
        String shopUrl = reqJSON.getAsJsonPrimitive("shop_url").getAsString();

        try {
            statementService.add(loginUser, loginUser, stopName, shopUrl);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }
        return respJSON;
    }

    @PostMapping("/update-url.json")
    public @ResponseBody JsonObject updateUrl(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            respJson.addProperty("code", 666);
            respJson.addProperty("message", "登录信息失效，请重新登录");
        }
        User user = (User) subject.getPrincipal();
        String url = JsonUtility.getString("shop_url", reqJson);

        try {
            statementService.updateShopUrl(user, url);
        } catch (YumaoException e) {
            respJson.addProperty("code", e.getHappenCode());
            respJson.addProperty("message", e.getHappenMessage());
        }
        
        return respJson;
    }
}