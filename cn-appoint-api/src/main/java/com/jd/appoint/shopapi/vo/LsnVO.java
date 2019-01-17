package com.jd.appoint.shopapi.vo;

import javax.validation.constraints.NotNull;

/**
 * 物流单号的bean
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/21 9:57
 */
public class LsnVO {
    /**
     * 商品订单号
     */
    private Long orderId;
    /**
     * 物流公司名称
     */
    @NotNull
    private String logisticsSource;
    /**
     * 物流公司站点ID
     */
    @NotNull
    private Integer logisticsSiteId;
    /**
     * 物流单号
     */
    @NotNull
    private String logisticsNo;
    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLogisticsSource() {
        return logisticsSource;
    }

    public void setLogisticsSource(String logisticsSource) {
        this.logisticsSource = logisticsSource;
    }

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
