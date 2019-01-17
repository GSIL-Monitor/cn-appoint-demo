package com.jd.appoint.api.vo;


import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by gaoxiaoqing on 2018/7/2.
 */
public class ApiReschuleVO {
    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;
    /**
     * 预约开始时间
     * 日期类型：只传开始日期
     * 时间槽类型：格式：yyyy-MM-dd hh:mm
     */
    @NotNull
    private Date appointStartTime;
    /**
     * 预约结束时间
     */
    private Date appointEndTime;
    /**
     * 用户Pin
      */
    @NotNull
    private String userPin;

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

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }
}
