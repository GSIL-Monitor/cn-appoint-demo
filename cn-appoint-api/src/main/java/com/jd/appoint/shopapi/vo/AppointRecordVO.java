package com.jd.appoint.shopapi.vo;

import java.util.Date;

/**
 * 预约记录
 * Created by gaoxiaoqing on 2018/5/16.
 */
public class AppointRecordVO {

    /**
     * 预约单号
     */
    private String appointOrderId;

    /**
     * 记录生成日期
     * 格式：yyyy-MM-dd hh:mm:ss
     */
    private Date generateDate;
    /**
     * 日志内容
     */
    private String recordContent;

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public String getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(String appointOrderId) {
        this.appointOrderId = appointOrderId;
    }
}
