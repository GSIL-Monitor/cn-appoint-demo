package com.jd.appoint.domain.sys;

import com.jd.appoint.domain.base.BaseEntity;

public class VenderConfigPO extends BaseEntity {

    /**
     * <pre>
     * 键
     * 表字段 : vender_config.cfg_key
     * </pre>
     */
    private String cfgKey;

    /**
     * <pre>
     * 值
     * 表字段 : vender_config.value
     * </pre>
     */
    private String value;

    /**
     * <pre>
     * 业务类型code
     * 表字段 : vender_config.business_code
     * </pre>
     */
    private String businessCode;

    /**
     * <pre>
     * 商家id
     * 表字段 : vender_config.vender_id
     * </pre>
     */
    private Long venderId;


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

    public String getCfgKey() {
        return cfgKey;
    }

    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey;
    }
}