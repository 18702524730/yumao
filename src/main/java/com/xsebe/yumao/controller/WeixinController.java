package com.xsebe.yumao.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/weixin")
public final class WeixinController {

    @RequestMapping("/mp.html")
    public String openMp() throws UnsupportedEncodingException {
        return "weixin/mp";
    }
}