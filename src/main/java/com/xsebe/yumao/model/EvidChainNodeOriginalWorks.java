package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class EvidChainNodeOriginalWorks implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 证据链节点
     */
    private EvidChain evidChain;
    /**
     * 证据链分类节点
     */
    private EvidChainCategoryNode evidChainCategoryNode;
    /**
     * 原创作品
     */
    private OriginalWorks originalWorks;
    /**
     * 排序
     */
    private int sort;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 删除标记。1，已删除；2，未删除
     */
    private int deleteFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EvidChain getEvidChain() {
        return evidChain;
    }

    public void setEvidChainNode(EvidChain evidChain) {
        this.evidChain = evidChain;
    }

    public EvidChainCategoryNode getEvidChainCategoryNode() {
        return evidChainCategoryNode;
    }

    public void setEvidChainCategoryNode(EvidChainCategoryNode evidChainCategoryNode) {
        this.evidChainCategoryNode = evidChainCategoryNode;
    }

    public OriginalWorks getOriginalWorks() {
        return originalWorks;
    }

    public void setOriginalWorks(OriginalWorks originalWorks) {
        this.originalWorks = originalWorks;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

}
