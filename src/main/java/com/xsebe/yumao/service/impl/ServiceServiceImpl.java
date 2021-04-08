package com.xsebe.yumao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.xsebe.api.utility.PageUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.ServicePricePackage;
import com.xsebe.yumao.repository.ServiceRepository;
import com.xsebe.yumao.repository.UserRepository;
import com.xsebe.yumao.service.ServiceService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceServiceImpl implements ServiceService {
    
    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<ServicePricePackage> getList(final String[] ids) throws YumaoException {
        List<ServicePricePackage> list = new ArrayList<ServicePricePackage>();
        try {
            for (String id : ids) {
                list.add(serviceRepository.getPricePackage(id));
            }
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        return list;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    public Pageable<ServicePricePackage> getPricePackages(final int currentPageOffset, final int pageSize) throws YumaoException {
        long dataTotal;
        try {
            dataTotal = serviceRepository.getPricePackageTotal();
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        List<ServicePricePackage> currentPageList;
        try {
            currentPageList = serviceRepository.getPricePackagesByLimit(currentPageOffset, pageSize);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        Pageable<ServicePricePackage> page = new Pageable<ServicePricePackage>();
        page.setPageSize(pageSize);
        page.setPageTotal(PageUtility.computePageTotal(dataTotal, pageSize));
        page.setCurrentPageList(currentPageList);
        page.setDataTotal(dataTotal);
        return page;
    }
}
