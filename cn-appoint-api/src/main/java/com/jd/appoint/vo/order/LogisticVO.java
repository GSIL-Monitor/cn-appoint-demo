package com.jd.appoint.vo.order;

/**
 * 物流信息
 * Created by gaoxiaoqing on 2018/7/4.
 */
public class LogisticVO {
    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司站点id
     */
    private Integer logisticsSiteId;

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
