package com.xsebe.yumao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @deprecated 临时加的，用于给体验账户提供套餐区分用的
 */
public class TempAccountCategoryBundle {

    /**
     * @deprecated 临时加的，用于给体验账户提供套餐区分用的
     */
    public static boolean isAccountExper(final HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        try {
            return (Boolean) session.getAttribute("experience");
        } catch (Throwable ex) {
        }
        return false;
    }

    /**
     * @deprecated 临时加的，用于给体验账户提供套餐区分用的
     */
    public static void setAccountExper(final HttpServletRequest req) {
        String accountExper = req.getParameter("account_exper");

        if ("true".equalsIgnoreCase(accountExper)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("experience", true);
        }
    }

    /**
     * @deprecated 临时加的，用于给体验账户提供套餐区分用的
     */
    public static void clearAccountExper(final HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        try {
            session.removeAttribute("experience");
        } catch (Throwable ex) {
        }
    }
}
