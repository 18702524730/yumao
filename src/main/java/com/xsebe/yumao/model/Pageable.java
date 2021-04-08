package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.List;

public final class Pageable<E extends Serializable> {

    public Pageable(final int pageSize, final long pageTotal, final List<E> currentPageList, final long dataTotal) {
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.currentPageList = currentPageList;
        this.dataTotal = dataTotal;
    }

    public Pageable() {
    }

    private int pageSize;

    private long pageTotal;

    private List<E> currentPageList;

    private long dataTotal;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(final long pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<E> getCurrentPageList() {
        return currentPageList;
    }

    public void setCurrentPageList(final List<E> currentPageList) {
        this.currentPageList = currentPageList;
    }

    public long getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(final long dataTotal) {
        this.dataTotal = dataTotal;
    }
}
