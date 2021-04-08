package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:侵权信息
 * @date:2019年2月15日 下午3:04:43
 * @author:周伯通
 */
@SuppressWarnings("serial")
public class TortInfo implements Serializable {

    /**
     * 维权进度-待受理
     */
    public static final int RIGHTS_STATUS_TO_ACCEPT = 1;

    /**
     * 维权进度-已受理
     */
    public static final int RIGHTS_STATUS_HAVE_ACCEPT = 2;

    /**
     * 维权进度-已退回
     */
    public static final int RIGHTS_STATUS_RETURNED = 3;

    /**
     * 维权进度-维权中
     */
    public static final int RIGHTS_STATUS_THE_RIGHTS_OF = 4;

    /**
     * 维权进度-维权成功
     */
    public static final int RIGHTS_STATUS_SUCCESS = 5;

    /**
     * 维权进度-维权失败
     */
    public static final int RIGHTS_STATUS_FAILURE = 6;

    /**
     * 维权进度-已撤回
     */
    public static final int RIGHTS_STATUS_RECALL = 7;

    public static String getRights(final int reghtsStatus) {
        switch (reghtsStatus) {
        case RIGHTS_STATUS_TO_ACCEPT:
            return "待受理";
        case RIGHTS_STATUS_HAVE_ACCEPT:
            return "已受理";
        case RIGHTS_STATUS_RETURNED:
            return "已退回";
        case RIGHTS_STATUS_THE_RIGHTS_OF:
            return "维权中";
        case RIGHTS_STATUS_SUCCESS:
            return "维权成功";
        case RIGHTS_STATUS_FAILURE:
            return "维权失败";
        case RIGHTS_STATUS_RECALL:
            return "已撤回";
        }
        return "";
    }

    private String id;

    private User user;

    private String goodsUrl;

    private String worksName;

    private int rightsProtectionStatus;

    private Date submitTime;

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

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(final String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(final String worksName) {
        this.worksName = worksName;
    }

    public int getRightsProtectionStatus() {
        return rightsProtectionStatus;
    }

    public void setRightsProtectionStatus(final int rightsProtectionStatus) {
        this.rightsProtectionStatus = rightsProtectionStatus;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(final Date submitTime) {
        this.submitTime = submitTime;
    }
}
