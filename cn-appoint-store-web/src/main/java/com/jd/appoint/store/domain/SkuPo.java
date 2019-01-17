package com.jd.appoint.store.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zhangxianglong on 2017/4/14.
 */
public class SkuPo implements Serializable {
    private Long wareId;
    private Long skuId;
    private Long stockNum;
    private Long oldSkuStock;
    private String wareName;
    private Long orderBookingNumSum;//已占用数
    private String skuName;//sku名称
    private Map<String, String> att; //sku上的销售属性
    private String itemNum;//商品货号

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Map<String, String> getAtt() {
        return att;
    }

    public void setAtt(Map<String, String> att) {
        this.att = att;
    }

    public Long getOrderBookingNumSum() {
        return orderBookingNumSum;
    }

    public void setOrderBookingNumSum(Long orderBookingNumSum) {
        this.orderBookingNumSum = orderBookingNumSum;
    }

    public Long getWareId() {
        return wareId;
    }

    public void setWareId(Long wareId) {
        this.wareId = wareId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getOldSkuStock() {
        return oldSkuStock;
    }

    public void setOldSkuStock(Long oldSkuStock) {
        this.oldSkuStock = oldSkuStock;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }
}
