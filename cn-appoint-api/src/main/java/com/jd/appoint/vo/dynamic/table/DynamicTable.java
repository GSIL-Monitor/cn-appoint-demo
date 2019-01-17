package com.jd.appoint.vo.dynamic.table;

import java.util.List;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/6/26.
 */
public class DynamicTable {
    /**
     * 表头
     */
    private List<Th> thList;
    /**
     * 数据
     */
    private List<Map<String, String>> dataSource;
    /**
     * 一页大小
     */
    private int pageSize;
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 总条数
     */
    private long total;

    public List<Th> getThList() {
        return thList;
    }

    public void setThList(List<Th> thList) {
        this.thList = thList;
    }

    public List<Map<String, String>> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<Map<String, String>> dataSource) {
        this.dataSource = dataSource;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
