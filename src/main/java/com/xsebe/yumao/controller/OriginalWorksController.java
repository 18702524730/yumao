package com.xsebe.yumao.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.DateFormatUtility;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.DateConstants;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.*;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.ShiroUtility;
import com.xsebe.yumao.utility.SpaceUtility;
import org.apache.commons.codec.CharEncoding;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/original-works")
public final class OriginalWorksController {

    @Value("#{settings.upload_url}")
    private String uploadUrl;

    @Autowired
    private OriginalWorksService originalWorksService;
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
     * .../original-works/index.html
     */
    @RequestMapping("/index.html")
    public String openIndex(final HttpServletRequest req) {
        // 1. ??????????????????
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {// ?????????
            return "redirect:/login.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8);
        }
        User current = (User) currentUser.getPrincipal();

        // 2. ????????????
        try {
            UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
            if (false == userOrderService.hasServiceOpened(current)) {// ?????????????????????????????????????????????
                return "redirect:/service/open.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8) + "&step=1";
            } else if (DataUtility.isNull(realnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != realnameAuth.getStatus())) {// ????????????????????????????????????????????????
                return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8);
            }
        } catch (YumaoException ex) {
            // TODO ????????????????????????????????????
            return "redirect:/status-code/500.html";
        }
        try {
            UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
            // ???????????????????????????
            if (DataUtility.isNotNull(realnameAuth) && UserRealnameAuth.STATUS_AUTH_SUCCESS == realnameAuth.getStatus()) {
                int userType = current.getUserType();
                String name;
                if (User.USER_TYPE_PERSONAL == userType) {
                    name = realnameAuth.getRealname();
                } else {
                    name = realnameAuth.getEntName();
                }
                req.setAttribute("name", name);
            }
        } catch (YumaoException e) {
        }
        return "original-works/index";
    }

    @RequestMapping("/my-save.html")
    public String mySave(final HttpServletRequest req) {
        // 1. ??????????????????
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {// ?????????
            return "redirect:/login.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/my-save.html", CharEncoding.UTF_8);
        }
        User current = (User) currentUser.getPrincipal();

        // 2. ????????????
        try {
            boolean hasServiceOpened = userOrderService.hasServiceOpened(current);
            UserRealnameAuth realnameAuth = userService.getRealnameAuth(current);
            if (false == hasServiceOpened) {// ?????????????????????????????????????????????
                return "redirect:/service/open.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/my-save.html", CharEncoding.UTF_8) + "&step=1";
            } else if (DataUtility.isNull(realnameAuth) || (UserRealnameAuth.STATUS_AUTH_SUCCESS != realnameAuth.getStatus())) {// ????????????????????????????????????????????????
                return "redirect:/realname-auth.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/my-save.html", CharEncoding.UTF_8);
            }
            
            req.setAttribute("hasRealName", true);
            String name;
            if (current.getUserType() == User.USER_TYPE_ENTERPRISE) {
                req.setAttribute("entName", realnameAuth.getEntName());
                name = realnameAuth.getEntName();
            }else{
                name = realnameAuth.getRealname();
            }
            req.setAttribute("name", name);
            UserOrderServicePricePackage openOrderServicePricePackage = userOrderService.getOpenOrderServicePricePackage(current);
            if (DataUtility.isNotNull(openOrderServicePricePackage)) {
                req.setAttribute("endTime", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, openOrderServicePricePackage.getEndTime()));
            }
            req.setAttribute("hasServiceOpened", hasServiceOpened);
        } catch (YumaoException ex) {
            return "redirect:/status-code/500.html";
        }
        return "original-works/my-save";
    }

    /**
     * @Title: ????????????
     * @Description: TODO
     * @return JsonObject
     * @date 2018???8???8?????????4:23:41
     */
    @RequestMapping("/getStatus")
    public @ResponseBody JsonObject getFileState() {

        // 1. ??????????????????
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");
        ;

        Subject currentUser = SecurityUtils.getSubject();
        if (false == ShiroUtility.isAuthenticated(currentUser)) {
            respJSON.addProperty("code", -1);
            respJSON.addProperty("message", "???????????????");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2.????????????
        JsonObject fileStatesJSON = new JsonObject();
        List<OriginalWorks> fileStates;
        try {
            fileStates = originalWorksService.getFileStates(current.getId());
        } catch (YumaoException ex) {
            return respJSON;
        }
        JsonObject fileStateJSON;
        for (OriginalWorks originalWorks : fileStates) {
            fileStateJSON = new JsonObject();
            fileStateJSON.addProperty("status", originalWorks.getProtectionStatus());

            List<OriginalWorksCredentialFile> credentialFile;
            try {
                credentialFile = originalWorksService.getCredentialFiles(originalWorks.getId());
                JsonArray credentialFileJSON = new JsonArray();
                if (credentialFile != null && false == credentialFile.isEmpty()) {
                    JsonObject fileJSON;
                    for (OriginalWorksCredentialFile file : credentialFile) {
                        if (OriginalWorksCredentialFile.CATEGORY_NOTARY == file.getCategory()) {
                            fileJSON = new JsonObject();
                            fileJSON.addProperty("name", "??????");
                            fileJSON.addProperty("download_uri", "common/download.html?name=" + URLDeEncodeUtility.encode(file.getName(), CharEncoding.UTF_8) + "&src="
                                    + URLDeEncodeUtility.encode(uploadUrl + file.getUri(), CharEncoding.UTF_8));
                            fileJSON.addProperty("url", uploadUrl + file.getUri());
                            credentialFileJSON.add(fileJSON);
                        }
                    }
                }
                fileStateJSON.add("credential_file", credentialFileJSON);
            } catch (YumaoException ex) {
                ;
            }

            fileStatesJSON.add(originalWorks.getId(), fileStateJSON);
        }

        // JsonArray fileStatesJSON = new JsonArray();
        // String userId = current.getId();
        // List<OriginalWorks> fileStates;
        // try {
        // fileStates = originalWorksService.getFileStates(userId);
        // } catch (YumaoException ex) {
        // return respJSON;
        // }
        // JsonObject fileStateJSON;
        // for (OriginalWorks originalWorks : fileStates) {
        // fileStateJSON = new JsonObject();
        // fileStateJSON.addProperty("id", originalWorks.getId());
        // fileStateJSON.addProperty("status",
        // originalWorks.getProtectionStatus());
        // fileStatesJSON.add(fileStateJSON);
        // }
        //
        respJSON.add("file_states", fileStatesJSON);
        return respJSON;
    }

    // ------------------------------------------------------------------------------------
    // Actions.
    // ------------------------------------------------------------------------------------

    private String getCategoryDisplay(final int category) {
        switch (category) {
        case 1:
            return "????????????";
        case 2:
            return "????????????";
        case 3:
            return "????????????";
        case 4:
            return "????????????";
        case 5:
            return "????????????";
        case 6:
            return "????????????";
        case 7:
            return "????????????";
        case 8:
            return "????????????";
        case 9:
            return "????????????????????????????????????????????????";
        case 10:
            return "??????";
        case 11:
            return "????????????";
        case 12:
            return "???????????????";
        case 13:
            return "??????";
        case 15:
            return "??????????????????";
        case 16:
            return "??????";
        }
        return "";
    }

    private String getProtectionStateDisplay(final int protectionState) {
        switch (protectionState) {
        case 1:
            return "?????????";
        case 2:
            return "?????????";
        case 3:
            return "????????????";
        }
        return "";
    }

    private static String getMimetypeByContentType(final String contentType) {
        int indexOf = contentType.indexOf(';');
        if (-1 != indexOf) {
            return contentType.substring(0, indexOf).trim();
        }
        return contentType.trim();
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../original-works/list.json
     */
    @RequestMapping("/list.json")
    public @ResponseBody JsonObject doGetOriginalWorks(@RequestBody final JsonObject reqJSON) {
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
        String idsStr = JsonUtility.getString("ids", reqJSON);
        String name = JsonUtility.getString("name", reqJSON);
        boolean addToNode = JsonUtility.getBoolean("add_to_node", reqJSON);
        List<String> ids = null;
        if (false == DataUtility.isEmptyString(idsStr, true)) {
            ids = Arrays.asList(idsStr.split(","));
        }

        try {
            Pageable<OriginalWorks> page = originalWorksService.getPage(name, current.getId(), ids, currentPageOffset, pageSize, addToNode);
            List<OriginalWorks> currentPageList = page.getCurrentPageList();

            JsonArray rowsJSON = new JsonArray();

            JsonObject rowJSON;
            for (OriginalWorks originalWorks : currentPageList) {
                rowJSON = new JsonObject();
                rowJSON.addProperty("id", originalWorks.getId());
                rowJSON.addProperty("name", originalWorks.getName());
                rowJSON.addProperty("category", getCategoryDisplay(originalWorks.getCategory()));
                rowJSON.addProperty("creation_time", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, originalWorks.getCreationTime()));
                rowJSON.addProperty("protection_status", getProtectionStateDisplay(originalWorks.getProtectionStatus()));
                rowJSON.addProperty("protection_status_value", originalWorks.getProtectionStatus());
                rowJSON.addProperty("uploading_time", DateFormatUtility.format(DateConstants.PATTERN_YYYY_MM_DD, originalWorks.getUploadingTime()));

                OriginalWorksFile file = originalWorks.getOriginalWorksFile();
                JsonObject fileJSON;
                JsonArray originalFileJSON = new JsonArray();
                if (file != null) {
                    // ????????????????????????????????????????????????????????????????????????
//                    OriginalWorksFile file = files.get(0);
                    fileJSON = new JsonObject();
                    fileJSON.addProperty("name", "??????");
                    fileJSON.addProperty("download_uri", "common/download.html?name=" +
                            URLDeEncodeUtility.encode(file.getName(), CharEncoding.UTF_8) + "&src="
                            + URLDeEncodeUtility.encode(uploadUrl + file.getUri(), CharEncoding.UTF_8));
                    originalFileJSON.add(fileJSON);
                }
                rowJSON.add("original_file", originalFileJSON);


                OriginalWorksCredentialFile credentialFile = originalWorks.getOriginalWorksCredentialFile();
                JsonArray credentialFileJSON = new JsonArray();
                if (credentialFile != null) {
//                    for (OriginalWorksCredentialFile file : credentialFile) {
                        if (OriginalWorksCredentialFile.CATEGORY_NOTARY == credentialFile.getCategory()) {
                            fileJSON = new JsonObject();
                            fileJSON.addProperty("name", "??????");
                            fileJSON.addProperty("download_uri", "common/download.html?name=" +
                                    URLDeEncodeUtility.encode(credentialFile.getName(), CharEncoding.UTF_8) + "&src="
                                    + URLDeEncodeUtility.encode(uploadUrl + credentialFile.getUri(), CharEncoding.UTF_8));
                            fileJSON.addProperty("url", uploadUrl + file.getUri());
                            credentialFileJSON.add(fileJSON);
                        }
//                    }
                }
                rowJSON.add("credential_file", credentialFileJSON);
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

    @RequestMapping("/fetch-spaces.json")
    public @ResponseBody JsonObject doFetchSpaces() {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. ??????????????????
        Subject currentUser = SecurityUtils.getSubject();
        if (false == ShiroUtility.isAuthenticated(currentUser)) {
            respJSON.addProperty("code", -1);
            respJSON.addProperty("message", "????????????????????????????????????");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. ????????????
        UserOrderServicePricePackage up;
        try {
            up = userOrderService.getOpenOrderServicePricePackage(current);
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
            return respJSON;
        }
        try {
            respJSON.addProperty("data_total", originalWorksService.getDataTotal(null, current, null));
        } catch (YumaoException ex) {
            respJSON.addProperty("code", ex.getHappenCode());
            respJSON.addProperty("message", ex.getHappenMessage());
            return respJSON;
        }
        respJSON.addProperty("total_space", SpaceUtility.getSpaceDisplay(up.getTotalSpace()));
        respJSON.addProperty("used_space", SpaceUtility.getSpaceDisplay(Float.toString(Float.parseFloat(up.getTotalSpace()) - Float.parseFloat(up.getFreeSpace()))));
        respJSON.addProperty("free_space", SpaceUtility.getSpaceDisplay(up.getFreeSpace()));
        return respJSON;
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../original-works/add.json
     */
    @RequestMapping("/add.json")
    public @ResponseBody JsonObject doAdd(@RequestParam("name") final String name, @RequestParam("category") final int category,
            @RequestParam("creation_time") final String sCreationTime, final MultipartHttpServletRequest req) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. ??????????????????
        Subject currentUser = SecurityUtils.getSubject();
        if (false == ShiroUtility.isAuthenticated(currentUser)) {
            respJSON.addProperty("code", -1);
            respJSON.addProperty("message", "????????????????????????????????????");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. ????????????
        Map<String, MultipartFile> fileMap = req.getFileMap();
        if (DataUtility.isNotNull(fileMap) && (false == fileMap.isEmpty())) {
            MultipartFile value;
            for (Entry<String, MultipartFile> file : fileMap.entrySet()) {
                value = file.getValue();
                byte[] bb;
                try {
                    bb = value.getBytes();
                } catch (IOException ex) {
                    respJSON.addProperty("code", 666);
                    respJSON.addProperty("message", "????????????????????????????????????????????????");
                    return respJSON;
                }
                try {
                    originalWorksService.add(current, name, category, sCreationTime, getMimetypeByContentType(value.getContentType()), value.getOriginalFilename(), bb);
                } catch (YumaoException ex) {
                    respJSON.addProperty("code", ex.getHappenCode());
                    respJSON.addProperty("message", ex.getHappenMessage());
                    return respJSON;
                }
            }
        } else {
            respJSON.addProperty("code", 666);
            respJSON.addProperty("message", "?????????????????????");
        }
        return respJSON;
    }

    /**
     * Finished!<br/>
     * <br/>
     * 
     * .../original-works/re-upload.json
     */
    @RequestMapping("/re-upload.json")
    public @ResponseBody JsonObject doReUpload(@RequestBody final JsonObject reqJSON) {
        JsonObject respJSON = new JsonObject();
        respJSON.addProperty("code", 0);
        respJSON.addProperty("message", "SUCCESS");

        // 1. ??????????????????
        Subject currentUser = SecurityUtils.getSubject();
        if (false == ShiroUtility.isAuthenticated(currentUser)) {
            respJSON.addProperty("code", -1);
            respJSON.addProperty("message", "????????????????????????????????????");
            return respJSON;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. ????????????
        String id = JsonUtility.getString("id", reqJSON);
        try {
            originalWorksService.reUpload(current, id);
        } catch (Throwable ex) {
            ex.printStackTrace();
            respJSON.addProperty("code", 666);
            respJSON.addProperty("message", "??????????????????????????????");
            return respJSON;
        }
        return respJSON;
    }
}
