package com.xsebe.yumao.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.EvidChainCategory;
import com.xsebe.yumao.repository.EvidChainCategoryRepository;
import com.xsebe.yumao.service.EvidChainCategoryService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EvidChainCategoryServiceImpl implements EvidChainCategoryService {
    @Autowired
    private EvidChainCategoryRepository repository;

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private final static Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);
    
    @Override
    public EvidChainCategory get(String id) throws YumaoException {
        try{
            return repository.get(id);
        }catch(Throwable e){
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

}
