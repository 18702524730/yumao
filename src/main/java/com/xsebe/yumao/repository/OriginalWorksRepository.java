package com.xsebe.yumao.repository;

import com.xsebe.yumao.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface OriginalWorksRepository {

    OriginalWorks get(@Param("id") String id);

    List<OriginalWorks> getList(@Param("ids") List<String> ids);

    List<OriginalWorks> getByTortInfoId(@Param("tortInfoId") String tortInfoId);

    long getTotal(@Param("name") String name, @Param("ids") List<String> ids,
                  @Param("user_id") String userId, @Param("addToNode") boolean addToNode);

    List<OriginalWorks> getsByLimit(@Param("name") String name, @Param("ids") List<String> ids,
                                    @Param("user_id") String userId, @Param("offset") int offset,
            @Param("limit") int limit, @Param("addToNode") boolean addToNode);

    long getTotalForTask(@Param("protectionStatus") int protectionStatus);

    List<OriginalWorks> getsByLimitForTask(@Param("protectionStatus") int protectionStatus, @Param("offset") int offset, @Param("limit") int limit);

    OriginalWorks getByFxServiceConfig(@Param("fxEvidId") String fxEvidId);

    OriginalWorksFxServiceConfig getFxServiceConfig(@Param("id") String id);

    List<OriginalWorksFile> getFiles(@Param("id") String id);

    List<OriginalWorksCredentialFile> getCredentialFiles(@Param("id") String id);

    List<OriginalWorksFile> listFileByTortInfoId(@Param("tortInfoId") String tortInfoId);

    List<OriginalWorks> getListByTortInfoId(@Param("tortInfoId") String tortInfoId);

    long countByName(@Param("name") String name, @Param("userId") String userId);

    /**
     * 
     * @Title: getFileState
     * @Description: TODO
     * @param userId
     * @return List<OriginalWorks>
     * @date 2018年8月8日下午3:57:24
     */
    List<OriginalWorks> getFileStates(@Param("user_id") String userId);

    void add(@Param("originalWorks") OriginalWorks originalWorks);

    void addFxServiceConfig(@Param("originalWorksFxServiceConfig") OriginalWorksFxServiceConfig originalWorksFxServiceConfig);

    void addFile(@Param("originalWorksFile") OriginalWorksFile originalWorksFile);

    void addUserLink(@Param("userId") String userId, @Param("originalWorksId") String originalWorksId);

    void addCredentialFile(@Param("originalWorksCredentialFile") OriginalWorksCredentialFile originalWorksCredentialFile);

    void updateUploadingProtectionStatus(@Param("id") String id, @Param("protectionStatus") int protectionStatus, @Param("uploadingTime") Date uploadingTime);

    void updateProtectingProtectionStatus(@Param("id") String id, @Param("protectionStatus") int protectionStatus, @Param("protectionTime") Date protectionTime);

    void updateUploadingFailureProtectionStatus(@Param("id") String id, @Param("protectionStatus") int protectionStatus,
            @Param("uploadingTime") Date uploadingTime);

    void updateFxServiceConfig(@Param("originalWorksFxServiceConfig") OriginalWorksFxServiceConfig originalWorksFxServiceConfig);

    /**
     * 查询所有存证成功但是存证函失败的单子
     *
     * @return
     */
    List<OriginalWorksVO> selectAllFailureWorksForCredentialFile();

    List<OriginalWorksFile> getFileByChainId(@Param("chainId") String chainId);

    List<OriginalWorksFile> getFileByChainIdAndNodeId(@Param("chainId") String chainId, @Param("nodeId") String nodeId);


}