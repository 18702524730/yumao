package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServicePricePackage implements Serializable {

    public static final int BY_TIME_TYPE_YEAR = 1;
    public static final int BY_TIME_TYPE_MONTH = 2;
    public static final int BY_TIME_TYPE_DAY = 3;

    private String id;

    private Service service;

    private String name;

    private int byTimeType;

    private float unitPrice;

    private int validTimes;

    private float price;
    
    private String totalSpace;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(final Service service) {
        this.service = service;
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
}
