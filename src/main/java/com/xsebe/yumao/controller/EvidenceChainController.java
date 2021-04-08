package com.xsebe.yumao.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.xsebe.yumao.model.EvidChain;
import com.xsebe.yumao.model.EvidChainCredentialFile;
import com.xsebe.yumao.model.EvidChainNode;
import com.xsebe.yumao.model.OriginalWorksCredentialFile;
import com.xsebe.yumao.model.OriginalWorksFile;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.service.EvidChainCredentialFileService;
import com.xsebe.yumao.service.EvidChainNodeOriginalWorksService;
import com.xsebe.yumao.service.EvidChainNodeService;
import com.xsebe.yumao.service.EvidChainService;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/evid-chain")
public class EvidenceChainController {

    @Value("#{settings.upload_url}")
    private String uploadUrl;

    @Autowired
    private UserService userService;
    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private EvidChainService evidChainService;
    @Autowired
    private EvidChainCredentialFileService evidChainCredentialFileService;
    @Autowired
    private EvidChainNodeOriginalWorksService evidChainNodeOriginalWorksService;
    @Autowired
    private OriginalWorksService originalWorksService;
    @Autowired
    private EvidChainNodeService evidChainNodeService;

    @RequestMapping("/my-chain.html")
    public String myChain(final HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=evid-chain/my-chain.html";
        }
        User current = (User) subject.getPrincipal();

        req.setAttribute("user", current);
        try {
            boolean hasServiceOpened = userOrderService.hasServiceOpened(current);
            UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
            if (false == hasServiceOpened) {// 如果没有“可用”的商品，去购买
                return "redirect:/service/open.html?return_mapping=" + URLDeEncodeUtility.encode("evid-chain/my-chain.html", CharEncoding.UTF_8) + "&step=1";
            } else if (DataUtility.isNull(realnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != realnameAuth.getStatus())) {// 如果有商品，但是没有实名，去实名
                return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode("evid-chain/my-chain.html", CharEncoding.UTF_8);
            }
            if (DataUtility.isNotNull(realnameAuth) && realnameAuth.getStatus() == UserRealnameAuth.STATUS_AUTH_SUCCESS) {
                req.setAttribute("hasRealName", true);
                if (current.getUserType() == User.USER_TYPE_ENTERPRISE) {
                    req.setAttribute("entName", realnameAuth.getEntName());
                }
            }
            if (hasServiceOpened) {
                UserOrderServicePricePackage openOrderServicePricePackage = userOrderService.getOpenOrderServicePricePackage(current);
                if (DataUtility.isNotNull(openOrderServicePricePackage)) {
                    req.setAttribute("endTime", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, openOrderServicePricePackage.getEndTime()));
                }
            }
            req.setAttribute("hasServiceOpened", hasServiceOpened);
        } catch (YumaoException e) {
        }

        return "evid-chain/my-chain";
    }

    @RequestMapping("/detail.html")
    public String detail(final HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();
        String chainId = req.getParameter("id");
        if (false == subject.isAuthenticated()) {
            return "redirect:/login.html?return_mapping=evid-chain/detail.html?id=" + chainId;
        }
        User current = (User) subject.getPrincipal();

        try {
            EvidChain evidChain = evidChainService.get(chainId);
            req.setAttribute("evidChain", evidChain);
            req.setAttribute("percent", Float.valueOf(evidChain.getIntegrityPercent()).intValue() + "%");
            boolean hasServiceOpened = userOrderService.hasServiceOpened(current);
            UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
            if (DataUtility.isNotNull(realnameAuth) && realnameAuth.getStatus() == UserRealnameAuth.STATUS_AUTH_SUCCESS) {
                req.setAttribute("hasRealName", true);
                if (current.getUserType() == User.USER_TYPE_ENTERPRISE) {
                    req.setAttribute("entName", realnameAuth.getEntName());
                }
            }
            if (hasServiceOpened) {
                UserOrderServicePricePackage openOrderServicePricePackage = userOrderService.getOpenOrderServicePricePackage(current);
                if (DataUtility.isNotNull(openOrderServicePricePackage)) {
                    req.setAttribute("endTime", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, openOrderServicePricePackage.getEndTime()));
                }
            }
            req.setAttribute("hasServiceOpened", hasServiceOpened);

            List<EvidChainNode> nodes = evidChainNodeService.getByChainId(chainId);
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            for (EvidChainNode evidChainNode : nodes) {
                Map<String, Object> nodeMap = new HashMap<String, Object>();
                nodeMap.put("node", evidChainNode);
                List<OriginalWorksFile> files = originalWorksService.getFileByChainIdAndNodeId(chainId, evidChainNode.getEvidChainCategoryNode().getId());
                List<Map<String, Object>> fileMaps = new ArrayList<Map<String, Object>>();
                for (OriginalWorksFile originalWorksFile : files) {
                    Map<String, Object> fileMap = new HashMap<String, Object>();
                    fileMap.put("file", originalWorksFile);
                    fileMap.put(
                            "downUrl",
                            "common/download.html?name=" + URLDeEncodeUtility.encode(originalWorksFile.getName(), CharEncoding.UTF_8) + "&src="
                                    + URLDeEncodeUtility.encode(uploadUrl + originalWorksFile.getUri(), CharEncoding.UTF_8));
                    List<OriginalWorksCredentialFile> credentialFiles = originalWorksService.getCredentialFiles(originalWorksFile.getOriginalWorks().getId());
                    if (DataUtility.isNotNull(credentialFiles) && credentialFiles.size() > 0) {
                        OriginalWorksCredentialFile credentialFile = credentialFiles.get(0);
                        fileMap.put("credentialFileUrl", "common/download.html?name=" + URLDeEncodeUtility.encode(credentialFile.getName(), CharEncoding.UTF_8) + "&src="
                                + URLDeEncodeUtility.encode(uploadUrl + credentialFile.getUri(), CharEncoding.UTF_8));
                    }

                    fileMaps.add(fileMap);
                }
                nodeMap.put("nodeFileMaps", fileMaps);
                if (DataUtility.isNotNull(files) && files.size() > 0) {
                    nodeMap.put("hasFiles", true);
                }
                if (evidChainNode.getIsMasterPic() == 1) {
                    nodeMap.put("isMasterPic", true);
                }
                maps.add(nodeMap);
            }
            req.setAttribute("maps", maps);
        } catch (YumaoException e) {
        }

        return "evid-chain/chain-detail";
    }

    @RequestMapping("/download-credential-file.html")
    public void downloadCredentialFile(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            String chainId = req.getParameter("id");
            byte[] filesBytes = evidChainCredentialFileService.getFileBytesByEvidChainId(chainId);
            InputStream inputStream = new ByteArrayInputStream(filesBytes);
            OutputStream outputStream = resp.getOutputStream();
            // 文件名乱码处理
            String userAgent = req.getHeader("User-Agent");
            String fileName = evidChainCredentialFileService.getByEvidChainId(chainId).getName();
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            resp.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            IOUtils.copy(inputStream, outputStream);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/download-original-works.html")
    public void downloadOriginalWorks(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            String chainId = req.getParameter("id");
            byte[] filesBytes = evidChainService.getFilePackage(chainId);
            InputStream inputStream = new ByteArrayInputStream(filesBytes);
            OutputStream outputStream = resp.getOutputStream();
            // 文件名乱码处理
            String userAgent = req.getHeader("User-Agent");
            String fileName = evidChainService.get(chainId).getName() + ".zip";
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            resp.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            IOUtils.copy(inputStream, outputStream);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/my-chain-list.json")
    public @ResponseBody JsonObject myChainList(@RequestBody JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        JsonArray rowArray = new JsonArray();
        Pageable<EvidChain> page = new Pageable<EvidChain>();

        Subject subject = SecurityUtils.getSubject();
        User current = (User) subject.getPrincipal();

        int pageSize = JsonUtility.getInt("page_size", reqJson);
        int offSet = JsonUtility.getInt("current_page_offset", reqJson);
        String name = JsonUtility.getString("name", reqJson);

        try {
            page = evidChainService.getPage(current, name, offSet, pageSize);
            List<EvidChain> currentPageList = page.getCurrentPageList();
            for (EvidChain evidChain : currentPageList) {
                JsonObject row = new JsonObject();
                row.addProperty("id", evidChain.getId());
                row.addProperty("chain_name", evidChain.getName());
                row.addProperty("category", evidChain.getCategoryName());
                row.addProperty("create_time", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, evidChain.getCreatedTime()));
                row.addProperty("percent", Float.valueOf(evidChain.getIntegrityPercent()).intValue() + "%");
                EvidChainCredentialFile credentialFile = evidChainCredentialFileService.getByEvidChainId(evidChain.getId());
                if (DataUtility.isNotNull(credentialFile)) {
                    row.addProperty("has_credential_file", true);
                }
                List<OriginalWorksFile> fileByChainId = originalWorksService.getFileByChainId(evidChain.getId());
                if (DataUtility.isNotNull(fileByChainId) && fileByChainId.size() > 0) {
                    row.addProperty("has_original_file", true);
                }

                rowArray.add(row);
            }
        } catch (YumaoException e) {
        }
        respJson.add("rows", rowArray);
        respJson.addProperty("total", page.getDataTotal());
        return respJson;
    }

    @PostMapping("/add.json")
    public @ResponseBody JsonObject add(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            respJson.addProperty("code", 666);
            respJson.addProperty("message", "登录信息失效，请重新登录");
            return respJson;
        }
        User current = (User) subject.getPrincipal();

        String name = JsonUtility.getString("name", reqJson);
        String categoryId = "b6c22c2076b6422e9651912afd8257a0";
        String categoryName = "服装";

        try {
            evidChainService.add(current, name, categoryId, categoryName);
        } catch (YumaoException e) {
            respJson.addProperty("code", e.getHappenCode());
            respJson.addProperty("message", e.getHappenMessage());
        }

        return respJson;
    }

    @PostMapping("/delete.json")
    public @ResponseBody JsonObject delete(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            respJson.addProperty("code", 666);
            respJson.addProperty("message", "登录信息失效，请重新登录");
            return respJson;
        }
        String id = JsonUtility.getString("id", reqJson);
        try {
            evidChainService.delete(id);
        } catch (YumaoException e) {
            respJson.addProperty("code", e.getHappenCode());
            respJson.addProperty("message", e.getHappenMessage());
        }

        return respJson;
    }

    @PostMapping("/add-original-works.json")
    public @ResponseBody JsonObject addOriginalWorks(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            respJson.addProperty("code", 666);
            respJson.addProperty("message", "登录信息失效，请重新登录");
            return respJson;
        }

        String chainId = JsonUtility.getString("chian_id", reqJson);
        String originalWorksIds = JsonUtility.getString("original_works_ids", reqJson);
        String nodeId = JsonUtility.getString("chain_node_id", reqJson);
        String[] split = originalWorksIds.split(",");
        List<String> idList = new ArrayList<String>();
        for (String id : split) {
            idList.add(id);
        }
        try {
            evidChainService.addOriginalWorks(chainId, nodeId, idList);
        } catch (YumaoException e) {
            respJson.addProperty("code", e.getHappenCode());
            respJson.addProperty("message", e.getHappenMessage());
        }

        return respJson;
    }
    
    @PostMapping("/remove-original-works.json")
    public @ResponseBody JsonObject removeOriginalWorks(@RequestBody final JsonObject reqJson) {
        JsonObject respJson = new JsonObject();
        respJson.addProperty("code", 0);
        respJson.addProperty("message", "SUCCESS");

        Subject subject = SecurityUtils.getSubject();
        if (false == subject.isAuthenticated()) {
            respJson.addProperty("code", 666);
            respJson.addProperty("message", "登录信息失效，请重新登录");
            return respJson;
        }

        String originalWorksId = JsonUtility.getString("original_works_id", reqJson);
        String nodeId = JsonUtility.getString("chain_node_id", reqJson);
        try {
            evidChainService.removeOriginalWorks(nodeId, originalWorksId);
        } catch (YumaoException e) {
            respJson.addProperty("code", e.getHappenCode());
            respJson.addProperty("message", e.getHappenMessage());
        }

        return respJson;
    }
}
