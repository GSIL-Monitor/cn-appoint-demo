package com.jd.appoint.domain.rpc;

/**
 * Created by gaoxiaoqing on 2018/7/3.
 */
public class ServerTypeInfo {
    /**
     * 到店名称
     */
    private String toShopName;
    /**
     * 到家名称
     */
    private String toHomeName;

    public String getToShopName() {
        return toShopName;
    }

    public void setToShopName(String toShopName) {
        this.toShopName = toShopName;
    }

    public String getToHomeName() {
        return toHomeName;
    }

    public void setToHomeName(String toHomeName) {
        this.toHomeName = toHomeName;
    }
}
