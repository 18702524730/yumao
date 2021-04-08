package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class EvidChain implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 证据链分类
     */
    private EvidChainCategory evidChainCategory;
    /**
     * 证据链分类名称
     */
    private String categoryName;
    /**
     * 名称
     */
    private String name;
    /**
     * 完整度百分比
     */
    private float integrityPercent;
    /**
     * 状态。1，xxx
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 删除标记。1，已删除；2，未删除
     */
    private int deleteFlag;
    /**
     * 创建用户
     */
    private User user;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public EvidChainCategory getEvidChainCategory() {
        return evidChainCategory;
    }
    public void setEvidChainCategory(EvidChainCategory evidChainCategory) {
        this.evidChainCategory = evidChainCategory;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getIntegrityPercent() {
        return integrityPercent;
    }
    public void setIntegrityPercent(float integrityPercent) {
        this.integrityPercent = integrityPercent;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public int getDeleteFlag() {
        return deleteFlag;
    }
    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
}
