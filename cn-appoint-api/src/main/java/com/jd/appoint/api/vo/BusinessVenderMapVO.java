package com.jd.appoint.api.vo;

/**
 * Created by yangyuan on 7/4/18.
 */
public class BusinessVenderMapVO {

    private String businessCode;

    private Long venderId;

    private String venderName;

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

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    @Override
    public String toString() {
        return "BusinessVenderMapVO{" +
                "businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", venderName='" + venderName + '\'' +
                '}';
    }
}
