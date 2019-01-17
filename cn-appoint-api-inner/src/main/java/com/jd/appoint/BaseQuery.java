package com.jd.appoint;

public class BaseQuery {

    /**
     * 起始页面
     */
    protected int pageNo=1;
    /**
     * 页面大小
     */
    protected int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
