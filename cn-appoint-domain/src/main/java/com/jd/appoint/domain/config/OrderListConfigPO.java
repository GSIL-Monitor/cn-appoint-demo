package com.jd.appoint.domain.config;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.*;

import java.util.Date;

public class OrderListConfigPO extends BaseEntity {
    /**
     * 前端展示文案
     */
    private String label;
    /**
     * 别名
     */
    private String alias;
    /**
     * 优先级 越大越靠前
     */
    private Integer priority;
    /**
     * 参与查询 0 否 1是
     */
    private Integer inSearch;
    /**
     * 参与列表项 0 否 1是
     */
    private Integer inList;
    /**
     * 查询类型 EQ
     */
    private QueryTypeEnum queryType;
    /**
     * 业务类型
     */
    private String businessCode;
    /**
     * 控件类型 1：文本框 2：下拉框，3：日期 4:日期双选 5:动态下拉框
     */
    private InputTypeEnum inputType;
    /**
     * 控件内容json
     */
    private String itemData;
    /**
     * 平台 1：商家 2:门店 3：C端
     */
    private PlatformEnum platform;
    /**
     * 行号
     */
    private Integer lineNo;
    /**
     * 长度 单位PX
     */
    private Integer width;
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

    public Integer getInSearch() {
        return inSearch;
    }

    public void setInSearch(Integer inSearch) {
        this.inSearch = inSearch;
    }

    public Integer getInList() {
        return inList;
    }

    public void setInList(Integer inList) {
        this.inList = inList;
    }

    public QueryTypeEnum getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryTypeEnum queryType) {
        this.queryType = queryType;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public InputTypeEnum getInputType() {
        return inputType;
    }

    public void setInputType(InputTypeEnum inputType) {
        this.inputType = inputType;
    }

    public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    public PlatformEnum getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformEnum platform) {
        this.platform = platform;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
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
}