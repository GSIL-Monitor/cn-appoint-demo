package com.jd.appoint.domain.config;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;

/**
 * Created by yangyuan on 7/4/18.
 */
public class BusinessVenderMapPO extends BaseEntity {

    private String businessCode;

    private Long venderId;

    private String venderName;

    private StatusEnum status;

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BusinessVenderMapPO{" +
                "businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", venderName='" + venderName + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
