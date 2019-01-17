package com.jd.appoint.service.card.dto;

import java.io.Serializable;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/25 14:52
 */
public class SkuRespDto implements Serializable {
    private Long skuId;
    private String skuName;
    private Long relatedErpOrderId;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getRelatedErpOrderId() {
        return relatedErpOrderId;
    }

    public void setRelatedErpOrderId(Long relatedErpOrderId) {
        this.relatedErpOrderId = relatedErpOrderId;
    }
}
