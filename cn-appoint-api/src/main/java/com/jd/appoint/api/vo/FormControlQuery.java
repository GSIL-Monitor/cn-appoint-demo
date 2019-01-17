package com.jd.appoint.api.vo;

import javax.validation.constraints.NotNull;

/**
 *
 * 表单查询对象，没有相关属性传null
 * Created by yangyuan on 6/6/18.
 *
 */
public class FormControlQuery {

    /**
     * 业务编号
     */
    @NotNull
    private String businessCode;

    /**
     * 商家编号
     */
    private Long venderId;

    /**
     * 页码
     */
    private String pageNo;

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

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public String toString() {
        return "FormControlQuery{" +
                "businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", pageNo=" + pageNo +
                '}';
    }
}
