package com.jd.appoint.shopapi.vo;

import javax.validation.constraints.NotNull;

/**
 * sku 查询条件
 * Created by yangyuan on 6/15/18.
 */
public class ProductQueryVO {

    private String name;

    private Long skuId;

    @NotNull
    private String businessCode;

    /**
     * 门店 传 门店id
     * 商家 传 商家id
     */
    @NotNull
    private Long shopId;

    private Integer pageSize;

    private Integer pageNum;

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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "ProductQueryVO{" +
                "name='" + name + '\'' +
                ", skuId=" + skuId +
                ", businessCode='" + businessCode + '\'' +
                ", venderId=" + shopId +
                ", pageSize=" + pageSize +
                ", offset=" + pageNum +
                '}';
    }
}
