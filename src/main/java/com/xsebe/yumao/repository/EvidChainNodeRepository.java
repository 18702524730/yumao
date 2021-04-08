package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.EvidChainNode;

@Repository
public interface EvidChainNodeRepository {

    List<EvidChainNode> getByChainId(@Param("chainId") String chainId);

    void add(@Param("nodeList") List<EvidChainNode> nodeList);

    void deleteByChainId(@Param("id") String id);

    EvidChainNode get(@Param("id") String id);

}
