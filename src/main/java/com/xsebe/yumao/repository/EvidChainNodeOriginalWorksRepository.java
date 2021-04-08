package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.EvidChainNodeOriginalWorks;

@Repository
public interface EvidChainNodeOriginalWorksRepository {

    EvidChainNodeOriginalWorks get(@Param("id") String id);

    List<EvidChainNodeOriginalWorks> getByChainId(@Param("id") String id);

    void add(@Param("list") List<EvidChainNodeOriginalWorks> list);

    void delete(@Param("chainId") String chainId, @Param("nodeId") String nodeId, @Param("originalWorksId") String originalWorksId);

    List<EvidChainNodeOriginalWorks> getByChainIdGruopByNodeId(@Param("chainId") String chainId);
}
