package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 预约派单
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/9 14:58
 */
public class DispatchOrderVO extends CommonRequest implements Serializable {

    /**
     * 预约单好
     */
    @NotNull
    private Long appointOrderId;
    /**
     * 服务人员Code
     */
    @NotNull
    private String staffCode;

    @NotNull
    private Long venderId;
    /**
     * 登录用户Pin
     */
    @NotNull
    private String loginUserPin;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getLoginUserPin() {
        return loginUserPin;
    }

    public void setLoginUserPin(String loginUserPin) {
        this.loginUserPin = loginUserPin;
    }
}
