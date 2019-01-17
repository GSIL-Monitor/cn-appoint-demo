package com.jd.appoint.domain.shop.query;

import com.jd.appoint.domain.enums.StatusEnum;

public class ShopStaffQueryPO {
    /**
     * 商家ID
     */
    private Long venderId;

    /**
     * 服务人员京东账号
     */
    private String userPin;

    /**
     * 服务人员姓名
     */
    private String serverName;

    /**
     * 服务人员电话
     */
    private String serverPhone;

    /**
     * 可用状态 1有效 2无效 9删除
     */
    private StatusEnum status;

    private Integer pageNumber;

    private Integer pageSize;

    private Long storeId;

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPhone() {
        return serverPhone;
    }

    public void setServerPhone(String serverPhone) {
        this.serverPhone = serverPhone;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
