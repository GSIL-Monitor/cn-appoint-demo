package com.jd.appoint.api.vo;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 卡密导入的信息的实体
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/15 17:15
 */
public class CardVO {
    /**
     * 卡密号
     */
    @NotNull
    private String cardNumber;
    /**
     * 卡密密码
     */
    private String cardPwd;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 使用用户的pin
     */
    @NotNull
    private String userPin;
    /**
     * 卡密类型
     */
    private Integer cardType;
    /**
     * 收短信的手机号
     */
    @NotNull
    private String receivePhone;
    /**
     * 商家id
     */
    @NotNull
    private Long venderId;
    /**
     * 业务代码
     */
    @NotNull
    private String businessCode;
    /**
     * skuId
     */
    @NotNull
    private String skuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 支付时间
     */
    private Date payDate;
    /**
     * 生效开始时间
     */
    private Date effectStartTime;
    /**
     * 生效截止时间
     */
    private Date effectEndTime;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getEffectStartTime() {
        return effectStartTime;
    }

    public void setEffectStartTime(Date effectStartTime) {
        this.effectStartTime = effectStartTime;
    }

    public Date getEffectEndTime() {
        return effectEndTime;
    }

    public void setEffectEndTime(Date effectEndTime) {
        this.effectEndTime = effectEndTime;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }
}
