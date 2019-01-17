package com.jd.appoint.domain.shop;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;

/**
 * Created by luqiang3 on 2018/5/5.
 * 店铺服务时间明细
 */
public class ShopWorkTimeItemPO extends BaseEntity {

    /**
     * <pre>
     * 关联店铺工作时间ID
     * 表字段 : shop_work_time_detail.shop_work_time_id
     * </pre>
     */
    private Long shopWorkTimeId;

    /**
     * <pre>
     * 星期几：1,2,3,4,5,6,7
     * 表字段 : shop_work_time_detail.weekday
     * </pre>
     */
    private Integer weekday;

    /**
     * <pre>
     * 工作开始时间，格式hh:mm
     * 表字段 : shop_work_time_detail.work_start
     * </pre>
     */
    private String workStart;

    /**
     * <pre>
     * 工作结束时间，格式hh:mm
     * 表字段 : shop_work_time_detail.work_end
     * </pre>
     */
    private String workEnd;

    /**
     * <pre>
     * 休息开始时间，格式hh:mm
     * 表字段 : shop_work_time_detail.rest_start
     * </pre>
     */
    private String restStart;

    /**
     * <pre>
     * 休息结束时间，格式hh:mm
     * 表字段 : shop_work_time_detail.rest_end
     * </pre>
     */
    private String restEnd;

    /**
     * <pre>
     * 状态：有效【1】，无效【2】，删除【9】
     * 表字段 : shop_work_time_detail.status
     * </pre>
     */
    private StatusEnum status;

    //get set
    public Long getShopWorkTimeId() {
        return shopWorkTimeId;
    }

    public void setShopWorkTimeId(Long shopWorkTimeId) {
        this.shopWorkTimeId = shopWorkTimeId;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getWorkStart() {
        return workStart;
    }

    public void setWorkStart(String workStart) {
        this.workStart = workStart;
    }

    public String getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(String workEnd) {
        this.workEnd = workEnd;
    }

    public String getRestStart() {
        return restStart;
    }

    public void setRestStart(String restStart) {
        this.restStart = restStart;
    }

    public String getRestEnd() {
        return restEnd;
    }

    public void setRestEnd(String restEnd) {
        this.restEnd = restEnd;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}