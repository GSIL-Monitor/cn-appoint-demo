package com.jd.appoint.vo.stock;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by luqiang3 on 2018/6/15.
 */
public class StockInfoQueryVO extends CommonRequest {

    /**
     * 商家编号
     */
    @NotNull
    private Long venderId;

    /**
     * 门店编号
     */
    private Long storeId;

    //get set
    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
