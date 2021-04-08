package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:TODO
 * @date:2019年2月19日 下午1:39:54
 * @author:周伯通
 */
@SuppressWarnings("serial")
public class TortLog implements Serializable {

    /**
     * 已撤回
     */
    public final static String STATUS_RECALL = "已撤回";
    /**
     * 已受理
     */
    public final static String STATUS_ACCEPTED = "已受理";
    /**
     * 已退回
     */
    public final static String STATUS_SEND_BACK = "已退回";
    /**
     * 维权中
     */
    public final static String STATUS_IN_RIGHTS = "维权中";
    /**
     * 维权失败
     */
    public final static String STATUS_FAILURE_RIGHTS = "维权失败";
    /**
     * 维权成功
     */
    public final static String STATUS_SUCCESS_RIGHTS = "维权成功";
    
    private String id;

    private TortInfo tortInfo;

    private int tortInfoStatus;
    
    private String modifyReason;

    private String remark;

    private Date operationTime;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public TortInfo getTortInfo() {
        return tortInfo;
    }

    public void setTortInfo(final TortInfo tortInfo) {
        this.tortInfo = tortInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(final Date operationTime) {
        this.operationTime = operationTime;
    }

    public int getTortInfoStatus() {
        return tortInfoStatus;
    }

    public void setTortInfoStatus(int tortInfoStatus) {
        this.tortInfoStatus = tortInfoStatus;
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(final String modifyReason) {
        this.modifyReason = modifyReason;
    }
    
    

}
