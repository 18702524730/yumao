package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class EvidChainNode implements Serializable{

    /**
     * id
     */
    private String id;
    /**
     * 证据链
     */
    private EvidChain evidChain;
    /**
     * 证据链分类节点
     */
    private EvidChainCategoryNode evidChainCategoryNode;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否主图。1，是；2，否
     */
    private int isMasterPic;
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
    public void setEvidChain(EvidChain evidChain) {
        this.evidChain = evidChain;
    }
    public EvidChainCategoryNode getEvidChainCategoryNode() {
        return evidChainCategoryNode;
    }
    public void setEvidChainCategoryNode(EvidChainCategoryNode evidChainCategoryNode) {
        this.evidChainCategoryNode = evidChainCategoryNode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIsMasterPic() {
        return isMasterPic;
    }
    public void setIsMasterPic(int isMasterPic) {
        this.isMasterPic = isMasterPic;
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
