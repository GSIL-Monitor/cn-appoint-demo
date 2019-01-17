package com.jd.appoint.domain.dto;

/**
 * Created by luqiang3 on 2018/8/21.
 */
public class OrderShipsDto {

    /**
     * 预约单号
     */
    private Long appointOrderId;
    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 站点编号
     */
    private Integer logisticsSiteId;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
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
