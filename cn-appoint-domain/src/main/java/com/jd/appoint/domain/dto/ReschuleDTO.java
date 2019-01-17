package com.jd.appoint.domain.dto;

import com.jd.appoint.domain.enums.PlatformEnum;


import java.util.Date;

/**
 * 改期
 * Created by gaoxiaoqing on 2018/7/2.
 */
public class ReschuleDTO {
    /**
     * 预约单号
     */
    private Long appointOrderId;
    /**
     * 预约开始时间
     */
    private Date appointStartTime;
    /**
     * 预约结束时间
     */
    private Date appointEndTime;
    /**
     * 商家ID
     */
    private Long venderId;
    /**
     * 用户Pin
     */
    private String userPin;

    /**
     * 平台来源
     */
    private PlatformEnum platformEnum;
    /**
     * 门店Code
     */
    private String storeCode;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public Date getAppointStartTime() {
        return appointStartTime;
    }

    public void setAppointStartTime(Date appointStartTime) {
        this.appointStartTime = appointStartTime;
    }

    public Date getAppointEndTime() {
        return appointEndTime;
    }

    public void setAppointEndTime(Date appointEndTime) {
        this.appointEndTime = appointEndTime;
    }

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

    public PlatformEnum getPlatformEnum() {
        return platformEnum;
    }

    public void setPlatformEnum(PlatformEnum platformEnum) {
        this.platformEnum = platformEnum;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
