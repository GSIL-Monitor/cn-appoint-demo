package com.jd.appoint.domain.rpc.query;

import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;

import javax.validation.constraints.NotNull;

/**
 * Created by gaoxiaoqing on 2018/5/7.
 */
public class MappingStatusQuery {
    /**
     * 业务类型
     */
    private String businessCode;
    /**
     * 系统状态
     */
    private AppointStatusEnum appointStatusEnum;
    /**
     * 来源
     */
    private ServerTypeEnum serverTypeEnum;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public AppointStatusEnum getAppointStatusEnum() {
        return appointStatusEnum;
    }

    public void setAppointStatusEnum(AppointStatusEnum appointStatusEnum) {
        this.appointStatusEnum = appointStatusEnum;
    }

    public ServerTypeEnum getServerTypeEnum() {
        return serverTypeEnum;
    }

    public void setServerTypeEnum(ServerTypeEnum serverTypeEnum) {
        this.serverTypeEnum = serverTypeEnum;
    }
}
