package com.xsebe.yumao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.UserFaq;
import com.xsebe.yumao.service.UserFaqService;
import com.xsebe.yumao.utility.WebUtility;

/**
 * @Description:TODO
 * @date:2019年3月4日 上午10:34:34
 * @author:周伯通
 */
@RequestMapping("/faq")
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FaqController {

    @Autowired
    private UserFaqService userFaqService;

    @RequestMapping("/index.html")
    public String index(HttpServletRequest req) {
        List<UserFaq> list;
        try {
            list = userFaqService.list();
            req.setAttribute("list", list);
        } catch (YumaoException e) {
            e.printStackTrace();
        }
        return "faq/index";
    }

    @RequestMapping("/details")
    public String details(HttpServletRequest req) {
        String id = WebUtility.getParameter("id", CharEncoding.UTF_8, req);
        try {
            UserFaq userFaq = userFaqService.get(id);
            req.setAttribute("faq", userFaq);
        } catch (YumaoException e) {
            e.printStackTrace();
        }
        return "faq/details";

    }
}
