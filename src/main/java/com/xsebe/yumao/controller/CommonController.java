package com.xsebe.yumao.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.SessionConstants;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.service.CommonService;
import com.xsebe.yumao.service.impl.CommonServiceImpl.SmsCategory;
import com.xsebe.yumao.utility.ShiroUtility;
import com.xsebe.yumao.utility.VertifycodeUtility;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/common")
public final class CommonController {

    @Value("#{sms.verifycode_expiry_minutes}")
    private int smsVerifycodeExpiryMinutes;

    @Autowired
    private CommonService commonService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../download.html
     */
    @RequestMapping("/download.html")
    public void openDownload(@RequestParam("name") final String name, @RequestParam("src") final String src, final HttpServletRequest req,
            final HttpServletResponse resp) {
        TempAccountCategoryBundle.setAccountExper(req);

        resp.setCharacterEncoding(CharEncoding.UTF_8);

        String _name = URLDeEncodeUtility.decode(name, CharEncoding.UTF_8);
        _name = new String(_name.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8);
        String _src = URLDeEncodeUtility.decode(src, CharEncoding.UTF_8);
        _src = new String(_src.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8);

        resp.setHeader("Content-Disposition", "attachment;filename=" + URLDeEncodeUtility.encode(_name, CharEncoding.UTF_8));

        InputStream input = null;
        try {
            OutputStream output = resp.getOutputStream();

            URL url = new URL(_src);
            input = url.openStream();

            IOUtils.copy(input, output);
        } catch (Throwable ex) {
        } finally {
            try {
                input.close();
            } catch (Throwable ex) {
            }
        }
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    private JsonObject toSessionVerifycode(final String mobile, final String verifycode) {
        JsonObject sessionVerifycodeJSON = new JsonObject();
        sessionVerifycodeJSON.addProperty("mobile", mobile);
        sessionVerifycodeJSON.addProperty("verifycode", verifycode);
        sessionVerifycodeJSON.addProperty("create_time_millis", System.currentTimeMillis());
        return sessionVerifycodeJSON;
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../send-verifycode.json
     */
    @PostMapping(path = "/send-verifycode.json")
    public @ResponseBody JsonObject doSendVerifycode(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        Session session = ShiroUtility.getSession(currentUser);

        // 2. 业务功能
        String mobile = reqJSON.getAsJsonPrimitive("mobile").getAsString();
        try {
            ShiroUtility.removeSessionAttribute(session, SessionConstants.KEY_REGISTER_VERIFYCODE);
            String verifycode = VertifycodeUtility.randomLen(4, VertifycodeUtility.NUMBERS);
            commonService.sendSms(UUID.randomUUID().toString(), mobile, SmsCategory.VERIFYCODE, verifycode, String.valueOf(smsVerifycodeExpiryMinutes));
            ShiroUtility.setSessionAttribute(session, SessionConstants.KEY_REGISTER_VERIFYCODE, toSessionVerifycode(mobile, verifycode));
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }
        return respJSON;
    }
}