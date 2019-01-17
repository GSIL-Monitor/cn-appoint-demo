package com.jd.appoint.vo.order;

import javax.validation.constraints.NotNull;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/17 21:54
 */
public class MailInformation {
    /**
     * 物流公司
     */
    @NotNull
    private String logisticsSource;
    /**
     * 物流单号
     */
    @NotNull
    private String logisticsNo;
    /**
     * 站点ID
     */
    @NotNull
    private Integer logisticsSiteId;


    public String getLogisticsSource() {
        return logisticsSource;
    }

    public void setLogisticsSource(String logisticsSource) {
        this.logisticsSource = logisticsSource;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Integer getLogisticsSiteId() {
        return logisticsSiteId;
    }

    public void setLogisticsSiteId(Integer logisticsSiteId) {
        this.logisticsSiteId = logisticsSiteId;
    }
}
