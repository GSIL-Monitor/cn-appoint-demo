package com.jd.appoint.vo.time;

import com.jd.appoint.shopapi.ShopWorkTimeFacade;
import com.jd.appoint.storeapi.StoreWorkTimeFacade;
import com.jd.travel.validate.vextends.Exists;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/5.
 * 店铺服务时间明细
 */
public class WorkTimeItem implements Serializable {

    /**
     * 服务时间项编号
     */
    private Long id;

    /**
     * 星期几：1,2,3,4,5,6,7
     */
    @NotNull(groups = {ShopWorkTimeFacade.class,StoreWorkTimeFacade.class})
    @Exists(values = "1,2,3,4,5,6,7")
    private Integer weekday;

    /**
     * 工作开始时间，格式hh:mm
     */
    private String workStart;

    /**
     * 工作结束时间，格式hh:mm
     */
    private String workEnd;

    /**
     * 休息开始时间，格式hh:mm
     */
    private String restStart;

    /**
     * 休息结束时间，格式hh:mm
     */
    private String restEnd;

    /**
     * 状态，1.有效；2.无效
     */
    @NotNull(groups = {ShopWorkTimeFacade.class,StoreWorkTimeFacade.class})
    @Exists(values = "1,2")
    private Integer status;

    //get set
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}