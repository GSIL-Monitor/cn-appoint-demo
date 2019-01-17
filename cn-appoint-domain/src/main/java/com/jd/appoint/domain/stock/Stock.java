package com.jd.appoint.domain.stock;

import com.jd.appoint.common.utils.AppointDateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/15.
 */
public class Stock implements Serializable{

    /**
     * 预约订单号
     */
    private Long appointOrderId;

    /**
     * 业务编号
     */
    private String businessCode;

    /**
     * 商家编号
     */
    private Long venderId;

    /**
     * 门店编号
     */
    private String storeCode;

    /**
     * SKU编号
     */
    private Long skuId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 改期前的日期
     */
    private Date preDate;

    @Override
    public String toString() {
        return "Stock{" +
                "appointOrderId=" + appointOrderId +
                ", businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", storeCode=" + storeCode +
                ", skuId=" + skuId +
                ", date=" + (null != date ? AppointDateUtils.getDate2Str("yyyy-MM-dd", date) : date) +
                ", preDate=" + (null != preDate ? AppointDateUtils.getDate2Str("yyyy-MM-dd", preDate) : preDate) +
                '}';
    }

    //get set
    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date != null ? AppointDateUtils.getDate2Date("yyyy-MM-dd", date) : date;
    }

    public Date getPreDate() {
        return preDate;
    }

    public void setPreDate(Date preDate) {
        this.preDate = preDate != null ? AppointDateUtils.getDate2Date("yyyy-MM-dd", preDate) : preDate;
    }
}
