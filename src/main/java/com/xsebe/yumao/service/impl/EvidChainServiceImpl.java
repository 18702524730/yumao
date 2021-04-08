package com.xsebe.yumao.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.PageUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.controller.EvidenceChainController;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.EvidChain;
import com.xsebe.yumao.model.EvidChainCategoryNode;
import com.xsebe.yumao.model.EvidChainNode;
import com.xsebe.yumao.model.EvidChainNodeOriginalWorks;
import com.xsebe.yumao.model.OriginalWorks;
import com.xsebe.yumao.model.OriginalWorksFile;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.repository.EvidChainRepository;
import com.xsebe.yumao.service.EvidChainCategoryNodeService;
import com.xsebe.yumao.service.EvidChainCategoryService;
import com.xsebe.yumao.service.EvidChainNodeOriginalWorksService;
import com.xsebe.yumao.service.EvidChainNodeService;
import com.xsebe.yumao.service.EvidChainService;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.utility.ZipUtil;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EvidChainServiceImpl implements EvidChainService {

    @Autowired
    private EvidChainCategoryService evidChainCategoryService;
    @Autowired
    private EvidChainNodeOriginalWorksService evidChainNodeOriginalWorksService;
    @Autowired
    private OriginalWorksService originalWorksService;
    @Autowired
    private EvidChainNodeService evidChainNodeService;
    @Autowired
    private EvidChainCategoryNodeService evidChainCategoryNodeService;

    @Value("#{settings.upload_absolute_path}")
    private String uploadAbsolutePath;

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Autowired
    private EvidChainRepository repository;

    @Override
    public long getTotalByUser(User current, String name) throws YumaoException {
        try {
            return repository.getTotalByUser(current, name);
        } catch (Exception e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍后再试");
        }
    }

    @Override
    public Pageable<EvidChain> getPage(User current, String name, int offset, int limit) throws YumaoException {
        long total;
        try {
            total = repository.getTotalByUser(current, name);
        } catch (Exception e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍后再试");
        }
        List<EvidChain> currentPageList = new ArrayList<EvidChain>();
        try {
            currentPageList = repository.getPageLimit(current, name, offset, limit);
        } catch (Exception e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍后再试");
        }

        Pageable<EvidChain> page = new Pageable<EvidChain>();
        page.setDataTotal(total);
        page.setCurrentPageList(currentPageList);
        page.setPageSize(limit);
        page.setPageTotal(PageUtility.computePageTotal(total, limit));
        return page;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public EvidChain add(User current, String name, String categoryId, String categoryName) throws YumaoException {
        if (DataUtility.isEmptyString(name, true)) {
            throw new YumaoException(666, "名称不能为空");
        }
        String id = IDUtility.createID();
        EvidChain evidChain = new EvidChain();
        evidChain.setUser(current);
        evidChain.setCategoryName(categoryName);
        evidChain.setCreatedTime(new Date());
        evidChain.setDeleteFlag(2);
        evidChain.setEvidChainCategory(evidChainCategoryService.get(categoryId));
        evidChain.setId(id);
        evidChain.setIntegrityPercent(0);
        evidChain.setName(name);
        evidChain.setStatus(0);

        try {
            repository.add(evidChain);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍后再试");
        }

        evidChainNodeService.addByChainAndCategory(id, categoryId);

        return evidChain;
    }

    @Override
    public EvidChain get(String id) throws YumaoException {
        try {
            return repository.get(id);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍后再试");
        }
    }

    @Override
    public byte[] getFilePackage(String chainId) throws YumaoException {
        List<EvidChainNodeOriginalWorks> evidChainNodeOriginalWorksList = evidChainNodeOriginalWorksService.getByEvidChainId(chainId);
        ByteArrayOutputStream fout = null;
        try {
            fout = new ByteArrayOutputStream();
            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
            Map<String, Object> map;
            for (EvidChainNodeOriginalWorks evidChainNodeOriginalWorks : evidChainNodeOriginalWorksList) {
                OriginalWorks originalWorks = evidChainNodeOriginalWorks.getOriginalWorks();
                List<OriginalWorksFile> files = originalWorksService.getFiles(originalWorks.getId());
                map = new HashMap<String, Object>();
                StringBuffer name = new StringBuffer();
                name.append(evidChainNodeOriginalWorks.getEvidChainCategoryNode().getName());
                name.append("/");
                name.append(files.get(0).getName());
                map.put("fileName", name.toString());
                map.put("fileBytes", getBytesByUrl(files.get(0).getUri()));
                fileList.add(map);
            }
            ZipUtil.compress(fileList, fout);
            return fout.toByteArray();
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        } finally {
            try {
                fout.close();
            } catch (Throwable ex) {
            }
        }
    }

    private byte[] getBytesByUrl(final String url) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        try {
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

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void delete(String id) throws YumaoException {
        List<EvidChainNodeOriginalWorks> list = evidChainNodeOriginalWorksService.getByEvidChainId(id);
        if (DataUtility.isNotNull(list) && list.size() > 0) {
            throw new YumaoException(666, "证据链包含原创内容，无法删除");
        }
        evidChainNodeService.deleteByChainId(id);
        try {
            repository.delete(id);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void addOriginalWorks(String chainId, String nodeId, List<String> idList) throws YumaoException {
        EvidChainCategoryNode node = evidChainCategoryNodeService.get(nodeId);
        EvidChain evidChain = get(chainId);
        List<EvidChainNodeOriginalWorks> list = new ArrayList<EvidChainNodeOriginalWorks>();
        for (String originWorkId : idList) {
            EvidChainNodeOriginalWorks ecno = new EvidChainNodeOriginalWorks();
            OriginalWorks originWork = originalWorksService.get(originWorkId);
            ecno.setId(IDUtility.createID());
            ecno.setSort(node.getSort());
            ecno.setOriginalWorks(originWork);
            ecno.setEvidChainNode(evidChain);
            ecno.setEvidChainCategoryNode(node);
            ecno.setDeleteFlag(2);
            ecno.setCreatedTime(new Date());
            list.add(ecno);
        }
        evidChainNodeOriginalWorksService.add(list);
        List<EvidChainNodeOriginalWorks> nodeOriginalWorkList = evidChainNodeOriginalWorksService.getByChainIdGruopByNodeId(chainId);
        List<EvidChainNode> chainNodeList = evidChainNodeService.getByChainId(chainId);
        float percent = nodeOriginalWorkList.size() * 100 / chainNodeList.size();
        updatePercent(chainId, percent);
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updatePercent(String chainId, float percent) throws YumaoException {
        try {
            repository.updatePercent(chainId, percent);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void removeOriginalWorks(String nodeId, String originalWorksId) throws YumaoException {
        EvidChainNode evidChainNode = evidChainNodeService.get(nodeId);
        try {
            evidChainNodeOriginalWorksService.delete(evidChainNode.getEvidChain().getId(), evidChainNode.getEvidChainCategoryNode().getId(), originalWorksId);
        } catch (Throwable e) {
            ERROR_LOG.error(ThrowableUtils.stackTracesToString(e));
            throw new YumaoException(500, "服务器异常，请稍候再试");
        }
        EvidChain evidChain = evidChainNode.getEvidChain();
        List<EvidChainNodeOriginalWorks> nodeOriginalWorkList = evidChainNodeOriginalWorksService.getByChainIdGruopByNodeId(evidChain.getId());
        List<EvidChainNode> chainNodeList = evidChainNodeService.getByChainId(evidChain.getId());
        float percent = nodeOriginalWorkList.size() * 100 / chainNodeList.size();
        updatePercent(evidChain.getId(), percent);
    }
}
