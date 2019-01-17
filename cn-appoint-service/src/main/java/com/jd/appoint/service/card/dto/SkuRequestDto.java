package com.jd.appoint.service.card.dto;

import java.io.Serializable;

/**
 * 通过sku获得商品信息的请求
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/25 11:23
 */
public class SkuRequestDto implements Serializable {
    /**
     * 业务代码
     */
    private String businessCode;
    /**
     * 商家ID
     */
    private Long venderId;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡密
     */
    private String cardPassword;


    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
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
}
