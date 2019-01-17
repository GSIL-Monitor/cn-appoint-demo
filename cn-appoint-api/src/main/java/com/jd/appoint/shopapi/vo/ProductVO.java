package com.jd.appoint.shopapi.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by yangyuan on 6/15/18.
 */
public class ProductVO {

    @NotNull
    private Long id;

    private String name;

    private Long skuId;

    private StatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ProductVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skuId=" + skuId +
                ", status=" + status +
                '}';
    }
}
