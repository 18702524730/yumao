package com.xsebe.yumao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.EvidChain;
import com.xsebe.yumao.model.EvidChainCategoryNode;
import com.xsebe.yumao.model.EvidChainNode;
import com.xsebe.yumao.repository.EvidChainNodeRepository;
import com.xsebe.yumao.service.EvidChainCategoryNodeService;
import com.xsebe.yumao.service.EvidChainNodeService;
import com.xsebe.yumao.service.EvidChainService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EvidChainNodeServiceImpl implements EvidChainNodeService {

    @Autowired
    private EvidChainNodeRepository repository;
    @Autowired
    private EvidChainCategoryNodeService evidChainCategoryNodeService;
    @Autowired
    private EvidChainService evidChainService;

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Override
    public List<EvidChainNode> getByChainId(String chainId) throws YumaoException {
        try {
            return repository.getByChainId(chainId);
        } catch (Exception e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void addByChainAndCategory(String chainId, String evidChainCategoryId) throws YumaoException {
        List<EvidChainCategoryNode> categoryNodes = evidChainCategoryNodeService.getByCategoryId(evidChainCategoryId);
        EvidChain evidChain = evidChainService.get(chainId);
        List<EvidChainNode> nodeList = new ArrayList<EvidChainNode>();
        for (EvidChainCategoryNode evidChainCategoryNode : categoryNodes) {
            EvidChainNode evidChainNode = new EvidChainNode();
            evidChainNode.setId(IDUtility.createID());
            evidChainNode.setCreatedTime(new Date());
            evidChainNode.setDeleteFlag(2);
            evidChainNode.setEvidChainCategoryNode(evidChainCategoryNode);
            evidChainNode.setIsMasterPic(evidChainCategoryNode.getIsMasterPic());
            evidChainNode.setName(evidChainCategoryNode.getName());
            evidChainNode.setSort(evidChainCategoryNode.getSort());
            evidChainNode.setEvidChain(evidChain);
            
            nodeList.add(evidChainNode);
        }
        try {
            repository.add(nodeList);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void deleteByChainId(String id) throws YumaoException {
        try {
            repository.deleteByChainId(id);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Override
    public EvidChainNode get(String id) throws YumaoException {
        try {
            return repository.get(id);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

}
