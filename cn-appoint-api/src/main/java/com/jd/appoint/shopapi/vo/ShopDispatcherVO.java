package com.jd.appoint.shopapi.vo;

import com.jd.appoint.vo.CommonRequest;

/**
 * 派单
 * Created by gaoxiaoqing on 2018/6/15.
 */
public class ShopDispatcherVO extends CommonRequest{
    /**
     * 员工ID
     */
    private String staffId;
    /**
     * 商家ID
     */
    private String venderId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }
}
