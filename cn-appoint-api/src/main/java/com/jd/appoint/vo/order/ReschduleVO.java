package com.jd.appoint.vo.order;

import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.storeapi.StoreAppointOrderFacade;
import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 改期
 * Created by gaoxiaoqing on 2018/6/14.
 */
public class ReschduleVO extends CommonRequest {
    /**
     * 预约单ID
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private Long appointOrderId;
    /**
     * 预约开始时间
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private Date appointStartTime;
    /**
     * 预约结束时间
     */
    private Date appointEndTime;

    /**
     * 商家id
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private Long venderId;

    /**
     * 登录用户
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private String loginUserPin;

    /**
     * 门店Code
     */
    @NotNull(groups = {StoreAppointOrderFacade.class})
    private String storeCode;

    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private String businessCode;

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

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
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

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Override
    public String getBusinessCode() {
        return businessCode;
    }

    @Override
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
