package com.jd.appoint.shopapi.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by yangyuan on 6/27/18.
 */
public class SkuOperateRequest {

    @NotNull
    private Long shopId;

    @NotNull
    private Long id;

    private  StatusEnum status;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SkuOperateRequest{" +
                "shopId=" + shopId +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
