package com.xsebe.yumao.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.DateFormatUtility;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.DateConstants;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.OriginalWorks;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.TortInfo;
import com.xsebe.yumao.model.TortLog;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.service.TortService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;

/**
 * @Description:侵权
 * @date:2019年2月15日 下午2:55:16
 * @author:周伯通
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/tort")
public class TortController {

    @Autowired
    private TortService tortService;

    @Autowired
    private OriginalWorksService originalWorksService;
    
    @Autowired
    private UserOrderService userOrderService;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/my-tort.html")
    public String myTort(final HttpServletRequest req) {
        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {// 未登录
            return "redirect:/login.html?return_mapping=" + URLDeEncodeUtility.encode("tort/my-tort.html", CharEncoding.UTF_8);
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        try {
            boolean hasServiceOpened = userOrderService.hasServiceOpened(current);
            UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
            if (false == hasServiceOpened) {// 如果没有“可用”的商品，去购买
                return "redirect:/service/open.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/my-save.html", CharEncoding.UTF_8) + "&step=1";
            } else if (DataUtility.isNull(realnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != realnameAuth.getStatus())) {// 如果有商品，但是没有实名，去实名
                return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/my-save.html", CharEncoding.UTF_8);
            }

            req.setAttribute("hasRealName", true);
            if (current.getUserType() == User.USER_TYPE_ENTERPRISE) {
                req.setAttribute("entName", realnameAuth.getEntName());
            }
            UserOrderServicePricePackage openOrderServicePricePackage = userOrderService.getOpenOrderServicePricePackage(current);
            if (DataUtility.isNotNull(openOrderServicePricePackage)) {
                req.setAttribute("endTime", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, openOrderServicePricePackage.getEndTime()));
            }
            req.setAttribute("hasServiceOpened", hasServiceOpened);
        } catch (YumaoException ex) {
            return "redirect:/status-code/500.html";
        }

        return "tort/my-tort";
    }

    @RequestMapping("/list.json")
    public @ResponseBody JsonObject list(@RequestBody JsonObject reqJSON) {
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
        String name = JsonUtility.getString("name", reqJSON);

        try {
            Pageable<TortInfo> tortInfoPage = tortService.getTortInfoPage(current.getId(), currentPageOffset, pageSize, name);
            List<TortInfo> currentPageList = tortInfoPage.getCurrentPageList();

            JsonArray rows = new JsonArray();
            JsonObject row;
            for (TortInfo tortInfo : currentPageList) {
                row = new JsonObject();
                row.addProperty("id", tortInfo.getId());
                row.addProperty("goods_url", tortInfo.getGoodsUrl());
                row.addProperty("works_name", tortInfo.getWorksName());
                row.addProperty("rights_protection_status", tortInfo.getRightsProtectionStatus());
                row.addProperty("rights_protection_status_str", TortInfo.getRights(tortInfo.getRightsProtectionStatus()));
                row.addProperty("submit_time", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD_HH_MM_SS, tortInfo.getSubmitTime()));
                row.add("tort_log", disposeTortLog(tortService.getTortLog(tortInfo.getId())));
                rows.add(row);
            }
            respJSON.add("rows", rows);
            respJSON.addProperty("total", tortInfoPage.getDataTotal());
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }

        return respJSON;
    }

    private JsonArray disposeTortLog(List<TortLog> tortLogs) {
        if (tortLogs.isEmpty() || tortLogs.size() <= 0) {
            return null;
        }
        JsonArray rows = new JsonArray();
        JsonObject row;
        for (TortLog tortLog : tortLogs) {
            row = new JsonObject();
            row.addProperty("operation_time", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD_HH_MM_SS, tortLog.getOperationTime()));
            row.addProperty("status", TortInfo.getRights(tortLog.getTortInfoStatus()));
            row.addProperty("modify_reason", null == tortLog.getModifyReason() ? "" : tortLog.getModifyReason());
            rows.add(row);
        }
        return rows;

    }

    @PostMapping(path = "/to-submit.json")
    public @ResponseBody JsonObject toSubmit(@RequestBody JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();

        // 初始化
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }

        String id = JsonUtility.getString("id", reqJSON);

        try {
            // 原创作品
            List<OriginalWorks> getsByTortInfoId = originalWorksService.getsByTortInfoId(id);
            JsonArray originalWorksIds = new JsonArray();
            for (OriginalWorks originalWorks : getsByTortInfoId) {
                originalWorksIds.add(originalWorks.getId());
            }
            respJSON.add("original_works_ids", originalWorksIds);
            // url
            TortInfo tortInfo = tortService.getTortInfo(id);
            respJSON.addProperty("goods_url", tortInfo.getGoodsUrl());
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }

        return respJSON;
    }

    @PostMapping(path = "/submit-tort-info.json")
    public @ResponseBody JsonObject submitTortInfo(@RequestBody JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();

        // 初始化
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }

        User current = (User) currentUser.getPrincipal();

        // 获取数据
        String tortInfoId = JsonUtility.getString("tort_info_id", reqJSON);
        String goodsUrl = JsonUtility.getString("goods_url", reqJSON);
        String orginalWorksIdsStr = JsonUtility.getString("original_works_ids", reqJSON);
        List<String> originalWorksIds = Arrays.asList(orginalWorksIdsStr.split(","));

        // 业务
        try {
            tortService.submitTortInfo(current, tortInfoId, goodsUrl, originalWorksIds);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }

        return respJSON;
    }

    @PostMapping(path = "/to-withdraw.json")
    public @ResponseBody JsonObject updateTortInfoStatus(@RequestBody JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();

        // 初始化
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }

        String id = JsonUtility.getString("id", reqJSON);

        try {
            tortService.toWithdraw(id);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }

        return respJSON;
    }

    @PostMapping(path = "/del-tort-info.json")
    public @ResponseBody JsonObject delTortInfo(@RequestBody JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();

        // 初始化
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            respJSON.addProperty("code", 1);
            respJSON.addProperty("message", "没有操作权限");
            return respJSON;
        }

        String id = JsonUtility.getString("id", reqJSON);

        try {
            tortService.delTortInfoById(id);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
        }

        return respJSON;
    }

    @RequestMapping(path = "/download-original-works.html")
    public void downloadOriginalWorks(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            String tortInfoId = req.getParameter("id");
            byte[] filesLoad = tortService.filesLoad(tortInfoId);
            InputStream inputStream = new ByteArrayInputStream(filesLoad);
            OutputStream outputStream = resp.getOutputStream();
            // 文件名乱码处理
            String userAgent = req.getHeader("User-Agent");
            String fileName = originalWorksService.getOriginalWorksName(tortInfoId) + ".zip";
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            resp.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            IOUtils.copy(inputStream, outputStream);
        } catch (YumaoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
