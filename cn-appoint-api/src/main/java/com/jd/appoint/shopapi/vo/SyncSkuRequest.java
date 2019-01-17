package com.jd.appoint.shopapi.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by yangyuan on 6/27/18.
 */
public class SyncSkuRequest {

    /**
     * 业务类型
     */
    @NotNull
    private Integer businessType;

    /**
     * 商家id
     */
    @NotNull
    private Long venderId;

    /**
     * 店铺id
     */
    private Long storeId;

    private String clientIp;

    private int port;

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "SyncSkuRequest{" +
                "businessType=" + businessType +
                ", venderId=" + venderId +
                ", storeId=" + storeId +
                ", clientIp='" + clientIp + '\'' +
                ", port=" + port +
                '}';
    }
}
