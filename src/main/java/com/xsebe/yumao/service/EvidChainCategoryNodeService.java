package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChainCategoryNode;

public interface EvidChainCategoryNodeService {

    EvidChainCategoryNode get(String id) throws YumaoException;

    List<EvidChainCategoryNode> getByCategoryId(String chainId) throws YumaoException;
}
