package com.jd.appoint.store.vo;

import com.jd.appoint.vo.time.WorkTime;

import java.util.List;

/**
 * Created by bjliuyong on 2018/5/24.
 */
public class ProductSave {

    private Product product;

    private List<SingleStockVO> singleStockVOList;
    //更新服务时间用
    private WorkTime workTime;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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
