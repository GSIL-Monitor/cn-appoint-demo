package com.jd.appoint.domain.shop;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;

public class ShopStaffPO extends BaseEntity {
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
     * 可用状态 1可用 2不可用 9 删除
     */
    private StatusEnum status;

    /**
     * 户簿唯一id
     */
    private String securityId;

    /**
     * 门店ID
     */
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

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
