package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 预约单更新对应的VO
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/9 14:58
 */
public class AppointUpdateVO extends CommonRequest implements Serializable {
    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;
    /**
     * 预约开始时间
     */
    private Date appointStartTime;
    /**
     * 预约结束时间
     */
    private Date appointEndTime;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public Date getAppointStartTime() {
        return appointStartTime;
    }

    public void setAppointStartTime(Date appointStartTime) {
        this.appointStartTime = appointStartTime;
    }

    public Date getAppointEndTime() {
        return appointEndTime;
    }

    public void setAppointEndTime(Date appointEndTime) {
        this.appointEndTime = appointEndTime;
    }
}
