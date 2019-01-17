package com.jd.appoint.shop.vo;

import com.jd.appoint.vo.time.WorkTime;

import java.util.List;

/**
 * Created by bjliuyong on 2018/5/24.
 */
public class StockAndScheduleSave {
    //更新服务时间用
    private WorkTime workTime;
    //用户修改的多个间隔库存
    private List<SingleStockVO> singleStockVOList;


    public List<SingleStockVO> getSingleStockVOList() {
        return singleStockVOList;
    }

    public void setSingleStockVOList(List<SingleStockVO> singleStockVOList) {
        this.singleStockVOList = singleStockVOList;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }
}
