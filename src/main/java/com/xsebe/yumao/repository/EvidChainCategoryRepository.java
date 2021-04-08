package com.xsebe.yumao.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.EvidChainCategory;

@Repository
public interface EvidChainCategoryRepository {

    EvidChainCategory get(@Param("id") String id);
}
