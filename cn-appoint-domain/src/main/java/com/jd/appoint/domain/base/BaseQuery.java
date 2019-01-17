package com.jd.appoint.domain.base;

/**
 * Created by lijianzhen1 on 2017/3/16.
 */
public class BaseQuery {
    /**
     * 起始页面
     */
    protected int pageNo=1;
    /**
     * 页面大小
     */
    protected int pageSize;
    /**
     * 排序 desc或asc
     */
    protected String orderByClause;

    /**
     * 是否去重
     */
    protected boolean distinct;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if (pageSize == 0)
            pageSize = 20;
        return pageSize;
    }

    public void setPageSize(int pageSize) {

        this.pageSize = pageSize;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
}
