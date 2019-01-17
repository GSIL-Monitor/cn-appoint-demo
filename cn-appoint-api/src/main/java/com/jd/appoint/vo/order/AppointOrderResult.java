package com.jd.appoint.vo.order;

/**
 * Created by shaohongsen on 2018/5/15.
 */
public class AppointOrderResult {
    /**
     * 预约单id
     */
    private long orderId;
    /**
     * 预约单状态
     */
    private int appointStatus;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(int appointStatus) {
        this.appointStatus = appointStatus;
    }
}
