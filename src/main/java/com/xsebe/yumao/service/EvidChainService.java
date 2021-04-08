package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChain;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.User;

public interface EvidChainService {

    long getTotalByUser(User current, String name) throws YumaoException;
    
    Pageable<EvidChain> getPage(User current, String name, int offset, int limit) throws YumaoException;

    EvidChain add(User current, String name, String categoryId, String categoryName) throws YumaoException;
    
    EvidChain get(String id) throws YumaoException;

    byte[] getFilePackage(String chainId) throws YumaoException;

    void delete(String id) throws YumaoException;

    void addOriginalWorks(String chainId, String nodeId, List<String> idList) throws YumaoException;

    void removeOriginalWorks(String nodeId, String originalWorksId) throws YumaoException;

    void updatePercent(String chainId, float percent) throws YumaoException;
}
