package com.jd.appoint.shop.vo;

import java.util.List;

/**
 * Created by bjliuyong on 2018/5/24.
 */
public class UpdateStock {

    private Long skuId = -1L;

    private List<SingleStockVO> singleStockVOList;

    public List<SingleStockVO> getSingleStockVOList() {
        return singleStockVOList;
    }

    public void setSingleStockVOList(List<SingleStockVO> singleStockVOList) {
        this.singleStockVOList = singleStockVOList;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
