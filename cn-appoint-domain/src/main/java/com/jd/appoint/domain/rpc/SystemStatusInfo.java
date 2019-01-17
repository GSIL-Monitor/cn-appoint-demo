package com.jd.appoint.domain.rpc;

/**
 * 系统状态
 * Created by gaoxiaoqing on 2018/5/4.
 */
public class SystemStatusInfo {

    /**
     * 系统状态code
     */
    private Integer systemStatusCode;

    /**
     * 服务类型
     */
    private ServiceTypeInfo serviceTypeInfo;

    public Integer getSystemStatusCode() {
        return systemStatusCode;
    }

    public void setSystemStatusCode(Integer systemStatusCode) {
        this.systemStatusCode = systemStatusCode;
    }

    public ServiceTypeInfo getServiceTypeInfo() {
        return serviceTypeInfo;
    }

    public void setServiceTypeInfo(ServiceTypeInfo serviceTypeInfo) {
        this.serviceTypeInfo = serviceTypeInfo;
    }
}
