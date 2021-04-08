package com.xsebe.yumao.service;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.*;

import java.util.List;

public interface OriginalWorksService {

    Pageable<OriginalWorks> getPage(String name,String userId, List<String> ids, int currentPageOffset, int pageSize, boolean addToNode) throws YumaoException;

    long getDataTotal(String name,final User user, List<String> ids) throws YumaoException;

    Pageable<OriginalWorks> getPageForTask(int protectionStatus, int currentPageOffset, int pageSize) throws YumaoException;

    OriginalWorks getByFxServiceConfig(String fxEvidId) throws YumaoException;

    List<OriginalWorksFile> getFiles(String id) throws YumaoException;

    List<OriginalWorksCredentialFile> getCredentialFiles(String id) throws YumaoException;

    List<OriginalWorks> getFileStates(String userId) throws YumaoException;

    List<OriginalWorks> getList(List<String> ids) throws YumaoException;
    
    List<OriginalWorks> getsByTortInfoId(String tortInfoId) throws YumaoException;

    void add(User user, String name, int category, String sCreationTime, String originalFileMimetype, String originalFileName, byte[] originalFileBytes)
            throws YumaoException;

    void submitProtection(User user, OriginalWorks originalWorks, OriginalWorksFile originalWorksFile) throws YumaoException;

    void updateUploadingFailureProtectionStatus(String id) throws YumaoException;

    void reUpload(User user, String id) throws YumaoException;
    
    String getOriginalWorksName(String tortInfoId) throws YumaoException;
    
    List<OriginalWorks> getListByTortInfoId(String tortInfoId) throws YumaoException;
    
    List<OriginalWorksFile> listByTortInfoId(String tortInfoId) throws YumaoException;

    void addCredentialFile(OriginalWorks originalWorksId,List<String> evidIds);

    List<OriginalWorksFile> getFileByChainId(String chainId) throws YumaoException;
    
    List<OriginalWorksFile> getFileByChainIdAndNodeId(String chainId, String nodeId) throws YumaoException;

    OriginalWorks get(String originWorkId) throws YumaoException;

}
