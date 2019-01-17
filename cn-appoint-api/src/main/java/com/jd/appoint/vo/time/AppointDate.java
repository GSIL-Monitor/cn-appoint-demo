package com.jd.appoint.vo.time;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/2.
 * 用于表示预约天的信息
 */
public class AppointDate implements Serializable {

    /**
     * 日期，格式：yyyy-MM-dd
     */
    @NotNull
    private String date;

    /**
     * 时间项
     * 格式：1.时间区间 "hh:mm-hh:mm"
     *       2.时间点 "hh:mm起"
     */
    @NotNull
    private List<String> timeItems;

    //get set
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTimeItems() {
        return timeItems;
    }

    public void setTimeItems(List<String> timeItems) {
        this.timeItems = timeItems;
    }

}
