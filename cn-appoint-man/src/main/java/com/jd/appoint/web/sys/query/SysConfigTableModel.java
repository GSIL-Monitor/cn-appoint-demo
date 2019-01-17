package com.jd.appoint.web.sys.query;

import com.jd.adminlte4j.annotation.UIFormItem;

/**
 * @author lishuaiwei
 * @date 2018/5/17 10:15
 */
public class SysConfigTableModel {
    @UIFormItem(span = 3, label = "key")
    private String key;
    @UIFormItem(span = 3, label = "value")
    private String value;
    @UIFormItem(span = 3, label = "venderId")
    private Long venderId;
    @UIFormItem(span = 3, label = "businessCode")
    private String businessCode;
    public SysConfigTableModel() {
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

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
