package com.xsebe.yumao.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.model.EvidChainCredentialFile;
import com.xsebe.yumao.repository.EvidChainCredentialFileRepository;
import com.xsebe.yumao.service.EvidChainCredentialFileService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EvidChainCredentialFileServiceImpl implements EvidChainCredentialFileService {
    @Autowired
    private EvidChainCredentialFileRepository repository;

    @Value("#{settings.upload_absolute_path}")
    private String uploadAbsolutePath;
    
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Override
    public EvidChainCredentialFile getByEvidChainId(String id) throws YumaoException {
        try {
            return repository.getByEvidChainId(id);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Override
    public EvidChainCredentialFile get(String id) throws YumaoException {
        try {
            return repository.get(id);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Override
    public byte[] getFileBytesByEvidChainId(String chainId) throws YumaoException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        try {
            EvidChainCredentialFile file = getByEvidChainId(chainId);
            String url = file.getUrl();
            fis = new FileInputStream(uploadAbsolutePath + url.replace("/", File.separator));
            IOUtils.copy(fis, baos);
        } catch (Throwable ex) {
        } finally {
            try {
                fis.close();
            } catch (Throwable ex) {
            }
            try {
                baos.close();
            } catch (Throwable ex) {
            }
        }
        return baos.toByteArray();
    }
}
