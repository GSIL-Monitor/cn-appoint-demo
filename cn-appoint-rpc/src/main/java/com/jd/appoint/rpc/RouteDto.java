package com.jd.appoint.rpc;

/**
 * Created by yangyuan on 8/2/18.
 */
public class RouteDto {

    private Long orderId;

    private String shipId;

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
        return "RouteDto{" +
                "orderId=" + orderId +
                ", shipId='" + shipId + '\'' +
                ", thirdId=" + thirdId +
                '}';
    }
}

