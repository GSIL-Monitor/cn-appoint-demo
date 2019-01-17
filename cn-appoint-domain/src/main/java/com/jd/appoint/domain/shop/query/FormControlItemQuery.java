package com.jd.appoint.domain.shop.query;

/**
 * Created by yangyuan on 6/13/18.
 */
public class FormControlItemQuery {

    private String businessCode;

    private Long venderId;

    private String pageNo;

    private Boolean onSiteDisplay;

    private Boolean toShopDisplay;

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

    public Boolean getToShopDisplay() {
        return toShopDisplay;
    }

    public void setToShopDisplay(Boolean toShopDisplay) {
        this.toShopDisplay = toShopDisplay;
    }

    public Boolean getOnSiteDisplay() {
        return onSiteDisplay;
    }

    public void setOnSiteDisplay(Boolean onSiteDisplay) {
        this.onSiteDisplay = onSiteDisplay;
    }

    @Override
    public String toString() {
        return "FormControlItemQuery{" +
                "businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", pageNo='" + pageNo + '\'' +
                ", onSiteDisplay=" + onSiteDisplay +
                ", toShopDisplay=" + toShopDisplay +
                '}';
    }
}
