package com.jd.appoint.vo.order;

import javax.validation.constraints.NotNull;

/**
 * Created by shaohongsen on 2018/6/13.
 */
public class FilterItemVO {
    /**
     * 要筛选的属性名称
     */
    @NotNull
    private String fieldName;
    /**
     * 筛选操作
     */
    @NotNull
    private FilterOperator filterOperator;
    /**
     * 要筛选的值
     */
    private String value;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FilterOperator getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(FilterOperator filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
