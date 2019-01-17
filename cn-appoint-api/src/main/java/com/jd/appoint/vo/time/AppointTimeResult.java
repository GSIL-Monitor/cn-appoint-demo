package com.jd.appoint.vo.time;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/2.
 * 预约时间查询结果
 */
public class AppointTimeResult implements Serializable {

    /**
     * 预约日期
     */
    private List<AppointDate> appointDates;

    //get set
    public List<AppointDate> getAppointDates() {
        return appointDates;
    }

    public void setAppointDates(List<AppointDate> appointDates) {
        this.appointDates = appointDates;
    }
}
