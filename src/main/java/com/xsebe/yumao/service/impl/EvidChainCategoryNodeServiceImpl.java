package com.xsebe.yumao.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.model.EvidChainCategoryNode;
import com.xsebe.yumao.repository.EvidChainCategoryNodeRepository;
import com.xsebe.yumao.service.EvidChainCategoryNodeService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EvidChainCategoryNodeServiceImpl implements EvidChainCategoryNodeService {
    @Autowired
    private EvidChainCategoryNodeRepository repository;
    
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Override
    public EvidChainCategoryNode get(String id) throws YumaoException {
        try{
            return repository.get(id);
        }catch(Throwable e){
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Override
    public List<EvidChainCategoryNode> getByCategoryId(String evidChainCategoryId) throws YumaoException {
        try{
            return repository.getByCategoryId(evidChainCategoryId);
        }catch(Throwable e){
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

}
