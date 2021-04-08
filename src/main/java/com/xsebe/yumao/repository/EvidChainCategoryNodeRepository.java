package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChainCategoryNode;

@Repository
public interface EvidChainCategoryNodeRepository {

    EvidChainCategoryNode get(@Param("id") String id) throws YumaoException;

    List<EvidChainCategoryNode> getByCategoryId(@Param("categoryId") String evidChainCategoryId);
}
