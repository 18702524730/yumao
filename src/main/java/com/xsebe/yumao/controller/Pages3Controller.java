package com.xsebe.yumao.controller;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @deprecated 集成完成会删除掉
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/pages3")
public final class Pages3Controller {

    @RequestMapping("/{pathv}.html")
    public String openAllPages(@PathVariable("pathv") String pv) {
        return "pages3/" + pv;
    }
}