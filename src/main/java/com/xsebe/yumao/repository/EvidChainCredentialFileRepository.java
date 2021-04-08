package com.xsebe.yumao.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.EvidChainCredentialFile;

@Repository
public interface EvidChainCredentialFileRepository {

    EvidChainCredentialFile getByEvidChainId(@Param("id") String id);

    EvidChainCredentialFile get(@Param("id") String id);

}
