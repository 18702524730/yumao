package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.TortInfo;
import com.xsebe.yumao.model.TortLog;

/**
 * @Description:TODO
 * @date:2019年2月15日 下午3:03:24
 * @author:周伯通
 */
@Repository
public interface TortRepository {

    TortInfo getTortInfo(@Param("id") String id);

    long getTortInfoTotal(@Param("userId") String userId, @Param("name") String name);

    List<TortInfo> getsTortInfoLimit(@Param("userId") String userId, @Param("offset") int offset, @Param("limit") int limit, @Param("name") String name);

    List<TortInfo> listByStatus(@Param("userId") String userId, @Param("rightsProtectionStatus") int[] rightsProtectionStatus);

    List<TortLog> getsTortLogList(@Param("tortInfoId") String tortInfoId);

    List<TortInfo> getsByGoodsUrl(@Param("userId") String userId, @Param("goodsUrl") String goodsUrl);

    void addTortLog(@Param("tortLog") TortLog tortLog);

    void addTortInfo(@Param("tortInfo") TortInfo tortInfo);

    void addTortInfoAndOriginalWorks(@Param("tortInfoId") String tortInfoId, @Param("originalWorksId") String originalWorksId);

    void updateTortInfo(@Param("tortInfo") TortInfo tortInfo);

    void updateTortInfoStatus(@Param("tortInfoId") String tortInfoId, @Param("status") int status);

    void delTortInfoById(@Param("tortInfoId") String tortInfoId);

    void delTortInfoAndOriginal(@Param("tortInfoId") String tortInfoId);

    void delTortInfoAndTortLog(@Param("tortInfoId") String tortInfoId);
}
