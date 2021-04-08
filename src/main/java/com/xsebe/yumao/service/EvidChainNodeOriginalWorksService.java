package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChainNodeOriginalWorks;

public interface EvidChainNodeOriginalWorksService {

    EvidChainNodeOriginalWorks get(String id) throws YumaoException;

    List<EvidChainNodeOriginalWorks> getByEvidChainId(String chainId) throws YumaoException;

    void add(List<EvidChainNodeOriginalWorks> list) throws YumaoException;

    void delete(String chainId, String nodeId, String originalWorksId) throws YumaoException;

    List<EvidChainNodeOriginalWorks> getByChainIdGruopByNodeId(String chainId) throws YumaoException;
}
