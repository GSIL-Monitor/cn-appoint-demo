package com.jd.appoint.domain.rpc.query;

import javax.validation.constraints.NotNull;

/**
 * 预约记录查询
 * Created by gaoxiaoqing on 2018/5/9.
 */
public class AppointRecordQuery {
    /**
     * 足迹NO（预约单ID）
     */
    @NotNull
    private String appoinOrderId;

    public String getAppoinOrderId() {
        return appoinOrderId;
    }

    public void setAppoinOrderId(String appoinOrderId) {
        this.appoinOrderId = appoinOrderId;
    }
}
