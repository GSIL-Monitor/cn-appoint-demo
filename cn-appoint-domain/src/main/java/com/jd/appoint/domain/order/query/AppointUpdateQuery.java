package com.jd.appoint.domain.order.query;

import java.util.Date;
import java.util.Map;

/**
 * Created by gaoxiaoqing on 2018/5/28.
 */
public class AppointUpdateQuery {

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
     * 预约类型
     */
    private Integer serverType;
    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户手机号(不校验)
     */
    private String customerPhone;

    /**
     * 员工ID
     */
    private Long staffId;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡密
     */
    private String cardPassword;

    /**
     * 商家id
     */
    private Long venderId;

    /**
     * 动态配置项
     */
    private Map<String,String> formItems;
    /**
     * 业务Code
     */
    private String businessCode;
    /**
     * 操作人员
     */
    private String operateUserPin;

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

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Map<String, String> getFormItems() {
        return formItems;
    }

    public void setFormItems(Map<String, String> formItems) {
        this.formItems = formItems;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getOperateUserPin() {
        return operateUserPin;
    }

    public void setOperateUserPin(String operateUserPin) {
        this.operateUserPin = operateUserPin;
    }
}
