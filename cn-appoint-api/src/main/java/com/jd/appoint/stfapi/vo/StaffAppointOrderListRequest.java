package com.jd.appoint.stfapi.vo;

import com.google.common.collect.Maps;
import com.jd.appoint.vo.CommonRequest;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.order.FilterItemVO;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.page.Sort;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/5/30.
 */
public class StaffAppointOrderListRequest extends CommonRequest {
    /**
     * 技师userPin
     */
    @NotNull
    private String staffUserPin;
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
    /**
     * 筛选项
     */
    @Valid
    private List<FilterItemVO> filterItemVOS;
    /**
     * 排序条件
     */
    @Valid
    private List<Sort> sortList;

    public List<FilterItemVO> getFilterItemVOS() {
        return filterItemVOS;
    }

    public void setFilterItemVOS(List<FilterItemVO> filterItemVOS) {
        this.filterItemVOS = filterItemVOS;
    }

    public List<Sort> getSortList() {
        return sortList;
    }

    public void setSortList(List<Sort> sortList) {
        this.sortList = sortList;
    }

    public String getStaffUserPin() {
        return staffUserPin;
    }

    public void setStaffUserPin(String staffUserPin) {
        this.staffUserPin = staffUserPin;
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

}
