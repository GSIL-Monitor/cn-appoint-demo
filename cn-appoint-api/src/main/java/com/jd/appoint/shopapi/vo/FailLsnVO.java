package com.jd.appoint.shopapi.vo;

/**
 * 物流单号的bean
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/21 9:57
 */
public class FailLsnVO {
    /**
     * 物流商家
     */
    private Integer logisticsSiteId;
    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 预约单号
     */
    private Long appointOrderId;

    public Integer getLogisticsSiteId() {
        return logisticsSiteId;
    }

    public void setLogisticsSiteId(Integer logisticsSiteId) {
        this.logisticsSiteId = logisticsSiteId;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }
}
