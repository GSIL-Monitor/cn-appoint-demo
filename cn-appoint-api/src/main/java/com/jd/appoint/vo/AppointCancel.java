package com.jd.appoint.vo;

import com.jd.appoint.api.AppointOrderFacade;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.stfapi.StfAppointOrderFacade;
import com.jd.appoint.storeapi.StoreAppointOrderFacade;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/2.
 * 预约取消信息
 */
public class AppointCancel implements Serializable {
    /**
     * 业务编码，由预约系统分配
     */
    private String businessCode;
    /**
     * 预约订单号
     */
    @NotNull(groups = {AppointOrderFacade.class, ShopAppointOrderFacade.class, StfAppointOrderFacade.class, StoreAppointOrderFacade.class})
    private Long appointOrderId;
    /**
     * 商家venderId
     */
    @NotNull(groups = {ShopAppointOrderFacade.class, StoreAppointOrderFacade.class})
    @Null(groups = {AppointOrderFacade.class, StfAppointOrderFacade.class})
    private Long venderId;
    /**
     * 门店端接入必传
     */
    @NotNull(groups = {StoreAppointOrderFacade.class})
    private String storeCode;
    /**
     * 用户取消添加用户的pin
     * 用户端的和商家，
     */
    @NotNull(groups = {AppointOrderFacade.class, StfAppointOrderFacade.class, ShopAppointOrderFacade.class, StoreAppointOrderFacade.class})
    private String userPin;

    /**
     * 取消原因
     */
    private String cancelReason;


    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }


    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
