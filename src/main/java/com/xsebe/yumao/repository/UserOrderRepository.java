package com.xsebe.yumao.repository;

import com.xsebe.yumao.model.UserOrder;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface UserOrderRepository {

    UserOrder get(@Param("id") String id);

    long getTotalForTask(@Param("status") int status);

    List<UserOrder> getByLimitForTask(@Param("status") int status, @Param("offset") int offset, @Param("limit") int limit);

    long getOrderServicePricePackageTotalByPayment();

    List<UserOrderServicePricePackage> getOrderServicePricePackagesByPaymentAndLimit(@Param("offset") int offset,
            @Param("limit") int limit);
    
    List<UserOrderServicePricePackage> getOrderServicePricePackagePage(@Param("outTradeNo")String outTradeNo);

    UserOrderServicePricePackage getOrderServicePricePackage(@Param("id") String id);

    long getOrderServicePricePackageByStatus(@Param("user_id") String userId, @Param("ppstatus") int status);

    long getOrderServicePricePackage4User(@Param("user_id") String userId);


    UserOrder getByOutTradeNo(@Param("out_trade_no") String outTradeNo);

    void add(@Param("user_order") UserOrder userOrder);

    void addOrderServicePricePackage(@Param("user_order_service_price_package") UserOrderServicePricePackage userOrderServicePricePackage);

    void updatePaymentStatus(@Param("id") String id, @Param("status") int status, @Param("payment_time") Date paymentTime);

    void updatePaymentClosedStatus(@Param("id") String id, @Param("status") int status, @Param("payment_closed_time") Date paymentClosedTime);

    void updateOrderServicePricePackageOpenedStatus(@Param("orderServicePricePackage_id") String orderServicePricePackageId, @Param("status") int status,
            @Param("opened_time") Date openedTime);

    void updateOrderServicePricePackageExpiredStatus(@Param("orderServicePricePackage_id") String orderServicePricePackageId, @Param("status") int status,
            @Param("expired_time") Date expiredTime);

    List<UserOrderServicePricePackage> getOpenOrderServicePricePackages(@Param("user_id") String userId);

    void updateFreeSpace(@Param("orderServicePricePackage_id") String orderServicePricePackageId, @Param("freeSpace") String freeSpace);

    com.xsebe.yumao.model.UserOrderService getUserOrderService(@Param("userOrderServiceId") String userOrderServiceId);
    
    com.xsebe.yumao.model.UserOrderService getUserOrderServiceByName(@Param("name") String name);

    void addUserOrderService(@Param("userOrderService") com.xsebe.yumao.model.UserOrderService userOrderService);

    UserOrderServicePricePackage getOpenOrderServicePricePackages4Newest(@Param("user_id") String userId);


    List<UserOrderServicePricePackage> getOpenOrderServicePricePackages4Close(@Param("package_id") String packageId);
}