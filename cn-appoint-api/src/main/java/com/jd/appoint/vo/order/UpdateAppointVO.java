package com.jd.appoint.vo.order;

import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.storeapi.StoreAppointOrderFacade;

import javax.validation.constraints.NotNull;

/**
 * Created by gaoxiaoqing on 2018/6/18.
 */
public class UpdateAppointVO{
    /**
     * 预约单号
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private Long appointOrderId;

    /**
     * 商家id
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private Long venderId;

    /**
     * 商家备注
     */
    private String venderMemo;

    /**
     * 门店Code
     */
    @NotNull(groups = {StoreAppointOrderFacade.class})
    private String storeCode;

    /**
     * 业务Code
     */
    @NotNull(groups = {ShopAppointOrderFacade.class,StoreAppointOrderFacade.class})
    private String businessCode;


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

    public String getVenderMemo() {
        return venderMemo;
    }

    public void setVenderMemo(String venderMemo) {
        this.venderMemo = venderMemo;
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
