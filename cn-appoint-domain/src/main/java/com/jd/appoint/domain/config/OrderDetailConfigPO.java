package com.jd.appoint.domain.config;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.enums.StatusEnum;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public class OrderDetailConfigPO extends BaseEntity {
    /**
     * 前端展示文案
     */
    private String label;
    /**
     * 别名
     */
    private String alias;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 业务编码
     */
    private String businessCode;
    /**
     * 平台 1：商家 2:门店 3：C端
     */
    private PlatformEnum platform;
    /**
     * 服务类型： 到家(1),到店(2)
     */
    private ServerTypeEnum serverType;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ServerTypeEnum getServerType() {
        return serverType;
    }

    public void setServerType(ServerTypeEnum serverType) {
        this.serverType = serverType;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public PlatformEnum getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformEnum platform) {
        this.platform = platform;
    }
}
