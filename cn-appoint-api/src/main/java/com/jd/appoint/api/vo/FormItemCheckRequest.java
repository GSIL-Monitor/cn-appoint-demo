package com.jd.appoint.api.vo;


import java.util.Map;

/**
 * Created by yangyuan on 6/13/18.
 */
public class FormItemCheckRequest {

    private String contextId;

    private String pageCode;

    private Map<String, String> itemData;

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public Map<String, String> getItemData() {
        return itemData;
    }

    public void setItemData(Map<String, String> itemData) {
        this.itemData = itemData;
    }

    @Override
    public String toString() {
        return "FormItemCheckRequest{" +
                "contextId='" + contextId + '\'' +
                ", pageCode='" + pageCode + '\'' +
                ", itemData=" + itemData +
                '}';
    }
}
