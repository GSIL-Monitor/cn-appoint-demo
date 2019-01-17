package com.jd.appoint.store.domain;

import java.io.Serializable;

/**
 * web页面门店信息展示
 * Created by wangshangyu on 2017/3/21.
 */
public class WebStore implements Serializable {

    private static final long serialVersionUID = 4558993921292750366L;

    /**
     * 门店编号 0:非门店帐号; 大于0:门店编号
     */
    private Long storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 商家编号
     */
    private Long venderId;
    /**
     * 店铺ID
     */
    private Long shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 店铺完整的logo地址
     */
    private String shopLogo;
    /**
     * 商家合同到期时间
     */
    private String vendorContractTime;
    /**
     * 门店是否已经签署授权协议
     */
    private boolean hasAuthorized;

    public WebStore() {
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
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

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getVendorContractTime() {
        return vendorContractTime;
    }

    public void setVendorContractTime(String vendorContractTime) {
        this.vendorContractTime = vendorContractTime;
    }

    public boolean isHasAuthorized() {
        return hasAuthorized;
    }

    public void setHasAuthorized(boolean hasAuthorized) {
        this.hasAuthorized = hasAuthorized;
    }

    @Override
    public String toString() {
        return "WebStore{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", venderId=" + venderId +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopLogo='" + shopLogo + '\'' +
                ", vendorContractTime='" + vendorContractTime + '\'' +
                ", hasAuthorized=" + hasAuthorized +
                '}';
    }
}
