package com.jd.appoint.domain.enums;

/**
 * Created by luqiang3 on 2018/8/25.
 */
public enum BusinessCodeEnum {

    FUZHUANG("1001","服装"),
    DAZHAXIE("101","大闸蟹");

    BusinessCodeEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

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
}
