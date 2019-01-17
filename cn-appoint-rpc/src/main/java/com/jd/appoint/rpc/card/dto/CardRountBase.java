package com.jd.appoint.rpc.card.dto;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/25 16:42
 */
public abstract class CardRountBase {
    /**
     * 业务类型
     */
    protected String businessCode;
    /**
     * 商家Id
     */
    protected Long venderId;

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
}
