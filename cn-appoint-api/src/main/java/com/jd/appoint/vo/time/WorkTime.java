package com.jd.appoint.vo.time;

import com.jd.appoint.shopapi.ShopWorkTimeFacade;
import com.jd.appoint.storeapi.StoreWorkTimeFacade;
import com.jd.appoint.vo.CommonRequest;
import com.jd.travel.validate.vextends.Exists;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/5.
 * 店铺服务时间
 */
public class WorkTime extends CommonRequest implements Serializable{

    /**
     * 服务时间编号
     */
    private Long id;

    /**
     * 商家编号
     */
    @NotNull(groups = {ShopWorkTimeFacade.class,StoreWorkTimeFacade.class})
    private Long venderId;

    /**
     * 服务时间范围，开始天数
     */
    @NotNull(groups = {ShopWorkTimeFacade.class,StoreWorkTimeFacade.class})
    @Min(0)
    private Integer startDay;

    /**
     * 服务时间范围，结束天数
     */
    @NotNull(groups = {ShopWorkTimeFacade.class,StoreWorkTimeFacade.class})
    @Min(0)
    private Integer endDay;

    /**
     * 当天提前预约时间
     */
    private Integer t0Advance;

    /**
     * 当天提前预约时间单位，1.分钟；2.小时；
     * 默认1
     */
    @Exists(values = "1,2")
    private Integer t0AdvanceUnit;

    /**
     * 服务时间间隔
     */
    private Integer timeInterval;

    /**
     * 服务时间间隔单位，1.分钟；2.小时；
     * 默认1
     */
    @Exists(values = "1,2")
    private Integer timeIntervalUnit;

    /**
     * 时间展示模式，1.时间点；2.时间区间；3.天
     */
    @NotNull(groups = {ShopWorkTimeFacade.class,StoreWorkTimeFacade.class})
    @Exists(values = "1,2,3")
    private Integer timeShowType;

    /**
     * 服务时间明细
     */
    @Size(min = 7, max = 7, message = "周的天数不等于7天")
    @Valid
    private List<WorkTimeItem> workTimeItems;

    /**
     * 门店编号
     */
    @NotNull(groups = {StoreWorkTimeFacade.class})
    private String storeCode;

    /**
     * sku编号
     */
    private Long skuId;

    //get set
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

    public List<WorkTimeItem> getWorkTimeItems() {
        return workTimeItems;
    }

    public void setWorkTimeItems(List<WorkTimeItem> workTimeItems) {
        this.workTimeItems = workTimeItems;
    }

    public Integer getT0AdvanceUnit() {
        return t0AdvanceUnit;
    }

    public void setT0AdvanceUnit(Integer t0AdvanceUnit) {
        this.t0AdvanceUnit = t0AdvanceUnit;
    }

    public Integer getTimeIntervalUnit() {
        return timeIntervalUnit;
    }

    public void setTimeIntervalUnit(Integer timeIntervalUnit) {
        this.timeIntervalUnit = timeIntervalUnit;
    }

    public Integer getTimeShowType() {
        return timeShowType;
    }

    public void setTimeShowType(Integer timeShowType) {
        this.timeShowType = timeShowType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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