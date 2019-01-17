package com.jd.appoint.shopapi.vo;

import com.jd.appoint.vo.CommonRequest;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 预约单更新对应的VO
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/9 14:58
 */
public class ShopAppointUpdateVO extends CommonRequest implements Serializable {
    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;
    /**
     * 预约开始时间
     */
    @NotNull
    private Date appointStartTime;
    /**
     * 预约结束时间
     */
    @NotNull
    private Date appointEndTime;
    /**
     * 预约类型
     */
    @NotNull
    @Max(2)
    private Integer serverType;
    /**
     * 客户姓名
     */
    @Length(max = 100)
    @NotNull
    private String customerName;

    /**
     * 客户手机号(不校验)
     */
    @NotNull
    private String customerPhone;

    /**
     * 员工ID
     */
    @NotNull
    private Long staffId;

    /**
     * 商家id
     */
    @NotNull
    private Long venderId;

    /**
     * 动态配置项
     */
    private Map<String, String> formItems;

    /**
     * 操作人员
     */
    @NotNull
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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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

    public String getOperateUserPin() {
        return operateUserPin;
    }

    public void setOperateUserPin(String operateUserPin) {
        this.operateUserPin = operateUserPin;
    }
}
