package com.jd.appoint.service.api.outer.schedule;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luqiang3 on 2018/6/23.
 */
public class ScheduleWorkTime implements Serializable{

    private String date;
    private Boolean dateStatus;
    private List<TimeRange> timeRanges;

    //get set
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(Boolean dateStatus) {
        this.dateStatus = dateStatus;
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public void setTimeRanges(List<TimeRange> timeRanges) {
        this.timeRanges = timeRanges;
    }
}
