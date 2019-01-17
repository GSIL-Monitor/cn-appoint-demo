package com.jd.appoint.stfapi.vo;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StfAppointOrderQueryVO extends CommonRequest implements Serializable {

    /**
     * 服务人员PIN
     */
    @NotNull
    private String staffUserPin;
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

    public String getStaffUserPin() {
        return staffUserPin;
    }

    public void setStaffUserPin(String staffUserPin) {
        this.staffUserPin = staffUserPin;
    }
}
