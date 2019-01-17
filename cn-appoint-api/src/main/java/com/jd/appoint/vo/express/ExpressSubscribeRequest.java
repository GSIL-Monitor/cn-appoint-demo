package com.jd.appoint.vo.express;

import javax.validation.constraints.NotNull;

/**
 * Created by yangyuan on 6/27/18.
 */
public class ExpressSubscribeRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private String shipId;

    @NotNull
    private Integer thirdId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public Integer getThirdId() {
        return thirdId;
    }

    public void setThirdId(Integer thirdId) {
        this.thirdId = thirdId;
    }

    @Override
    public String toString() {
        return "ExpressSubscribeRequest{" +
                "orderId=" + orderId +
                ", shipId='" + shipId + '\'' +
                ", thirdId=" + thirdId +
                '}';
    }
}
