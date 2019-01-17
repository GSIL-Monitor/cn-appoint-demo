package com.jd.appoint.domain.stock;

import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;

import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/13.
 * 库存信息
 */
public class StockInfoPO extends BaseEntity {

    /**
     * <pre>
     * 业务线编码
     * 表字段 : stock_info.business_code
     * </pre>
     */
    private String businessCode;

    /**
     * <pre>
     * 商家编号
     * 表字段 : stock_info.vender_id
     * </pre>
     */
    private Long venderId;

    /**
     * <pre>
     * 门店编号，无门店时填-1
     * 表字段 : stock_info.store_id
     * </pre>
     */
    private String storeCode;

    /**
     * <pre>
     * SKU编号，无SKU时填-1
     * 表字段 : stock_info.sku_id
     * </pre>
     */
    private Long skuId;

    /**
     * <pre>
     * 库存日期，格式yyyy-MM-dd
     * 表字段 : stock_info.date
     * </pre>
     */
    private Date date;

    /**
     * <pre>
     * 总库存
     * 表字段 : stock_info.total_qty
     * </pre>
     */
    private int totalQty;

    /**
     * <pre>
     * 扣减库存
     * 表字段 : stock_info.sale_qty
     * </pre>
     */
    private int saleQty;

    /**
     * <pre>
     * 状态：有效【1】，无效【2】，删除【9】
     * 表字段 : stock_info.status
     * </pre>
     */
    private StatusEnum status;

    /**
     * <pre>
     * 获取：业务线编码
     * 表字段：stock_info.business_code
     * </pre>
     *
     * @return stock_info.business_code：业务线编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * <pre>
     * 获取：商家编号
     * 表字段：stock_info.vender_id
     * </pre>
     *
     * @return stock_info.vender_id：商家编号
     */
    public Long getVenderId() {
        return venderId;
    }

    /**
     * <pre>
     * 获取：SKU编号，无SKU时填-1
     * 表字段：stock_info.sku_id
     * </pre>
     *
     * @return stock_info.sku_id：SKU编号，无SKU时填-1
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * <pre>
     * 获取：库存日期，格式yyyy-MM-dd
     * 表字段：stock_info.date
     * </pre>
     *
     * @return stock_info.date：库存日期，格式yyyy-MM-dd
     */
    public Date getDate() {
        return date;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public int getSaleQty() {
        return saleQty;
    }

    @Override
    public String toString() {
        return "StockInfoPO{" +
                "businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", storeCode='" + storeCode + '\'' +
                ", skuId=" + skuId +
                ", date=" + (null != date ? AppointDateUtils.getDate2Str("yyyy-MM-dd", date) : null) +
                ", totalQty=" + totalQty +
                ", saleQty=" + saleQty +
                ", status=" + status +
                '}';
    }

    /**
     * 剩余库存
     * @return
     */
    public Integer getRemindQty(){
        return totalQty - saleQty;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public void setSaleQty(int saleQty) {
        this.saleQty = saleQty;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}