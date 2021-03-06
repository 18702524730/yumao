package com.xsebe.yumao.service.impl;

import com.xsebe.api.docking.faxin.fxservice.AESDeEncrypter;
import com.xsebe.api.docking.faxin.fxservice.UserType;
import com.xsebe.api.docking.faxin.fxservice.response.APP100050Response;
import com.xsebe.api.docking.faxin.fxservice.response.APP100070Response;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.PageUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.DateConstants;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.*;
import com.xsebe.yumao.repository.OriginalWorksRepository;
import com.xsebe.yumao.repository.UserRepository;
import com.xsebe.yumao.service.FxServiceService;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.utility.DataVerifyUtility;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OriginalWorksServiceImpl implements OriginalWorksService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Value("#{settings.upload_url}")
    private String uploadUrl;

    @Value("#{settings.upload_absolute_path}")
    private String uploadAbsolutePath;

    @Autowired
    private OriginalWorksRepository originalWorksRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private FxServiceService fxServiceService;

    @Transactional(/*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public Pageable<OriginalWorks> getPage(final String name, final String userId, final List<String> ids, final int currentPageOffset, final int pageSize, final boolean addToNode)
            throws YumaoException {
        long dataTotal;
        try {
            dataTotal = originalWorksRepository.getTotal(name, ids, userId, addToNode);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        List<OriginalWorks> currentPageList;
        try {
            currentPageList = originalWorksRepository.getsByLimit(name, ids, userId, currentPageOffset, pageSize, addToNode);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }

        Pageable<OriginalWorks> page = new Pageable<OriginalWorks>();
        page.setPageSize(pageSize);
        page.setPageTotal(PageUtility.computePageTotal(dataTotal, pageSize));
        page.setCurrentPageList(currentPageList);
        page.setDataTotal(dataTotal);
        return page;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public long getDataTotal(final String name, final User user, final List<String> ids) throws YumaoException {
        try {
            return originalWorksRepository.getTotal(name, ids, user.getId(), false);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public OriginalWorks getByFxServiceConfig(final String fxEvidId) throws YumaoException {
        try {
            return originalWorksRepository.getByFxServiceConfig(fxEvidId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<OriginalWorksFile> getFiles(final String id) throws YumaoException {
        try {
            return originalWorksRepository.getFiles(id);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<OriginalWorksCredentialFile> getCredentialFiles(final String id) throws YumaoException {
        try {
            return originalWorksRepository.getCredentialFiles(id);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    private String getNewfilename() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void add(final User user, final String name, final int category, final String sCreationTime, final String originalFileMimetype, final String originalFileName,
            final byte[] originalFileBytes) throws YumaoException {
        // ????????????
        String _name = DataVerifyUtility.notEmptyRequired(name, true, 666, "[????????????]????????????");
        String _sCreationTime = DataVerifyUtility.notEmptyRequired(sCreationTime, false, 666, "[????????????]????????????");

        // ????????????
        Date creationTime = DataVerifyUtility.dateRequired(_sCreationTime, DateConstants.PATTERN_YYYY_MM_DD, 666, "[????????????]????????????");

        // ????????????????????????
        long countByName = originalWorksRepository.countByName(_name, user.getId());
        if (countByName > 0) {
            throw new YumaoException(666, "????????????????????????");
        }
        // ??????????????????
        int len = originalFileBytes.length;
        UserOrderServicePricePackage uu = userOrderService.getOpenOrderServicePricePackage(user);
        if (DataUtility.isNull(uu)) {
            throw new YumaoException(666, "?????????????????????????????????");
        }
        long freeSpace = Long.parseLong(uu.getFreeSpace());
        if (freeSpace <= 0) {
            throw new YumaoException(666, "????????????????????????????????????");
        }

        // 1. ????????????????????????
        OriginalWorks originalWorks = new OriginalWorks();
        originalWorks.setId(IDUtility.createID());
        originalWorks.setName(_name);
        originalWorks.setCategory(category);
        originalWorks.setType(User.USER_TYPE_PERSONAL == user.getUserType() ? OriginalWorks.TYPE_INDIVIDUAL : OriginalWorks.TYPE_LEGAL_PERSON);
        originalWorks.setCreationTime(creationTime);
        originalWorks.setProtectionStatus(OriginalWorks.PROTECTION_STATUS_UPLOADING);
        originalWorks.setUploadingTime(new Date());
        originalWorks.setProtectionTime(null);
        try {
            originalWorksRepository.add(originalWorks);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        // ??????????????????????????????????????????
        try {
            originalWorksRepository.addUserLink(user.getId(), originalWorks.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        // 2. ??????????????????
        OriginalWorksFile originalWorksFile = new OriginalWorksFile();
        originalWorksFile.setId(IDUtility.createID());
        originalWorksFile.setOriginalWorks(originalWorks);
        originalWorksFile.setName(originalFileName);
        String newFilename = getNewfilename();
        originalWorksFile.setUri("original-files/" + newFilename);
        String fileKey = UUID.randomUUID().toString().replace("-", "");
        originalWorksFile.setFxFileKey(fileKey);
        try {
            originalWorksRepository.addFile(originalWorksFile);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(uploadAbsolutePath + "original-files" + File.separatorChar + newFilename);
            out.write(originalFileBytes);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        try {
            out = new FileOutputStream(uploadAbsolutePath + "for-faxin-original-files" + File.separatorChar + newFilename);
            out.write(AESDeEncrypter.encryptAES4File(fileKey.getBytes(CharEncoding.UTF_8), originalFileBytes));
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
        } finally {
            try {
                if(out!=null){
                    out.close();
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        // ??????????????????
        submitProtection(user, originalWorks, originalWorksFile);

        userOrderService.updateFreeSpace(uu.getId(), Long.toString(freeSpace - len));
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public Pageable<OriginalWorks> getPageForTask(final int protectionStatus, final int currentPageOffset, final int pageSize) throws YumaoException {
        long dataTotal;
        try {
            dataTotal = originalWorksRepository.getTotalForTask(protectionStatus);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        List<OriginalWorks> currentPageList;
        try {
            currentPageList = originalWorksRepository.getsByLimitForTask(protectionStatus, currentPageOffset, pageSize);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }

        Pageable<OriginalWorks> page = new Pageable<OriginalWorks>();
        page.setPageSize(pageSize);
        page.setPageTotal(PageUtility.computePageTotal(dataTotal, pageSize));
        page.setCurrentPageList(currentPageList);
        page.setDataTotal(dataTotal);
        return page;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void submitProtection(final User user, final OriginalWorks originalWorks, final OriginalWorksFile originalWorksFile) throws YumaoException {
        UserFxServiceConfig userFxServiceConfig;
        try {
            userFxServiceConfig = userRepository.getFxServiceConfig(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }

        // 3. ???????????????????????????
        UserRealnameAuth userRealnameAuth;
        try {
            userRealnameAuth = userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        String name;
        UserType userType;
        if (User.USER_TYPE_PERSONAL == user.getUserType()) {
            name = userRealnameAuth.getRealname();
            userType = UserType.personal;
        } else {
            name = userRealnameAuth.getEntName();
            userType = UserType.enterprise;
        }
        try {
            APP100050Response response = fxServiceService.submitProtect(originalWorks.getId(), originalWorksFile.getFxFileKey(), userFxServiceConfig.getFxUserId(),
                    userFxServiceConfig.getFxUserPublicKey(), user.getUsername(), name, userRealnameAuth.getIdcard(), userType, originalWorks.getId(), originalWorks.getName(),
                    Integer.toString(originalWorks.getCategory()), uploadUrl + originalWorksFile.getUri(), uploadUrl + "for-faxin-" + originalWorksFile.getUri(),
                    originalWorksFile.getName(), originalWorks.getCreationTime());

            OriginalWorksFxServiceConfig originalWorksFxServiceConfig = originalWorksRepository.getFxServiceConfig(originalWorks.getId());
            if (DataUtility.isNull(originalWorksFxServiceConfig)) {
                originalWorksFxServiceConfig = new OriginalWorksFxServiceConfig();
                originalWorksFxServiceConfig.setId(IDUtility.createID());
                originalWorksFxServiceConfig.setOriginalWorks(originalWorks);
                originalWorksFxServiceConfig.setFxEvidId(response.getEvidId());
                originalWorksFxServiceConfig.setFxWorkId(response.getWorkId());
                try {
                    originalWorksRepository.addFxServiceConfig(originalWorksFxServiceConfig);
                } catch (Throwable ex) {
                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                    throw new YumaoException(666, "???????????????????????????", ex);
                }
            } else {
                originalWorksFxServiceConfig.setFxEvidId(response.getEvidId());
                originalWorksFxServiceConfig.setFxWorkId(response.getWorkId());
                try {
                    originalWorksRepository.updateFxServiceConfig(originalWorksFxServiceConfig);
                } catch (Throwable ex) {
                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                    throw new YumaoException(666, "???????????????????????????", ex);
                }
            }
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "??????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateUploadingFailureProtectionStatus(final String id) throws YumaoException {
        OriginalWorks originalWorks;
        try {
            originalWorks = originalWorksRepository.get(id);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        if (DataUtility.isNull(originalWorks)) {
            throw new YumaoException(666, "?????????????????????");
        }

        try {
            originalWorksRepository.updateUploadingFailureProtectionStatus(id, OriginalWorks.PROTECTION_STATUS_UPLOADING_FAILURE, new Date());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void reUpload(final User user, final String id) throws YumaoException {
        OriginalWorks originalWorks;
        try {
            originalWorks = originalWorksRepository.get(id);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        if (DataUtility.isNull(originalWorks)) {
            throw new YumaoException(666, "?????????????????????");
        }
        // ??????????????????????????????
        try {
            originalWorksRepository.updateUploadingProtectionStatus(originalWorks.getId(), OriginalWorks.PROTECTION_STATUS_UPLOADING, new Date());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }

        // ????????????????????????
        List<OriginalWorksFile> originalWorksFiles = originalWorksRepository.getFiles(originalWorks.getId());
        OriginalWorksFile originalWorksFile = originalWorksFiles.get(0);
        submitProtection(user, originalWorks, originalWorksFile);
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<OriginalWorks> getFileStates(final String userId) throws YumaoException {
        try {
            return originalWorksRepository.getFileStates(userId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<OriginalWorks> getList(final List<String> ids) throws YumaoException {
        try {
            return originalWorksRepository.getList(ids);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<OriginalWorks> getsByTortInfoId(String tortInfoId) throws YumaoException {
        try {
            return originalWorksRepository.getByTortInfoId(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public String getOriginalWorksName(final String tortInfoId) throws YumaoException {
        List<OriginalWorksFile> listByTortInfoId = listByTortInfoId(tortInfoId);
        StringBuffer worksName = new StringBuffer();
        List<OriginalWorks> list = getListByTortInfoId(tortInfoId);
        for (OriginalWorks originalWorks : list) {
            worksName.append(originalWorks.getName());
            worksName.append('???');
        }
        worksName.deleteCharAt(worksName.length() - 1);
        if (listByTortInfoId.size() - 5 > 0) {
            worksName.append("???" + (listByTortInfoId.size() - 5) + "?????????");
        }
        return worksName.toString();
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<OriginalWorksFile> listByTortInfoId(final String tortInfoId) throws YumaoException {
        try {
            return originalWorksRepository.listFileByTortInfoId(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<OriginalWorks> getListByTortInfoId(final String tortInfoId) throws YumaoException {
        try {
            return originalWorksRepository.getListByTortInfoId(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }


    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void addCredentialFile(OriginalWorks originalWorks, List<String> evidIds) {
        try {
            INFO_LOG.info("????????????????????????????????????" + evidIds);
            APP100070Response response = fxServiceService.downloadVoucherImg(originalWorks.getId(), evidIds);

            String newFilename = getNewfilename();
            byte[] credentialFileBytes = Base64.decodeBase64(response.getVoucherImg());
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(uploadAbsolutePath + "credential-files" + File.separatorChar + newFilename);
                out.write(credentialFileBytes);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

            // ???????????????OriginalWorks???????????????
            OriginalWorksCredentialFile originalWorksCredentialFile = new OriginalWorksCredentialFile();
            originalWorksCredentialFile.setId(IDUtility.createID());
            originalWorksCredentialFile.setOriginalWorks(originalWorks);
            originalWorksCredentialFile.setName("credential.jpg");
            originalWorksCredentialFile.setUri("credential-files/" + newFilename);
            originalWorksCredentialFile.setCategory(OriginalWorksCredentialFile.CATEGORY_NOTARY);
            originalWorksRepository.addCredentialFile(originalWorksCredentialFile);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }



    @Override
    public List<OriginalWorksFile> getFileByChainId(String chainId) throws YumaoException {
        try {
            return originalWorksRepository.getFileByChainId(chainId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Override
    public List<OriginalWorksFile> getFileByChainIdAndNodeId(String chainId, String nodeId) throws YumaoException {
        try {
            return originalWorksRepository.getFileByChainIdAndNodeId(chainId, nodeId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Override
    public OriginalWorks get(String originWorkId) throws YumaoException {
        try {
            return originalWorksRepository.get(originWorkId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

}
