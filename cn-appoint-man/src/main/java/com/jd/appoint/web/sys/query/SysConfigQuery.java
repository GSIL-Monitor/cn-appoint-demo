package com.jd.appoint.web.sys.query;

import com.jd.adminlte4j.annotation.DictData;
import com.jd.adminlte4j.annotation.DictEntry;
import com.jd.adminlte4j.annotation.Form;
import com.jd.adminlte4j.annotation.UIFormItem;
import com.jd.adminlte4j.model.form.FormItemType;
import com.jd.appoint.web.query.PageQuery;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 17:15
 */
@Form(span = 3, ignore = true, hidden = false)
public class SysConfigQuery extends PageQuery {
    @UIFormItem(span = 3, label = "键")
    private String key;
    @UIFormItem(span = 3, label = "值")
    private String value;
    @UIFormItem(span = 3, label = "商家ID")
    private Long venderId;
    @UIFormItem(type = FormItemType.SELECT, span = 3, label = "业务Code")
    @DictData({@DictEntry(code = "618618", value = "服装定制"), @DictEntry(code = "123123", value = "体检预约")})
    private String businessCode;

    public SysConfigQuery() {
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


    public Map<String, String> getSearchMaps() {
        Map<String, String> maps = new HashMap<>();
        maps.put("cfgKey", this.key);
        maps.put("value", null != this.value ? String.valueOf(this.value) : null);
        maps.put("venderId", null != this.venderId ? String.valueOf(this.venderId) : null);
        maps.put("businessCode", this.businessCode);
        return maps;
    }
}
