package com.jd.appoint.vo.order;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * Created by lishuaiwei on 2018/6/20.
 */
public class DZXAppointOrderDetailVO {

    /**
     * 中文预约单状态
     * 待审核
     *
     */
    private String appointStatus;

    /**
     * 服务类型
     */
    private String serverType;

    /**
     * 商品skuName
     */
    private String skuName;

    /**
     * 收货人/提货人
     */
    private String customerName;

    /**
     * 联系方式
     */
    private String customerPhone;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 门店地址
     */
    private String storeAddress;

    /**
     * 期望发货时间/自提时间
     */
    private Date expectGetTime;

    /**
     * 预约单号
     */
    private Long appointOrderId;

    /**
     * 预约时间
     */
    private Date appointTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 凭证卡号
     */
    private String cardNo;

    public String getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(String appointStatus) {
        this.appointStatus = appointStatus;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Date getExpectGetTime() {
        return expectGetTime;
    }

    public void setExpectGetTime(Date expectGetTime) {
        this.expectGetTime = expectGetTime;
    }

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
