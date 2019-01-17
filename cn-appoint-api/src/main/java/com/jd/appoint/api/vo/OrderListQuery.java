package com.jd.appoint.api.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by gaoxiaoqing on 2018/6/26.
 */
public class OrderListQuery {
    /**
     * 客户userPin
     */
    @NotNull
    private String customerUserPin;
    /**
     * 页码，页码从1开始
     */
    @NotNull
    @Min(1)
    private Integer pageNumber;
    /**
     * 一页大小
     */
    @NotNull
    private Integer pageSize;

    /**
     * 业务Code
     */
    private String businessCode;

    public String getCustomerUserPin() {
        return customerUserPin;
    }

    public void setCustomerUserPin(String customerUserPin) {
        this.customerUserPin = customerUserPin;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
