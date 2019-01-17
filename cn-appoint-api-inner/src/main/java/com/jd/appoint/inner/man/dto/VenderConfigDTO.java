package com.jd.appoint.inner.man.dto;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 18:04
 */
public class VenderConfigDTO {

    private Long id;
    private String cfgKey;

    private String value;

    private String businessCode;

    private Long venderId;

    public String getCfgKey() {
        return cfgKey;
    }

    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
