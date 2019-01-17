package com.jd.appoint.vo.schedule;

import com.jd.appoint.shopapi.ShopScheduleFacade;
import com.jd.appoint.storeapi.StoreScheduleFacade;
import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/15.
 */
public class ScheduleVO extends CommonRequest {

    /**
     * 商家编号
     */
    @NotNull(groups = {ShopScheduleFacade.class, StoreScheduleFacade.class})
    private Long venderId;

    /**
     * 门店编号
     */
    @NotNull(groups = {StoreScheduleFacade.class})
    private String storeCode;

    /**
     * sku编号
     */
    private Long skuId;

    /**
     * 开始日期
     */
    @NotNull(groups = {ShopScheduleFacade.class, StoreScheduleFacade.class})
    private Date startDate;

    /**
     * 结束日期
     */
    @NotNull(groups = {ShopScheduleFacade.class, StoreScheduleFacade.class})
    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
