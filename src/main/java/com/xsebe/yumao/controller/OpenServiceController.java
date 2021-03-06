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
     * // ???????????????????????????????????????????????????????????????????????????????????????????????? try { UserOrder userOrder =
     * userOrderService.getByOutTradeNo(outTradeNo); if
     * (DataUtility.isNotNull(userOrder) && UserOrder.STATUS_PAYMENT ==
     * userOrder.getStatus()) { return "redirect:/" + _returnMapping; } } catch
     * (Throwable ex) { } } else { ; } }
     * 
     * // 1. ?????????????????? Subject currentUser = SecurityUtils.getSubject(); if (false
     * == currentUser.isAuthenticated()) { return
     * "redirect:/login.html?return_mapping=" +
     * URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8); } User
     * current = (User) currentUser.getPrincipal();
     * 
     * // 2. ???????????? // ????????????????????????????????????????????? try { //????????????????????????????????????????????? if
     * (userOrderService.hasServiceOpened(current)){//????????????"??????"??????
     * UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);
     * if(DataUtility.isNull(userRealnameAuth) ||
     * (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())){
     * return "redirect:/realname-auth.html?return_mapping=" +
     * URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8); } return
     * "redirect:/" + _returnMapping; } } catch (YumaoException ex) { return
     * "redirect:/status-code/500.html"; } // ???????????????????????? try { UserRealnameAuth
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
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????
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

        // 2. ????????????
        // ?????????????????????????????????????????????
        try {
            // ?????????????????????????????????????????????
            if (userOrderService.hasServiceOpened(current)) {// ????????????"??????"??????
                UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);
                if (DataUtility.isNull(userRealnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())) {
                    return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8);
                }
                return "redirect:/" + _returnMapping;
            }
        } catch (YumaoException ex) {
            return "redirect:/status-code/500.html";
        }
        // ????????????????????????
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

        // 1. ??????????????????
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=service/open-success.html";
        }
        User current = (User) currentUser.getPrincipal();

        // 2. ????????????
        try {
            if (!userOrderService.hasServiceOpened(current)) {// ??????????????????
                return "redirect:/service/open.html";
            }
            UserRealnameAuth userRealnameAuth = userService.getRealnameAuth(current);
            if (DataUtility.isNull(userRealnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != userRealnameAuth.getStatus())) {// ???????????????
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
        sUnitPrice.append(" ???/");
        switch (byTimeType) {
        case ServicePricePackage.BY_TIME_TYPE_YEAR:
            sUnitPrice.append("???");
            break;
        case ServicePricePackage.BY_TIME_TYPE_MONTH:
            sUnitPrice.append("???");
            break;
        case ServicePricePackage.BY_TIME_TYPE_DAY:
            sUnitPrice.append("???");
            break;
        }
        return sUnitPrice.toString();
    }

    private String getPriceDisplay(final float price) {
        StringBuffer sPrice = new StringBuffer();
        sPrice.append(new DecimalFormat("0.00").format(price));
        sPrice.append(" ???");
        return sPrice.toString();
    }

    private String getValidTimesDisplay(final int byTimeType, final int validTimes) {
        StringBuffer priceDefinition = new StringBuffer();
        priceDefinition.append(validTimes);
        switch (byTimeType) {
        case ServicePricePackage.BY_TIME_TYPE_YEAR:
            priceDefinition.append(" ???");
            break;
        case ServicePricePackage.BY_TIME_TYPE_MONTH:
            priceDefinition.append(" ??????");
            break;
        case ServicePricePackage.BY_TIME_TYPE_DAY:
            priceDefinition.append(" ???");
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

        // 1. ??????????????????
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "??????????????????");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. ????????????
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

                // ?????????????????????????????????????????????????????????????????????
                if (TempAccountCategoryBundle.isAccountExper(req)) {
                    if ("166b87617e93471b895032e0265fa639".equalsIgnoreCase(servicePricePackage.getId())) {
                        // ??????????????????????????????????????????
                    } else {
                        continue;
                    }
                } else {
                    if ("166b87617e93471b895032e0265fa639".equalsIgnoreCase(servicePricePackage.getId())) {
                        // ????????????????????????????????????
                        // continue;
                    } else {
                        // ?????????999??????????????????????????????
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
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????
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

        // 2. ????????????
        // ??????????????????????????? ???????????????????????????
//        try {
//            // ?????????????????????????????????????????????
//            if (userOrderService.hasServiceOpenedOrOver(current)) {// ????????????"??????"??????
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