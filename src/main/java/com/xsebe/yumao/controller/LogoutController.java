package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.ShiroUtility;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class LogoutController {

    @Autowired
    private UserService userService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../logout.html
     */
    @RequestMapping("/logout.html")
    public String openLogout(final HttpServletRequest req) {
        TempAccountCategoryBundle.clearAccountExper(req);

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (ShiroUtility.isAuthenticated(currentUser)) {
            try {
                userService.logout();
            } catch (YumaoException ex) {
                ex.printStackTrace();
            }
        }
        return "redirect:/login.html";
    }
}