package com.jd.appoint.rpc.card.dto;

/**
 * sku商品信息
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/25 17:43
 */
public class SkuInfoDTO {
    private Long skuId;
    private String skuName;
    private Long orderId;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
