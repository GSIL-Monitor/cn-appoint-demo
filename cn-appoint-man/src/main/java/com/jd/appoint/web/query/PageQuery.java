package com.jd.appoint.web.query;

import com.jd.adminlte4j.model.builder.TableBuilder;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/18 10:11
 */
public class PageQuery {

    protected int currentPage=1;
    protected int pageSize = 20;

    public TableBuilder builder() {
        return TableBuilder.newBuilder(this.getClass()).isPage(true).pageSize(pageSize);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
