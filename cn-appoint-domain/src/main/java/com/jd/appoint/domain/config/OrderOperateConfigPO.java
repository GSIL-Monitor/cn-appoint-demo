package com.jd.appoint.domain.config;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.*;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public class OrderOperateConfigPO extends BaseEntity {
    /**
     * 前端展示文案
     */
    private String label;
    /**
     * 预约单状态
     */
    private AppointStatusEnum appointStatus;
    /**
     * 操作类型
     */
    private OperateTypeEnum operateType;
    /**
     * 业务类型
     */
    private String businessCode;
    /**
     * 自定义操作对应的配置，打卡链接是url,div是divID
     */
    private String customData;
    /**
     * 自定义类型
     */
    private CustomTypeEnum customType;
    /**
     * 平台 1：商家 2:门店 3：C端
     */
    private PlatformEnum platform;
    /**
     * 0不是批量操作 1:批量操作
     */
    private Integer isBatch;
    /**
     * 优先级 越大越靠前
     */
    private Integer priority;
    /**
     * 服务类型
     */
    private ServerTypeEnum serverType;
    /**
     * 确认文案
     */
    private String confirmInfo;
    /**
     * 状态
     */
    private StatusEnum status;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AppointStatusEnum getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(AppointStatusEnum appointStatus) {
        this.appointStatus = appointStatus;
    }

    public OperateTypeEnum getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateTypeEnum operateType) {
        this.operateType = operateType;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public CustomTypeEnum getCustomType() {
        return customType;
    }

    public void setCustomType(CustomTypeEnum customType) {
        this.customType = customType;
    }

    public PlatformEnum getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformEnum platform) {
        this.platform = platform;
    }

    public Integer getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(Integer isBatch) {
        this.isBatch = isBatch;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ServerTypeEnum getServerType() {
        return serverType;
    }

    public void setServerType(ServerTypeEnum serverType) {
        this.serverType = serverType;
    }

    public String getConfirmInfo() {
        return confirmInfo;
    }

    public void setConfirmInfo(String confirmInfo) {
        this.confirmInfo = confirmInfo;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
