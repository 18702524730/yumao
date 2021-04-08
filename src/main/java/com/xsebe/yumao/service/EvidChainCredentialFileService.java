package com.xsebe.yumao.service;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChainCredentialFile;

public interface EvidChainCredentialFileService {

    public EvidChainCredentialFile getByEvidChainId(String id) throws YumaoException;
    
    public EvidChainCredentialFile get(String id) throws YumaoException;

    public byte[] getFileBytesByEvidChainId(String chainId) throws YumaoException;
}
