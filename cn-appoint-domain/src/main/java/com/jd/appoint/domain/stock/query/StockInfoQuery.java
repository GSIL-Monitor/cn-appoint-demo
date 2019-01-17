package com.jd.appoint.domain.stock.query;

import com.jd.appoint.domain.stock.StockInfoPO;

import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/14.
 */
public class StockInfoQuery extends StockInfoPO {

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    //get set
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
