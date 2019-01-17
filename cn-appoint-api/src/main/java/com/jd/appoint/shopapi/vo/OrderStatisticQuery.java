package com.jd.appoint.shopapi.vo;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by yangyuan on 5/24/18.
 */
public class OrderStatisticQuery {

    /**
     * 商家id
     */
    @NotNull
    private Long venderId;

    /**
     *查询日期
     */
    private LocalDate  date;

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderStatisticQuery{" +
                "venderId=" + venderId +
                ", date=" + date +
                '}';
    }
}
