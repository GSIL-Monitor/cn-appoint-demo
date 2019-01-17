package com.jd.appoint.domain.card;

import com.jd.appoint.domain.base.BaseEntity;

import java.util.Date;

/**
 * @author lijianzhen1
 */
public class AppointCardPO extends BaseEntity {

    /**
     * 卡号
     * 表字段 : appoint_card.card_number
     */
    private String cardNumber;

    /**
     * 密码
     * 表字段 : appoint_card.card_pwd
     */
    private String cardPwd;

    /**
     * 订单号
     * 表字段 : appoint_card.order_id
     */
    private Long orderId;

    /**
     * 用户的pin
     * 表字段 : appoint_card.user_pin
     */
    private String userPin;

    /**
     * 卡类型
     * 表字段 : appoint_card.card_type
     */
    private Integer cardType;

    /**
     * 卡状态
     * 表字段 : appoint_card.card_status
     */
    private Integer cardStatus;

    /**
     * 收件手机号
     * 表字段 : appoint_card.receive_phone
     */
    private String receivePhone;

    /**
     * 业务代码
     * 表字段 : appoint_card.business_code
     */
    private String businessCode;
    /**
     * 商家Id
     * 表字段 : appoint_card.vender_id
     */
    private Long venderId;

    /**
     * skuID
     * 表字段 : appoint_card.sku_id
     */
    private String skuId;

    /**
     * sku名称
     * 表字段 : appoint_card.sku_name
     * </pre>
     */
    private String skuName;

    /**
     * 支付时间
     * 表字段 : appoint_card.pay_date
     */
    private Date payDate;

    /**
     * 生效日期
     * 表字段 : appoint_card.effect_start_time
     */
    private Date effectStartTime;

    /**
     * 生效截止时间
     * 表字段 : appoint_card.effect_end_time
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
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