package com.jd.appoint.domain.rpc;

/**
 * 接入者状态
 * Created by gaoxiaoqing on 2018/5/4.
 */
public class AccessorInfo {
    /**
     * 服务端状态
     */
    private String severStatusName;
    /**
     * 客户端状态
     */
    private String clientStatusName;
    /**
     * 门店名称
     */
    private String storeStatusName;

    public String getSeverStatusName() {
        return severStatusName;
    }

    public void setSeverStatusName(String severStatusName) {
        this.severStatusName = severStatusName;
    }

    public String getClientStatusName() {
        return clientStatusName;
    }

    public void setClientStatusName(String clientStatusName) {
        this.clientStatusName = clientStatusName;
    }

    public String getStoreStatusName() {
        return storeStatusName;
    }

    public void setStoreStatusName(String storeStatusName) {
        this.storeStatusName = storeStatusName;
    }
}
