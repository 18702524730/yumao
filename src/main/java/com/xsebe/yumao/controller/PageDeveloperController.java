package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @deprecated 开发时候强制展示页面使用，开发完成可以删除掉。
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/for-dev")
public final class PageDeveloperController {

    @RequestMapping("/{url}.html")
    public String openView(@PathVariable("url") final String url, final HttpServletRequest req) {
        return url;
    }
}