package com.jd.appoint.inner.man.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/28 18:25
 */
public class VenderConfigFormDTO {
    private Long id;
    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;
    /**
     * 业务类型code
     * </pre>
     */
    private String businessCode;
    /**
     * 商家id
     */
    private Long venderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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



    public Map<String, String> getSearchMaps() {
        Map<String, String> maps = new HashMap<>();
        maps.put("cfgKey", this.key);
        maps.put("value", null != this.value ? String.valueOf(this.value) : null);
        maps.put("venderId", null != this.venderId ? String.valueOf(this.venderId) : null);
        maps.put("businessCode", this.businessCode);
        return maps;
    }
}
