package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class AboutUsController {

    @RequestMapping("/about-us.html")
    public String openAboutUs(final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);

        return "about-us";
    }
}