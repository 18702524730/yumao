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

import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.PageUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.TortInfo;
import com.xsebe.yumao.model.UserFaq;
import com.xsebe.yumao.repository.UserFaqRepository;
import com.xsebe.yumao.service.UserFaqService;

/**
 * @Description:TODO
 * @date:2019年3月1日 下午3:54:04
 * @author:周伯通
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserFaqServiceImpl implements UserFaqService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Autowired
    private UserFaqRepository userFaqRepository;

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public UserFaq get(final String id) throws YumaoException {
        try {
            return userFaqRepository.get(id);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<UserFaq> list() throws YumaoException {
        try {
            return userFaqRepository.list();
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public long total() throws YumaoException {
        try {
            return userFaqRepository.total();
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public Pageable<UserFaq> listPage(int pageNo, int pageSize) throws YumaoException {
        long dataTotal;
        try {
            dataTotal = userFaqRepository.total();
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        List<UserFaq> currentPageList = null;
        try {
            currentPageList = userFaqRepository.listPage(pageNo, pageSize);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        Pageable<UserFaq> page = new Pageable<UserFaq>();
        page.setPageSize(pageSize);
        page.setPageTotal(PageUtility.computePageTotal(dataTotal, pageSize));
        page.setCurrentPageList(currentPageList);
        page.setDataTotal(dataTotal);
        return page;
    }

}
