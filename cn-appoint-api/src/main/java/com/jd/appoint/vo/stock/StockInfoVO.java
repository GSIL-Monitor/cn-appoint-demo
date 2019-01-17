package com.jd.appoint.vo.stock;

import com.jd.appoint.shopapi.ShopStockFacade;
import com.jd.appoint.storeapi.StoreStockFacade;
import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/14.
 */
public class StockInfoVO extends CommonRequest{

    /**
     * 商家编号
     */
    @NotNull(groups = {ShopStockFacade.class, StoreStockFacade.class})
    private Long venderId;

    /**
     * 门店编号
     */
    @NotNull(groups = {StoreStockFacade.class})
    private String storeCode;

    /**
     * SKU编号
     */
    private Long skuId = -1L;

    /**
     * 库存开始日期，格式yyyy-MM-dd
     */
    @NotNull(groups = {ShopStockFacade.class, StoreStockFacade.class})
    private Date startDate;

    /**
     * 库存结束日期，格式yyyy-MM-dd
     */
    @NotNull(groups = {ShopStockFacade.class, StoreStockFacade.class})
    private Date endDate;

    /**
     * 总库存
     */
    private int totalQty;

    //get set
    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
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

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
