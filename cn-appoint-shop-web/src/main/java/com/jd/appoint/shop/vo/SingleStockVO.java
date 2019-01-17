package com.jd.appoint.shop.vo;


import java.util.Date;

/**
 * Created by bjliuyong on 2018/6/4.
 */
public class SingleStockVO {

    private Date date;

    private int totalQty;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }
}
