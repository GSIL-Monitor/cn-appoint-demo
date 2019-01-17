package com.jd.appoint.vo.staff;


import com.jd.appoint.vo.page.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StoreStaffQueryVO implements Serializable {
    /**
     * 商家ID
     */
    @NotNull
    private Long venderId;

    /**
     * 门店ID
     */
    @NotNull
    private Long storeId;

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
     * 分页参数
     */
    @Valid
    private Page page;

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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
