package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @Description:用户订单服务价格套餐表 
 * @date:   2018年8月22日 下午2:14:41 
 */
@SuppressWarnings("serial")
public class UserOrderServicePricePackage implements Serializable {

    public static final int STATUS_WAITING = 1;//等待开通
    public static final int STATUS_OPENED = 2;//已开通
    public static final int STATUS_EXPIRED = 3;//已到期


    public static final int ORDER_TYPE_OPEN=1;//开通
    public static final int ORDER_TYPE_RENEW=2;//续费

    private String id;

    private UserOrder userOrder;
    //用户订单服务表(存的订单服务表的id)
    private UserOrderService userOrderService;

    private Date startTime;

    private Date endTime;

    private int status;

    private Date creationTime;

    private Date openedTime;

    private Date expiredTime;

    private String name;

    private int byTimeType;

    private float unitPrice;

    private int validTimes;

    private float price;

    private String totalSpace;

    private String freeSpace;

    private int orderType=ORDER_TYPE_OPEN;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(final UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    public UserOrderService getUserOrderService() {
        return userOrderService;
    }

    public void setUserOrderService(final UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getOpenedTime() {
        return openedTime;
    }

    public void setOpenedTime(final Date openedTime) {
        this.openedTime = openedTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(final Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getByTimeType() {
        return byTimeType;
    }

    public void setByTimeType(final int byTimeType) {
        this.byTimeType = byTimeType;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getValidTimes() {
        return validTimes;
    }

    public void setValidTimes(final int validTimes) {
        this.validTimes = validTimes;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

    public String getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(final String totalSpace) {
        this.totalSpace = totalSpace;
    }

    public String getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(final String freeSpace) {
        this.freeSpace = freeSpace;
    }
}
