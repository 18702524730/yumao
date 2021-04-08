package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EvidChainCategoryNode implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 证据链分类
     */
    private EvidChainCategory evidChainCategory;
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

}
