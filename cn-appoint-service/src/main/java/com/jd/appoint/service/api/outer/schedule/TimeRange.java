package com.jd.appoint.service.api.outer.schedule;

/**
 * Created by luqiang3 on 2018/5/23.
 * 时间范围
 */
public class TimeRange {

    /**
     * 开始时间
     */
    private int start;
    /**
     * 结束时间
     */
    private int end;

    //get set
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
