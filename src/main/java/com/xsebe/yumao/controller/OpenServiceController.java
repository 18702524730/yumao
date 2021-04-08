package com.xsebe.yumao.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.*;
import com.xsebe.yumao.service.ServiceService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.SpaceUtility;
import com.xsebe.yumao.utility.WebUtility;
import org.apache.commons.codec.CharEncoding;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/service")
public final class OpenServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserOrderService userOrderService;

    // ------------------------------------------------------------------------------------
    // Views.
    // ------------------------------------------------------------------------------------

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../service/open.html
     */
    /*
     * @RequestMapping("/open.html") public String openOpen(@RequestParam(value
     * = "return_mapping", required = false) final String returnMapping,
     * 
     * @RequestParam(value = "step", required = false, defaultValue = "1") final
     * String step, @RequestParam(value = "ids", required = false, defaultValue
     * = "") final String sids,
     * 
     * @RequestParam(value = "out_trade_no", required = false) final String
     * outTradeNo, final HttpServletRequest req) {
     * TempAccountCategoryBundle.setAccountExper(req);
     *
     * String _returnMapping = WebUtility.getReturnMapping("open.html",
     * returnMapping, "original-works/index.html");
     * 
     * String _step = "1"; if (DataUtility.isNotEmptyString(step, true)) { _step
     * = step.trim(); if ("1".equals(step)) { ; } else if ("2".equals(step)) {
     * // 理论上支付宝支付后才会来到这里，如果是已支付就直接跳转到商品页。 try { UserOrder userOrder =
     * userOrderService.getByOutTradeNo(outTradeNo); if
     * (DataUtility.isNotNull(userOrder) && UserOrder.STATUS_PAYMENT ==
     * userOrder.getStatus()) { return "redirect:/" + _returnMapping; } } catch
     * (Throwable ex) { } } else { ; } }
     * 
     * // 1. 操作权限检查 Subject currentUser = SecurityUtils.getSubject(); if (false
     * == currentUser.isAuthenticated()) { return
     * "redirect:/login.html?return_mapping=" +
     * URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8); } User
     * current = (User) currentUser.getPrincipal();
     * 
     * // 2. 业务功能 // 如果已开通重定向到管理后台主页 try { //如果已经开通，没有实名，去实名 if
     * (userOrderService.hasServiceOpened(current)){//如果存在"有效"订单
     * UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);
     * if(DataUtility.isNull(userRealnameAuth) ||
     * (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())){
     * return "redirect:/realname-auth.html?return_mapping=" +
     * URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8); } return
     * "redirect:/" + _returnMapping; } } catch (YumaoException ex) { return
     * "redirect:/status-code/500.html"; } // 检查实名认证情况 try { UserRealnameAuth
     * userRealnameAuth = userService.getRealnameAuth(current); if
     * (DataUtility.isNull(userRealnameAuth) ||
     * (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())) {
     * return "redirect:/realname-auth.html?return_mapping=" +
     * URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8); } } catch
     * (YumaoException ex) { } req.setAttribute("return_mapping",
     * _returnMapping); req.setAttribute("step", _step); return "open-service";
     * }
     */
    @RequestMapping("/open.html")
    public String openService(@RequestParam(value = "return_mapping", required = false) final String returnMapping,
            @RequestParam(value = "step", required = false, defaultValue = "1") final String step,
                              @RequestParam(value = "out_trade_no", required = false) final String outTradeNo,
            final HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=service/open.html";
        }
        User current = (User) subject.getPrincipal();

        try {
            boolean hasServiceOpened = userOrderService.hasServiceOpened(current);
            if(hasServiceOpened){
                return "redirect:/user/account.html";
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        String _step = "1";
        String _returnMapping = WebUtility.getReturnMapping("open.html", returnMapping, "realname-auth.html");

        if (DataUtility.isNotEmptyString(step, true)) {
            _step = step.trim();
            if ("1".equals(step)) {
                ;
            } else if ("2".equals(step)) {
                // 理论上支付宝支付后才会来到这里，如果是已支付就直接跳转到实名认证页。
                try {
                    UserOrder userOrder = userOrderService.getByOutTradeNo(outTradeNo);
                    if (DataUtility.isNotNull(userOrder) && UserOrder.STATUS_PAYMENT == userOrder.getStatus()) {
                        return "redirect:/realname-auth.html";
                    }
                } catch (Throwable ex) {
                }
            } else {
                ;
            }
        }

        // 2. 业务功能
        // 如果已开通重定向到开通成功主页
        try {
            // 如果已经开通，没有实名，去实名
            if (userOrderService.hasServiceOpened(current)) {// 如果存在"有效"订单
                UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);
                if (DataUtility.isNull(userRealnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())) {
                    return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                }
                return "redirect:/" + _returnMapping;
            }
        } catch (YumaoException ex) {
            return "redirect:/status-code/500.html";
        }
        // 检查实名认证情况
        // try {
        // UserRealnameAuth userRealnameAuth =
        // userService.getRealnameAuth(current);
        // if (DataUtility.isNull(userRealnameAuth) ||
        // (UserRealnameAuth.STATUS_AUTH_SUCCESS !=
        // userRealnameAuth.getStatus())) {
        // return "redirect:/realname-auth.html?return_mapping=" +
        // URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
        // }
        // } catch (YumaoException ex) {
        // }

        int userType = current.getUserType();
        if (User.USER_TYPE_ENTERPRISE == userType) {
            try {
                UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
                if (DataUtility.isNotNull(realnameAuth)) {
                    if (UserRealnameAuth.STATUS_AUTH_SUCCESS == realnameAuth.getStatus()) {
                        req.setAttribute("entName", realnameAuth.getEntName());
                    }
                }
            } catch (YumaoException e) {
            }
        }

        req.setAttribute("return_mapping", _returnMapping);
        req.setAttribute("userType", userType);
        req.setAttribute("step", _step);

        return "open-service";
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../service/open-processing.html
     */
    @RequestMapping("/open-success.html")
    public String openOpenProcessing(final HttpServletRequest req) {
        TempAccountCategoryBundle.setAccountExper(req);

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=service/open-success.html";
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        try {
            if (!userOrderService.hasServiceOpened(current)) {// 没有开通服务
                return "redirect:/service/open.html";
            }
            UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);
            if (DataUtility.isNull(userRealnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())) {// 实名未成功
                return "redirect:/realname-auth.html";
            }
        } catch (YumaoException ex) {
            return "redirect:/status-code/500.html";
        }

        int userType = current.getUserType();
        if (User.USER_TYPE_ENTERPRISE == userType) {
            try {
                UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
                if (DataUtility.isNotNull(realnameAuth)) {
                    if (UserRealnameAuth.STATUS_AUTH_SUCCESS == realnameAuth.getStatus()) {
                        req.setAttribute("entName", realnameAuth.getEntName());
                    }
                }
            } catch (YumaoException e) {
            }
        }
        req.setAttribute("userType", userType);
        return "open-service-success";
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    private String getUnitPriceDisplay(final int byTimeType, final float unitPrice) {
        StringBuffer sUnitPrice = new StringBuffer();
        sUnitPrice.append(new DecimalFormat("0.00").format(unitPrice));
        sUnitPrice.append(" 元/");
        switch (byTimeType) {
        case ServicePricePackage.BY_TIME_TYPE_YEAR:
            sUnitPrice.append("年");
            break;
        case ServicePricePackage.BY_TIME_TYPE_MONTH:
            sUnitPrice.append("月");
            break;
        case ServicePricePackage.BY_TIME_TYPE_DAY:
            sUnitPrice.append("日");
            break;
        }
        return sUnitPrice.toString();
    }

    private String getPriceDisplay(final float price) {
        StringBuffer sPrice = new StringBuffer();
        sPrice.append(new DecimalFormat("0.00").format(price));
        sPrice.append(" 元");
        return sPrice.toString();
    }

    private String getValidTimesDisplay(final int byTimeType, final int validTimes) {
        StringBuffer priceDefinition = new StringBuffer();
        priceDefinition.append(validTimes);
        switch (byTimeType) {
        case ServicePricePackage.BY_TIME_TYPE_YEAR:
            priceDefinition.append(" 年");
            break;
        case ServicePricePackage.BY_TIME_TYPE_MONTH:
            priceDefinition.append(" 个月");
            break;
        case ServicePricePackage.BY_TIME_TYPE_DAY:
            priceDefinition.append(" 天");
            break;
        }
        return priceDefinition.toString();
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../service/price-package-list.json
     */
    @RequestMapping("/price-package-list.json")
    public @ResponseBody JsonObject doGetPricePackageList(@RequestBody final JsonObject reqJSON, final HttpServletRequest req) {
        JsonObject respJSON = new JsonObject();

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        int currentPageOffset = JsonUtility.getInt("current_page_offset", reqJSON);
        int pageSize = JsonUtility.getInt("page_size", reqJSON);

        try {
            Pageable<ServicePricePackage> page = serviceService.getPricePackages(currentPageOffset, pageSize);
            List<ServicePricePackage> currentPageList = page.getCurrentPageList();

            JsonArray rowsJSON = new JsonArray();

            JsonObject rowJSON;
            for (ServicePricePackage servicePricePackage : currentPageList) {
                rowJSON = new JsonObject();
                rowJSON.addProperty("id", servicePricePackage.getId());
                rowJSON.addProperty("service_name", servicePricePackage.getService().getName());
                rowJSON.addProperty("name", servicePricePackage.getName());
                rowJSON.addProperty("total_space", SpaceUtility.getSpaceDisplay(servicePricePackage.getTotalSpace()));
                rowJSON.addProperty("unit_price_value", servicePricePackage.getUnitPrice());
                rowJSON.addProperty("unit_price", getUnitPriceDisplay(servicePricePackage.getByTimeType(), servicePricePackage.getUnitPrice()));
                rowJSON.addProperty("valid_times", getValidTimesDisplay(servicePricePackage.getByTimeType(), servicePricePackage.getValidTimes()));
                rowJSON.addProperty("price_value", servicePricePackage.getPrice());
                rowJSON.addProperty("price", getPriceDisplay(servicePricePackage.getPrice()));

                // 这里临时添加的，以后去掉体验区分以下代码删除掉
                if (TempAccountCategoryBundle.isAccountExper(req)) {
                    if ("166b87617e93471b895032e0265fa639".equalsIgnoreCase(servicePricePackage.getId())) {
                        // 这里是体验的套餐，直接往下走
                    } else {
                        continue;
                    }
                } else {
                    if ("166b87617e93471b895032e0265fa639".equalsIgnoreCase(servicePricePackage.getId())) {
                        // 不需要体验套餐时去掉注释
                        // continue;
                    } else {
                        // 这里是999正式套餐，直接往下走
                    }
                }

                rowsJSON.add(rowJSON);
            }

            respJSON.add("rows", rowsJSON);
            respJSON.addProperty("total", page.getDataTotal());
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }
        return respJSON;
    }



    @RequestMapping("/renew.html")
    public String renewService(@RequestParam(value = "return_mapping", required = false) final String returnMapping,
                              @RequestParam(value = "step", required = false, defaultValue = "1") final String step,
                              @RequestParam(value = "out_trade_no", required = false) final String outTradeNo,
                              final HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=service/renew.html";
        }
        User current = (User) subject.getPrincipal();

        String _step = "1";
        String _returnMapping = WebUtility.getReturnMapping("renew.html", returnMapping, "user/account.html");

        if (DataUtility.isNotEmptyString(step, true)) {
            _step = step.trim();
            if ("1".equals(step)) {
                ;
            } else if ("2".equals(step)) {
                // 理论上支付宝支付后才会来到这里，如果是已支付就直接跳转到实名认证页。
                try {
                    UserOrder userOrder = userOrderService.getByOutTradeNo(outTradeNo);
                    if (DataUtility.isNotNull(userOrder) && UserOrder.STATUS_PAYMENT == userOrder.getStatus()) {
                        return "redirect:/user/account.html";
                    }
                } catch (Throwable ex) {
                }
            } else {
                ;
            }
        }

        // 2. 业务功能
        // 如果已开通到续费页 没有开通就到开通页
//        try {
//            // 如果已经开通，没有实名，去实名
//            if (userOrderService.hasServiceOpenedOrOver(current)) {// 如果存在"有效"订单
////                UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);
////                if (DataUtility.isNull(userRealnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())) {
////                    return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
////                }
//                return "redirect:/service/renew.html";
//            }
//        } catch (YumaoException ex) {
//            return "redirect:/status-code/500.html";
//        }

        int userType = current.getUserType();
        if (User.USER_TYPE_ENTERPRISE == userType) {
            try {
                UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
                if (DataUtility.isNotNull(realnameAuth)) {
                    if (UserRealnameAuth.STATUS_AUTH_SUCCESS == realnameAuth.getStatus()) {
                        req.setAttribute("entName", realnameAuth.getEntName());
                    }
                }
            } catch (YumaoException e) {
            }
        }

        req.setAttribute("return_mapping", _returnMapping);
        req.setAttribute("userType", userType);
        req.setAttribute("step", _step);

        return "renew-service";
    }



}