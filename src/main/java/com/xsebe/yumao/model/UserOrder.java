package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserOrder implements Serializable {

    public static final int TRADE_STATUS_TRADE_SUCCESS = 1;

    public static final int TRADE_STATUS_TRADE_FINISHED = 2;

    public static final int STATUS_PAYMENT_WAITING = 1;

    public static final int STATUS_PAYMENT = 2;

    public static final int STATUS_PAYMENT_CLOSED = 3;

    public static final int PAYMENT_WAY_ALIPAY = 1;


    public static final int ORDER_TYPE_OPEN=1;//开通
    public static final int ORDER_TYPE_RENEW=2;//续费

    private String id;

    private User user;

    private String outTradeNo;

    private String subject;

    private float totalPrice;

    private String body;

    private int tradeStatus;

    private int status;

    private Date creationTime;

    private Date paymentTime;

    private Date paymentClosedTime;

    private int paymentWay;

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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(final String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(final int tradeStatus) {
        this.tradeStatus = tradeStatus;
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

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(final Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentClosedTime() {
        return paymentClosedTime;
    }

    public void setPaymentClosedTime(final Date paymentClosedTime) {
        this.paymentClosedTime = paymentClosedTime;
    }

    public int getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(final int paymentWay) {
        this.paymentWay = paymentWay;
    }
}
