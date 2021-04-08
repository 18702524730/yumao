package com.xsebe.yumao.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.model.EvidChainNodeOriginalWorks;
import com.xsebe.yumao.repository.EvidChainNodeOriginalWorksRepository;
import com.xsebe.yumao.service.EvidChainNodeOriginalWorksService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EvidChainNodeOriginalWorksServiceImpl implements EvidChainNodeOriginalWorksService {
    @Autowired
    EvidChainNodeOriginalWorksRepository repository;

    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Override
    public List<EvidChainNodeOriginalWorks> getByEvidChainId(String chainId) throws YumaoException {
        try {
            return repository.getByChainId(chainId);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Override
    public EvidChainNodeOriginalWorks get(String id) throws YumaoException {
        try {
            return repository.get(id);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void add(List<EvidChainNodeOriginalWorks> list) throws YumaoException {
        try {
            repository.add(list);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void delete(String chainId, String nodeId, String originalWorksId) throws YumaoException {
        try {
            repository.delete(chainId, nodeId, originalWorksId);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Override
    public List<EvidChainNodeOriginalWorks> getByChainIdGruopByNodeId(String chainId) throws YumaoException {
        try {
            return repository.getByChainIdGruopByNodeId(chainId);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

}
