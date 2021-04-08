package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChainNode;

public interface EvidChainNodeService {

    public List<EvidChainNode> getByChainId(String chainId) throws YumaoException;
    
    public void addByChainAndCategory(String chainId, String evidChainCategoryId) throws YumaoException;

    public void deleteByChainId(String id) throws YumaoException;

    public EvidChainNode get(String id) throws YumaoException;
}
