package com.jd.appoint.store.domain;

import java.io.Serializable;

/**
 * 商家店铺信息
 * Created by wangshangyu on 2017/3/21.
 */
public class VendorShop implements Serializable {

    private static final long serialVersionUID = 2816716872256136670L;

    /**
     * 商家编号
     */
    private Long vendorId;
    /**
     * 店铺编号
     */
    private Long shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 店铺完整的logo标识URI地址（带域名）
     */
    private String shopFullLogoUri;

    public VendorShop() {
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopFullLogoUri() {
        return shopFullLogoUri;
    }

    public void setShopFullLogoUri(String shopFullLogoUri) {
        this.shopFullLogoUri = shopFullLogoUri;
    }

    @Override
    public String toString() {
        return "VendorShop{" +
                "vendorId=" + vendorId +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopFullLogoUri='" + shopFullLogoUri + '\'' +
                '}';
    }
}
