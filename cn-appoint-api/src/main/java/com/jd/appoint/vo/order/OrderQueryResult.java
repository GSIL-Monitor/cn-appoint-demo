package com.jd.appoint.vo.order;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/3.
 * 订单明细结果
 */
public class OrderQueryResult implements Serializable {

    /**
     * 预约单号
     */
    private Long appointOrderId;

    /**
     * 京东商家编号
     */
    @NotNull
    private Long venderId;

    /**
     * 用户姓名
     */
    @NotNull
    private String userPin;

    /**
     * SKU编号
     */
    @NotNull
    private Long skuId;

    /**
     * 联系人姓名
     */
    @NotNull
    private String contactName;

    /**
     * 联系人电话
     */
    @NotNull
    private String contactPhone;

    /**
     * 地址
     */
    private String address;

    /**
     * 服务开始时间，格式：yyyy-MM-dd hh:mm
     */
    @NotNull
    private String startTime;

    /**
     * 服务结束时间，格式：yyyy-MM-dd hh:mm
     */
    private String endTime;

    /**
     * 备注
     */
    private String memo;

    /**
     * 预约单状态，1.新订单；2.待派单；3.待服务；8.预约取消；9.预约完成
     */
    private Integer status;

    /**
     * 扩展信息，JSON格式
     */
    private String extendInfo;

    /**
     * 指派的服务人员信息
     */
    private Staff staff;

    /**
     * 附件信息
     */
    private Attach attach;

    //get set
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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Attach getAttach() {
        return attach;
    }

    public void setAttach(Attach attach) {
        this.attach = attach;
    }

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }
}
