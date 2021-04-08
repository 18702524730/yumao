package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.Service;
import com.xsebe.yumao.model.ServicePricePackage;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface ServiceRepository {

    Service get(@Param("id") String id);
    
    ServicePricePackage getPricePackage(@Param("price_package_id") String pricePackageId);
    
    long getPricePackageTotal();

    List<ServicePricePackage> getPricePackagesByLimit(@Param("offset") int offset, @Param("limit") int limit);
}