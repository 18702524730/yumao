package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.DateFormatUtility;
import com.xsebe.yumao.DateConstants;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.service.EvidChainService;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.service.TortService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private OriginalWorksService originalWorksService;
    @Autowired
    private TortService tortService;
    @Autowired
    private EvidChainService evidChainService;
    @Autowired
    private UserOrderService userOrderService;

    @RequestMapping("/index.html")
    public String openProfile(final HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();

        if (false == subject.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=user/account.html";
        }
        User current = (User) subject.getPrincipal();

        int userType = current.getUserType();
        UserRealnameAuth realnameAuth = null;
        try {
            realnameAuth = userService.getRealnameAuth(current);
        } catch (YumaoException e) {
        }
        if (DataUtility.isNotNull(realnameAuth)) {
            if (UserRealnameAuth.STATUS_AUTH_SUCCESS == realnameAuth.getStatus()) {
                req.setAttribute("realNameSuccess", true);
                if (User.USER_TYPE_ENTERPRISE == userType) {
                    req.setAttribute("entName", realnameAuth.getEntName());
                }
            }
        }
        req.setAttribute("userType", userType);

        long originalTotal;
        try {
            originalTotal = originalWorksService.getDataTotal(null, current, null);
        } catch (YumaoException e) {
            originalTotal = 0;
        }
        req.setAttribute("originalWorksNum", originalTotal);

        long tortTotal;
        try {
            tortTotal = tortService.getTortTotalByUser(current);
        } catch (YumaoException e) {
            tortTotal = 0;
        }
        req.setAttribute("tortNum", tortTotal);

        long evidChainTotal;
        try {
            evidChainTotal = evidChainService.getTotalByUser(current, null);
        } catch (Exception e) {
            evidChainTotal = 0;
        }
        req.setAttribute("evidChainNum", evidChainTotal);

        try {
            boolean hasServiceOpened = userOrderService.hasServiceOpened(current);
            if (hasServiceOpened) {
                UserOrderServicePricePackage openOrderServicePricePackage = userOrderService.getOpenOrderServicePricePackage(current);
                if (DataUtility.isNotNull(openOrderServicePricePackage)) {
                    req.setAttribute("endTime", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, openOrderServicePricePackage.getEndTime()));
                }
            }
            req.setAttribute("hasServiceOpened", hasServiceOpened);
        } catch (YumaoException e) {
        }

        return "profile/index";
    }
}
