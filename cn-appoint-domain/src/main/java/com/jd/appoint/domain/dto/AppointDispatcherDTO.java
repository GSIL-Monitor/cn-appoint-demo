package com.jd.appoint.domain.dto;


import java.util.Date;

/**
 * 预约派单
 * Created by gaoxiaoqing on 2018/6/2.
 */
public class AppointDispatcherDTO {

    /**
     * 预约单号
     */
    private Long appointOrderId;

    /**
     * 员工ID
     */
    private Long staffId;
    /**
     * 预约开始时间
     */
    private Date appointStartTime;
    /**
     * 预约结束时间
     */
    private Date appointEndTime;

    /**
     * 预约状态
     */
    private Integer appointStatus;

    /**
     * 操作人员
     */
    private String operateUserPin;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public Integer getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(Integer appointStatus) {
        this.appointStatus = appointStatus;
    }

    public String getOperateUserPin() {
        return operateUserPin;
    }

    public void setOperateUserPin(String operateUserPin) {
        this.operateUserPin = operateUserPin;
    }
}
