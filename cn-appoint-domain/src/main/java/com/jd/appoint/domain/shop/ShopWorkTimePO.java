package com.jd.appoint.domain.shop;


import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.enums.TimeUnitEnum;

/**
 * Created by luqiang3 on 2018/5/5.
 * 店铺服务时间
 */
public class ShopWorkTimePO extends BaseEntity {

    /**
     * <pre>
     * 业务编码
     * 表字段 : shop_work_time.business_code
     * </pre>
     */
    private String businessCode;

    /**
     * <pre>
     * 商家ID
     * 表字段 : shop_work_time.vender_id
     * </pre>
     */
    private Long venderId;

    /**
     * 门店编号
     */
    private String storeCode;

    /**
     * sku编号
     */
    private Long skuId;

    /**
     * <pre>
     * 服务时间范围，开始天数
     * 表字段 : shop_work_time.start_day
     * </pre>
     */
    private Integer startDay;

    /**
     * <pre>
     * 服务时间范围，结束天数
     * 表字段 : shop_work_time.end_day
     * </pre>
     */
    private Integer endDay;

    /**
     * <pre>
     * 当天提前预约时间
     * 表字段 : shop_work_time.t0_advance
     * </pre>
     */
    private Integer t0Advance;

    /**
     * <pre>
     * 当天提前预约时间单位
     * 表字段 : shop_work_time.t0_advance_unit
     * </pre>
     */
    private TimeUnitEnum t0AdvanceUnit;

    /**
     * <pre>
     * 服务时间间隔
     * 表字段 : shop_work_time.time_interval
     * </pre>
     */
    private Integer timeInterval;

    /**
     * <pre>
     * 服务时间间隔单位
     * 表字段 : shop_work_time.time_interval_unit
     * </pre>
     */
    private TimeUnitEnum timeIntervalUnit;

    /**
     * <pre>
     * 时间展示模式：时间点【1】，时间区间【2】
     * 表字段 : shop_work_time.time_show_type
     * </pre>
     */
    private TimeShowTypeEnum timeShowType;

    /**
     * <pre>
     * 状态：有效【1】，无效【2】，删除【9】
     * 表字段 : shop_work_time.status
     * </pre>
     */
    private StatusEnum status;

    //get set
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

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getT0Advance() {
        return t0Advance;
    }

    public void setT0Advance(Integer t0Advance) {
        this.t0Advance = t0Advance;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeShowTypeEnum getTimeShowType() {
        return timeShowType;
    }

    public void setTimeShowType(TimeShowTypeEnum timeShowType) {
        this.timeShowType = timeShowType;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public TimeUnitEnum getT0AdvanceUnit() {
        return t0AdvanceUnit;
    }

    public void setT0AdvanceUnit(TimeUnitEnum t0AdvanceUnit) {
        this.t0AdvanceUnit = t0AdvanceUnit;
    }

    public TimeUnitEnum getTimeIntervalUnit() {
        return timeIntervalUnit;
    }

    public void setTimeIntervalUnit(TimeUnitEnum timeIntervalUnit) {
        this.timeIntervalUnit = timeIntervalUnit;
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
}