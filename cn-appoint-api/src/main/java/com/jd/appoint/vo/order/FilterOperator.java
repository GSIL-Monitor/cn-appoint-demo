package com.jd.appoint.vo.order;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/13.
 * 支持的筛选操作
 */
public enum FilterOperator {

    EQ("等于"),
    CONTAIN("包含"),
    GTE("大于等于"),
    GT("大于"),
    LT("小于"),
    LTE("小于等于"),
    IN("IN");
    private String description;

    FilterOperator(String description) {
        this.description = description;
    }
}
