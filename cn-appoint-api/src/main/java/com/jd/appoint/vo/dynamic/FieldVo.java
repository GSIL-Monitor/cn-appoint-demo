package com.jd.appoint.vo.dynamic;

/**
 * Created by shaohongsen on 2018/6/26.
 */
public class FieldVo {
    /**
     * 别名
     */
    private String alias;
    /**
     * 展示文案
     */
    private String label;
    /**
     * 内容
     */
    private String value;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
