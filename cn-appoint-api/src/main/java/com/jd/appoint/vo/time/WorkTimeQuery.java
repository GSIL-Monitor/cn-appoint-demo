package com.jd.appoint.vo.time;


import com.jd.appoint.shopapi.ShopWorkTimeFacade;
import com.jd.appoint.storeapi.StoreWorkTimeFacade;
import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/5.
 * 服务时间查询请求
 */
public class WorkTimeQuery extends CommonRequest implements Serializable {

    /**
     * 商家ID
     */
    @NotNull(groups = {ShopWorkTimeFacade.class,StoreWorkTimeFacade.class})
    private Long venderId;
    /**
     * 门店编码
     */
    @NotNull(groups = {StoreWorkTimeFacade.class})
    private String storeCode;
    /**
     * sku编号
      */
    private Long skuId;

    //get set
    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
