package com.xsebe.yumao.service.impl;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.PageUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.*;
import com.xsebe.yumao.repository.UserOrderRepository;
import com.xsebe.yumao.service.CommonService;
import com.xsebe.yumao.service.ServiceService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.utility.OrderIDUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserOrderServiceImpl implements UserOrderService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Autowired
    private UserOrderRepository userOrderRepository;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private CommonService commonService;

    @Autowired
    private UserOrderService userOrderService;

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public Pageable<UserOrder> getPageForTask(final int status, final int currentPageOffset, final int pageSize) throws YumaoException {
        long dataTotal;
        try {
            dataTotal = userOrderRepository.getTotalForTask(status);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        List<UserOrder> currentPageList;
        try {
            currentPageList = userOrderRepository.getByLimitForTask(status, currentPageOffset, pageSize);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        Pageable<UserOrder> page = new Pageable<UserOrder>();
        page.setPageSize(pageSize);
        page.setPageTotal(PageUtility.computePageTotal(dataTotal, pageSize));
        page.setCurrentPageList(currentPageList);
        page.setDataTotal(dataTotal);
        return page;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public Pageable<UserOrderServicePricePackage> getOrderServicePricePackagePageByPayment(final int currentPageOffset, final int pageSize)
            throws YumaoException {
        long dataTotal;
        try {
            dataTotal = userOrderRepository.getOrderServicePricePackageTotalByPayment();
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        List<UserOrderServicePricePackage> currentPageList;
        try {
            currentPageList = userOrderRepository.getOrderServicePricePackagesByPaymentAndLimit(currentPageOffset, pageSize);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        Pageable<UserOrderServicePricePackage> page = new Pageable<UserOrderServicePricePackage>();
        page.setPageSize(pageSize);
        page.setPageTotal(PageUtility.computePageTotal(dataTotal, pageSize));
        page.setCurrentPageList(currentPageList);
        page.setDataTotal(dataTotal);
        return page;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public UserOrder getByOutTradeNo(final String outTradeNo) throws YumaoException {
        try {
            return userOrderRepository.getByOutTradeNo(outTradeNo);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updatePaymentStatus(final String id) throws YumaoException {
        try {
            userOrderRepository.updatePaymentStatus(id, UserOrder.STATUS_PAYMENT, new Date());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updatePaymentClosedStatus(final String id) throws YumaoException {
        try {
            userOrderRepository.updatePaymentClosedStatus(id, UserOrder.STATUS_PAYMENT_CLOSED, new Date());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateOrderServicePricePackageOpenedStatus(final String orderServicePricePackageId) throws YumaoException {
        try {
            //TODO 续费的开通  要把已开通的关闭
            UserOrderServicePricePackage userOrderServicePricePackage = userOrderRepository.getOrderServicePricePackage(orderServicePricePackageId);
            if (userOrderServicePricePackage != null) {
                if (userOrderServicePricePackage.getOrderType() == 2) {
                    //要把已开通的关闭
                    List<UserOrderServicePricePackage> list = userOrderRepository.getOpenOrderServicePricePackages4Close(orderServicePricePackageId);
                    if (list != null && list.size() > 0) {
                        for(UserOrderServicePricePackage pricePackage:list){
                            userOrderRepository.updateOrderServicePricePackageExpiredStatus(pricePackage.getId(), UserOrderServicePricePackage.STATUS_EXPIRED, new Date());
                        }
                    }
                }
                //续费的开通
                userOrderRepository.updateOrderServicePricePackageOpenedStatus(orderServicePricePackageId, UserOrderServicePricePackage.STATUS_OPENED, new Date());
            }
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateOrderServicePricePackageExpiredStatus(final String orderServicePricePackageId) throws YumaoException {
        try {
            userOrderRepository
                    .updateOrderServicePricePackageExpiredStatus(orderServicePricePackageId, UserOrderServicePricePackage.STATUS_EXPIRED, new Date());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    private static Date computeEndTime(final Date startTime, final int byTimeType, final int validTimes) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(startTime);
        switch (byTimeType) {
        case ServicePricePackage.BY_TIME_TYPE_YEAR:
            cale.add(Calendar.YEAR, validTimes);
            break;
        case ServicePricePackage.BY_TIME_TYPE_MONTH:
            cale.add(Calendar.MONTH, validTimes);
            break;
        case ServicePricePackage.BY_TIME_TYPE_DAY:
            cale.add(Calendar.DATE, validTimes);
            break;
        }
        return cale.getTime();
    }

//    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
//    @Override
//    public UserOrder placeOrder(final User user, final String[] servicePricePackageIds) throws YumaoException {
//        UserOrder userOrder = new UserOrder();
//        userOrder.setId(IDUtility.createID());
//        List<UserOrderServicePricePackage> userOrderServicePricePackages = new ArrayList<UserOrderServicePricePackage>(servicePricePackageIds.length);
//
//        String outTradeNo = OrderIDUtility.createOrderID();
//        String subject = "预猫年度服务";
//        float totalPrice = 0F;
//        String body = "原创知识产权保护服务";
//
//        UserOrderServicePricePackage userOrderServicePricePackage;
//        List<ServicePricePackage> currentPageList = serviceService.getList(servicePricePackageIds);
//        Date startTime = new Date();
//        Date endTime;
//        com.xsebe.yumao.model.Service service;
//        for (ServicePricePackage servicePricePackage : currentPageList) {
//            service = servicePricePackage.getService();
//            totalPrice += servicePricePackage.getPrice();
//            userOrderServicePricePackage = new UserOrderServicePricePackage();
//            userOrderServicePricePackage.setId(IDUtility.createID());
//            userOrderServicePricePackage.setUserOrder(userOrder);
//            com.xsebe.yumao.model.UserOrderService userOrderService = userOrderRepository.getUserOrderServiceByName(service.getName());
//            if (DataUtility.isNull(userOrderService)) {
//                userOrderService = new com.xsebe.yumao.model.UserOrderService();
//                userOrderService.setId(IDUtility.createID());
//                userOrderService.setName(service.getName());
//                try {
//                    userOrderRepository.addUserOrderService(userOrderService);
//                } catch (Throwable ex) {
//                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
//                    throw new YumaoException(666, "服务忙，请稍候再试", ex);
//                }
//            }
//            userOrderServicePricePackage.setUserOrderService(userOrderService);
//            userOrderServicePricePackage.setStartTime(startTime);
//            endTime = computeEndTime(startTime, servicePricePackage.getByTimeType(), servicePricePackage.getValidTimes());
//            userOrderServicePricePackage.setEndTime(endTime);
//            startTime = endTime;
//            userOrderServicePricePackage.setStatus(UserOrderServicePricePackage.STATUS_WAITING);
//            userOrderServicePricePackage.setCreationTime(new Date());
//            userOrderServicePricePackage.setOpenedTime(null);
//            userOrderServicePricePackage.setExpiredTime(null);
//            userOrderServicePricePackage.setName(servicePricePackage.getName());
//            userOrderServicePricePackage.setByTimeType(servicePricePackage.getByTimeType());
//            userOrderServicePricePackage.setValidTimes(servicePricePackage.getValidTimes());
//            userOrderServicePricePackage.setUnitPrice(servicePricePackage.getUnitPrice());
//            userOrderServicePricePackage.setPrice(servicePricePackage.getPrice());
//            userOrderServicePricePackage.setTotalSpace(servicePricePackage.getTotalSpace());
//            userOrderServicePricePackage.setFreeSpace(servicePricePackage.getTotalSpace());
//            userOrderServicePricePackages.add(userOrderServicePricePackage);
//        }
//        userOrder.setUser(user);
//        userOrder.setOutTradeNo(outTradeNo);
//        userOrder.setSubject(subject);
//        userOrder.setTotalPrice(totalPrice);
//        userOrder.setBody(body);
//        userOrder.setStatus(UserOrder.STATUS_PAYMENT_WAITING);
//        userOrder.setCreationTime(new Date());
//        userOrder.setPaymentTime(null);
//        userOrder.setPaymentWay(UserOrder.PAYMENT_WAY_ALIPAY);
//
//        try {
//            userOrderRepository.add(userOrder);
//        } catch (Throwable ex) {
//            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
//            throw new YumaoException(666, "服务忙，请稍候再试", ex);
//        }
//        for (UserOrderServicePricePackage p : userOrderServicePricePackages) {
//            try {
//                userOrderRepository.addOrderServicePricePackage(p);
//            } catch (Throwable ex) {
//                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
//                throw new YumaoException(666, "服务忙，请稍候再试", ex);
//            }
//        }
//
//        return userOrder;
//    }


    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/rollbackFor = Throwable.class)
    @Override
    public UserOrder placeOrder(User user, String[] servicePricePackageIds,Integer orderType) throws YumaoException{
        UserOrder userOrder = new UserOrder();
        userOrder.setId(IDUtility.createID());
        List<UserOrderServicePricePackage> userOrderServicePricePackages = new ArrayList<UserOrderServicePricePackage>(servicePricePackageIds.length);

        String outTradeNo = OrderIDUtility.createOrderID();
        String subject = "预猫年度服务";
        if (orderType.equals(2)) {
            subject = subject + "_续费";
        }
        float totalPrice = 0F;
        String body = "原创知识产权保护服务";

//        UserOrderServicePricePackage userOrderServicePricePackage;
        List<ServicePricePackage> currentPageList = serviceService.getList(servicePricePackageIds);
        Date startTime = new Date();
        if (orderType != null && orderType.equals(2)) {
            UserOrderServicePricePackage userOrderServicePricePackage = userOrderRepository.getOpenOrderServicePricePackages4Newest(user.getId());
            if (userOrderServicePricePackage != null) {
                startTime = userOrderServicePricePackage.getEndTime();
            }
        }
        Date endTime;
        com.xsebe.yumao.model.Service service;
        for (ServicePricePackage servicePricePackage : currentPageList) {
            service = servicePricePackage.getService();
            totalPrice += servicePricePackage.getPrice();
            UserOrderServicePricePackage userOrderServicePricePackage = new UserOrderServicePricePackage();
            userOrderServicePricePackage.setId(IDUtility.createID());
            userOrderServicePricePackage.setUserOrder(userOrder);
            com.xsebe.yumao.model.UserOrderService userOrderServiceVO = userOrderRepository.getUserOrderServiceByName(service.getName());
            if (DataUtility.isNull(userOrderServiceVO)) {
                userOrderServiceVO = new com.xsebe.yumao.model.UserOrderService();
                userOrderServiceVO.setId(IDUtility.createID());
                userOrderServiceVO.setName(service.getName());
                try {
                    userOrderRepository.addUserOrderService(userOrderServiceVO);
                } catch (Throwable ex) {
                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                    throw new YumaoException(666, "服务忙，请稍候再试", ex);
                }
            }
            userOrderServicePricePackage.setUserOrderService(userOrderServiceVO);
            userOrderServicePricePackage.setStartTime(startTime);
            endTime = computeEndTime(startTime, servicePricePackage.getByTimeType(), servicePricePackage.getValidTimes());
            userOrderServicePricePackage.setEndTime(endTime);
            startTime = endTime;
            userOrderServicePricePackage.setStatus(UserOrderServicePricePackage.STATUS_WAITING);
            userOrderServicePricePackage.setCreationTime(new Date());
            userOrderServicePricePackage.setOpenedTime(null);
            userOrderServicePricePackage.setExpiredTime(null);
            userOrderServicePricePackage.setName(servicePricePackage.getName());
            userOrderServicePricePackage.setByTimeType(servicePricePackage.getByTimeType());
            userOrderServicePricePackage.setValidTimes(servicePricePackage.getValidTimes());
            userOrderServicePricePackage.setUnitPrice(servicePricePackage.getUnitPrice());
            userOrderServicePricePackage.setPrice(servicePricePackage.getPrice());
            if (orderType != null && orderType.equals(UserOrderServicePricePackage.ORDER_TYPE_RENEW)) {
                userOrderServicePricePackage.setOrderType(UserOrderServicePricePackage.ORDER_TYPE_RENEW);
                //查上一次的
                UserOrderServicePricePackage uu = userOrderService.getOpenOrderServicePricePackage(user);
                userOrderServicePricePackage.setFreeSpace(uu.getTotalSpace());
                userOrderServicePricePackage.setTotalSpace(uu.getTotalSpace());
            } else {
                userOrderServicePricePackage.setFreeSpace(servicePricePackage.getTotalSpace());
                userOrderServicePricePackage.setTotalSpace(servicePricePackage.getTotalSpace());
            }
            userOrderServicePricePackages.add(userOrderServicePricePackage);
        }
        userOrder.setUser(user);
        userOrder.setOutTradeNo(outTradeNo);
        userOrder.setSubject(subject);
        userOrder.setTotalPrice(totalPrice);
        userOrder.setBody(body);
        userOrder.setStatus(UserOrder.STATUS_PAYMENT_WAITING);
        userOrder.setCreationTime(new Date());
        userOrder.setPaymentTime(null);
        userOrder.setPaymentWay(UserOrder.PAYMENT_WAY_ALIPAY);
        if (orderType != null && orderType.equals(UserOrder.ORDER_TYPE_RENEW)) {
            userOrder.setOrderType(UserOrder.ORDER_TYPE_RENEW);
        }
        try {
            userOrderRepository.add(userOrder);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        for (UserOrderServicePricePackage p : userOrderServicePricePackages) {
            try {
                userOrderRepository.addOrderServicePricePackage(p);
            } catch (Throwable ex) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                throw new YumaoException(666, "服务忙，请稍候再试", ex);
            }
        }

        return userOrder;
    }



    /**
     * 如果用户当前有套餐且已开通状态，返回true
     */
    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public boolean hasServiceOpened(final User user) throws YumaoException {
        long openedCount;
        try {
            // 查询当前用户在用户订单表中‘已支付’，并且在服务价格表中‘已开通’的条数
            openedCount = userOrderRepository.getOrderServicePricePackageByStatus(user.getId(), UserOrderServicePricePackage.STATUS_OPENED);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        return openedCount > 0;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public UserOrderServicePricePackage getOpenOrderServicePricePackage(final User user) throws YumaoException {
        UserOrderServicePricePackage list;
        try {
            list = userOrderRepository.getOpenOrderServicePricePackages4Newest(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        return list;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateFreeSpace(final String orderServicePricePackageId, final String freeSpace) throws YumaoException {
        try {
            userOrderRepository.updateFreeSpace(orderServicePricePackageId, freeSpace);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public List<UserOrderServicePricePackage> getOrderServicePricePackagePage(final String outTradeNo) throws YumaoException {

        List<UserOrderServicePricePackage> orderServicePricePackagePage;
        try {
            orderServicePricePackagePage = userOrderRepository.getOrderServicePricePackagePage(outTradeNo);
        } catch (Exception ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        return orderServicePricePackagePage;
    }


    //查询用户是否开通或过期
    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    @Override
    public boolean hasServiceOpenedOrOver(User user) throws YumaoException {

        long openedCount;
        try {
            openedCount = userOrderRepository.getOrderServicePricePackage4User(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        return openedCount > 0;
    }
}
