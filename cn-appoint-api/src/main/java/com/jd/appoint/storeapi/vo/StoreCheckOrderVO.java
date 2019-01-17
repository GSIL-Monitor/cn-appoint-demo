package com.jd.appoint.storeapi.vo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 门店端审核
 * Created by gaoxiaoqing on 2018/7/4.
 */
public class StoreCheckOrderVO {

    /**
     * 门店Code
     */
    @NotNull
    private Long storeCode;
    /**
     * 预约单ID
     */
    @NotNull
    private List<Long> appointOrderIds;
    /**
     * 登录用Pin
     */
    @NotNull
    private String loginUserPin;


    public Long getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(Long storeCode) {
        this.storeCode = storeCode;
    }

    public List<Long> getAppointOrderIds() {
        return appointOrderIds;
    }

    public void setAppointOrderIds(List<Long> appointOrderIds) {
        this.appointOrderIds = appointOrderIds;
    }

    public String getLoginUserPin() {
        return loginUserPin;
    }

    public void setLoginUserPin(String loginUserPin) {
        this.loginUserPin = loginUserPin;
    }
}
