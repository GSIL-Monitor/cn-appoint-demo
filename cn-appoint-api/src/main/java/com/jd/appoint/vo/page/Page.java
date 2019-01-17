package com.jd.appoint.vo.page;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/5/9.
 */
public class Page<T> {
    /**
     * 结果
     */
    private List<T> list;
    /**
     * 页码，从1开始
     */
    @Min(1L)
    private int pageNumber;
    /**
     * 一页大小
     */
    @Min(1L)
    private int pageSize;
    /**
     * 总条数
     */
    private long totalCount;
    /**
     * 查询条件
     */
    private Map<String, String> searchMap;
    /**
     * 排序条件
     */
    private List<Sort> sorts;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }
}
