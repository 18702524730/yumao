package com.xsebe.yumao.service;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserOrder;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserOrderService {

    Pageable<UserOrder> getPageForTask(int status, int currentPageOffset, int pageSize) throws YumaoException;

    Pageable<UserOrderServicePricePackage> getOrderServicePricePackagePageByPayment(int currentPageOffset, int pageSize) throws YumaoException;

    UserOrder getByOutTradeNo(String outTradeNo) throws YumaoException;

    void updatePaymentStatus(String id) throws YumaoException;

    void updatePaymentClosedStatus(String id) throws YumaoException;

    void updateOrderServicePricePackageOpenedStatus(final String orderServicePricePackageId) throws YumaoException;

    void updateOrderServicePricePackageExpiredStatus(final String orderServicePricePackageId) throws YumaoException;

//    UserOrder placeOrder(User user, String[] servicePricePackageIds) throws YumaoException;

    UserOrder placeOrder(User user, String[] servicePricePackageIds,Integer orderType) throws YumaoException;

    boolean hasServiceOpened(User user) throws YumaoException;

    UserOrderServicePricePackage getOpenOrderServicePricePackage(User user) throws YumaoException;

    void updateFreeSpace(String orderServicePricePackageId, String freeSpace) throws YumaoException;

    List<UserOrderServicePricePackage> getOrderServicePricePackagePage(@Param("outTradeNo")String outTradeNo) throws YumaoException;



    boolean hasServiceOpenedOrOver(User user) throws YumaoException;
}
