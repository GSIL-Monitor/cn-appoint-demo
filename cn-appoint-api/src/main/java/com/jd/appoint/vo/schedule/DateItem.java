package com.jd.appoint.vo.schedule;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/15.
 */
public class DateItem implements Serializable {

    /**
     * 日期，格式：yyyy-MM-dd
     */
    private Date date;

    /**
     * 总库存
     */
    private Integer totalQty;

    /**
     * 已约库存
     */
    private Integer saleQty;

    @Override
    public String toString() {
        return "DateItem{" +
                "date=" + date +
                ", totalQty=" + totalQty +
                ", saleQty=" + saleQty +
                '}';
    }

    //get set
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(Integer saleQty) {
        this.saleQty = saleQty;
    }
}
