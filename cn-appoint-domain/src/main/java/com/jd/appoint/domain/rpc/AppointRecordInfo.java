package com.jd.appoint.domain.rpc;

import java.util.Date;

/**
 * 预约单记录
 * Created by gaoxiaoqing on 2018/5/9.
 */
public class AppointRecordInfo {

    /**
     * 记录生成日期
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
}
