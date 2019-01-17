package com.jd.appoint.service.mq.msg;

/**
 * 预约单变更
 * Created by gaoxiaoqing on 2018/5/21.
 */
public class AppointChangeNoticeMo {
    /**
     * 预约单号
     */
    private Long appointOrderId;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }
}
