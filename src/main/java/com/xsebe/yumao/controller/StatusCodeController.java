package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/status-code")
public final class StatusCodeController {

    private static final String PREFIX_STATUS_CODE = "status-code/";

    @RequestMapping("/{code}.html")
    public String openStatusCode(@PathVariable("code") final String statusCode, final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);
        
        return PREFIX_STATUS_CODE + statusCode;
    }
}
