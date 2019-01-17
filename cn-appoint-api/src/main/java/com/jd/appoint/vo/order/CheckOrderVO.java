package com.jd.appoint.vo.order;

import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.storeapi.StoreAppointOrderFacade;
import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 预约单审核
 * Created by gaoxiaoqing on 2018/6/18.
 */
public class CheckOrderVO{

    /**
     * 商家ID
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private Long venderId;
    /**
     * 预约单ID
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private List<Long> appointOrderIds;
    /**
     * 登录用Pin
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


    public List<Long> getAppointOrderIds() {
        return appointOrderIds;
    }

    public void setAppointOrderIds(List<Long> appointOrderIds) {
        this.appointOrderIds = appointOrderIds;
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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
