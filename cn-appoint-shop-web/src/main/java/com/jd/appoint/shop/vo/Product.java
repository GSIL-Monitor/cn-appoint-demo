package com.jd.appoint.shop.vo;

import com.jd.appoint.shopapi.vo.ProductVO;
import com.jd.appoint.shopapi.vo.StatusEnum;

import java.util.List;

/**
 * Created by bjliuyong on 2018/5/24.
 */
public class Product {

    private Long id;

    private String name;

    private Long skuId;

    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
