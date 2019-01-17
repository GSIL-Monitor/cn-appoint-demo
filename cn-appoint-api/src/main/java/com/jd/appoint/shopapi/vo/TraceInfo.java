package com.jd.appoint.shopapi.vo;

import java.util.Date;

/**
 * Created by yangyuan on 6/27/18.
 */
public class TraceInfo {

    private Date traceDate;

    private String info;

    public TraceInfo(){}

    public TraceInfo(Date traceDate, String info){
        this.traceDate = traceDate;
        this.info = info;
    }

    public Date getTraceDate() {
        return traceDate;
    }

    public void setTraceDate(Date traceDate) {
        this.traceDate = traceDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "TraceInfo{" +
                "traceDate=" + traceDate +
                ", info='" + info + '\'' +
                '}';
    }
}
