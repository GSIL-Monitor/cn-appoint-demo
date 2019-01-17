package com.jd.appoint.shopapi.vo;

import javax.validation.constraints.NotNull;

/**
 * 预约信息查询
 * Created by gaoxiaoqing on 2018/5/16.
 */
public class AppointRecordQueryVO {
    /**
     * 预约单号
     */
    @NotNull
    private String appointOrderId;

    public String getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(String appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

}
