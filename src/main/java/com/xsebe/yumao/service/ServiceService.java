package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.ServicePricePackage;

public interface ServiceService {
    
    List<ServicePricePackage> getList(String[] ids) throws YumaoException;
    
    Pageable<ServicePricePackage> getPricePackages(int currentPageOffset, int pageSize) throws YumaoException;
}
