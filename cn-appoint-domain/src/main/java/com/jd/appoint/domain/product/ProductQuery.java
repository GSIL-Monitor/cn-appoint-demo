package com.jd.appoint.domain.product;

/**
 * Created by yangyuan on 6/14/18.
 */
public class ProductQuery {

    private String name;

    private Long skuId;

    private String businessCode;

    private Long venderId;

    private Long storeId;

    private Integer pageSize;

    private Integer offset;

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

    public Integer getPageSize() {
        if(pageSize == null || pageSize == 0){
            return 20;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public static class Builder{

    }

    @Override
    public String toString() {
        return "ProductQuery{" +
                "name='" + name + '\'' +
                ", skuId=" + skuId +
                ", businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", storeId=" + storeId +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                '}';
    }
}
