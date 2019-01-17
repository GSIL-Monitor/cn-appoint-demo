package com.jd.appoint.service.mq.msg;

import java.util.Date;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 20:06
 */
public class AppointNoticeMsg {
    private Long appointId;
    private Integer appointStatus;
    private Date occurrenceTime;


    public AppointNoticeMsg(Long appointId, Integer appointStatus) {
        this.appointId = appointId;
        this.appointStatus = appointStatus;
    }

    public Long getAppointId() {
        return appointId;
    }

    public void setAppointId(Long appointId) {
        this.appointId = appointId;
    }

    public Integer getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(Integer appointStatus) {
        this.appointStatus = appointStatus;
    }


    public Date getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(Date occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

}
