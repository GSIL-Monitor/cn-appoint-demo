package com.jd.appoint.vo.order;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lishuaiwei on 2018/6/20.
 * 大闸蟹预约单详情查询
 */
public class DZXAppointOrderQuery implements Serializable {

    /**
     * 用户PIN
     */
    @NotNull
    private String userPin;
    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }


    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }
}
