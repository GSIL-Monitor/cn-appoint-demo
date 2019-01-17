package com.jd.appoint.domain.stock;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.StockOptStatusEnum;

import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/14.
 * 库存操作PO
 */
public class StockOperatePO extends BaseEntity {

    /**
     * <pre>
     * 预约订单号
     * 表字段 : stock_operate.appoint_order_id
     * </pre>
     */
    private Long appointOrderId;

    /**
     * <pre>
     * 库存状态：已扣减【1】，已回冲【2】
     * 表字段 : stock_operate.stock_status
     * </pre>
     */
    private StockOptStatusEnum stockStatus;

    /**
     * <pre>
     * 状态：有效【1】，无效【2】，删除【9】
     * 表字段 : stock_operate.status
     * </pre>
     */
    private StatusEnum status;

    /**
     * 预约日期
     */
    private Date appointDate;

    @Override
    public String toString() {
        return "StockOperatePO{" +
                "appointOrderId=" + appointOrderId +
                ", stockStatus=" + stockStatus +
                ", status=" + status +
                ", appointDate=" + appointDate +
                '}';
    }

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public StockOptStatusEnum getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockOptStatusEnum stockStatus) {
        this.stockStatus = stockStatus;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(Date appointDate) {
        this.appointDate = appointDate;
    }
}