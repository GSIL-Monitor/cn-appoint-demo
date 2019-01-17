package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/3.
 * 订单信息查询
 */
public class AppointOrderQueryVO implements Serializable {

    /**
     * 用户PIN
     */
    @NotNull
    private String customerUserPin;
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

    public String getCustomerUserPin() {
        return customerUserPin;
    }

    public void setCustomerUserPin(String customerUserPin) {
        this.customerUserPin = customerUserPin;
    }

}
