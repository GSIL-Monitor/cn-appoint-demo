package com.jd.appoint.domain.rpc;

/**
 * 服务类型信息
 * Created by gaoxiaoqing on 2018/6/14.
 */
public class ServiceTypeInfo {
    /**
     * 到店状态名称
     */
    private String toShopStatusName;
    /**
     * 到家状态名称
     */
    private String toHomeStatusName;

    public String getToShopStatusName() {
        return toShopStatusName;
    }

    public void setToShopStatusName(String toShopStatusName) {
        this.toShopStatusName = toShopStatusName;
    }

    public String getToHomeStatusName() {
        return toHomeStatusName;
    }

    public void setToHomeStatusName(String toHomeStatusName) {
        this.toHomeStatusName = toHomeStatusName;
    }
}
