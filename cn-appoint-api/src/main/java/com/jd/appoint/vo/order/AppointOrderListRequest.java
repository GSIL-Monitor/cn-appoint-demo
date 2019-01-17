package com.jd.appoint.vo.order;

import com.google.common.collect.Maps;
import com.jd.appoint.vo.CommonRequest;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.page.Sort;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/5/17.
 */
public class AppointOrderListRequest extends CommonRequest {
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
    @Min(1)
    private Integer pageSize;

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

    public Page<AppointOrderDetailVO> toPage() {
        Page<AppointOrderDetailVO> result = new Page<>();
        result.setPageNumber(this.pageNumber);
        result.setPageSize(this.pageSize);
        Map<String, String> searchMap = Maps.newHashMap();
        searchMap.put("customerUserPin.EQ", this.customerUserPin);
        searchMap.put("businessCode.EQ", this.businessCode);
        result.setSearchMap(searchMap);
        return result;
    }
}
