package com.jd.appoint.domain.rpc;

/**
 * Created by gaoxiaoqing on 2018/7/3.
 */
public class AppointServerInfo {
    /**
     * 业务Code
     */
    private String businessCode;
    /**
     * 服务类型信息
     */
    private ServerTypeInfo serverTypeInfo;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public ServerTypeInfo getServerTypeInfo() {
        return serverTypeInfo;
    }

    public void setServerTypeInfo(ServerTypeInfo serverTypeInfo) {
        this.serverTypeInfo = serverTypeInfo;
    }
}
