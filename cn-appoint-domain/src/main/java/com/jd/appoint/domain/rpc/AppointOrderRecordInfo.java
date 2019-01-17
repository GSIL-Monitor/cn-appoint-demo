package com.jd.appoint.domain.rpc;

import java.util.List;

/**
 * 预约单流转记录
 * Created by gaoxiaoqing on 2018/5/11.
 */
public class AppointOrderRecordInfo {

    /**
     * 订单ID（定金）
     */
    private Long erpOrderId;
    /**
     * 预约单流程信息
     */
    private List<AppointRecordInfo> appointOrderRecordList;

    public List<AppointRecordInfo> getAppointOrderRecordList() {
        return appointOrderRecordList;
    }

    public void setAppointOrderRecordList(List<AppointRecordInfo> appointOrderRecordList) {
        this.appointOrderRecordList = appointOrderRecordList;
    }
}
