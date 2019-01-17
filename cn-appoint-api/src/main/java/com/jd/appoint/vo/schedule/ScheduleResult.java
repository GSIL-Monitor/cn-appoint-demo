package com.jd.appoint.vo.schedule;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiang3 on 2018/6/15.
 */
public class ScheduleResult implements Serializable{

    /**
     * 日期日历
     */
    private List<DateItem> dateItems;

    /**
     * 日期对应的时间项
     */
    private Map<String, List<TimeItem>> timeItems;

    @Override
    public String toString() {
        return "ScheduleResult{" +
                "dateItems=" + dateItems +
                ", timeItems=" + timeItems +
                '}';
    }

    //get set
    public List<DateItem> getDateItems() {
        return dateItems;
    }

    public void setDateItems(List<DateItem> dateItems) {
        this.dateItems = dateItems;
    }

    public Map<String, List<TimeItem>> getTimeItems() {
        return timeItems;
    }

    public void setTimeItems(Map<String, List<TimeItem>> timeItems) {
        this.timeItems = timeItems;
    }
}
