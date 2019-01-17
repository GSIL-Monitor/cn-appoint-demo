package com.jd.appoint.web.sys.query;

import com.jd.adminlte4j.annotation.DictData;
import com.jd.adminlte4j.annotation.DictEntry;
import com.jd.adminlte4j.annotation.Form;
import com.jd.adminlte4j.annotation.UIFormItem;

/**
 * @author lishuaiwei
 * @date 2018/5/17 10:15
 */
@Form(span = 12, ignore = true, hidden = false)
public class SysConfigModel {
    /**
     * 数据的ID
     */
    @UIFormItem(hidden = true)
    private Long id;
    @UIFormItem(span = 10, label = "键")
    private String key;
    @UIFormItem(span = 10, label = "值")
    private String value;
    @UIFormItem(span = 10, label = "商家ID")
    private Long venderId;
    @UIFormItem(span = 10, label = "具体业务")
    @DictData({@DictEntry(code = "618618",value = "服装定制"), @DictEntry(code = "123123", value = "体检预约")})
    private String businessCode;


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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
