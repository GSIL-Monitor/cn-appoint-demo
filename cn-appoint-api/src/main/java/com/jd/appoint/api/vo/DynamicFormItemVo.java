package com.jd.appoint.api.vo;

import com.jd.appoint.vo.ControlItemType;
import com.jd.appoint.vo.Pair;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/22.
 */
public class DynamicFormItemVo {
    /**
     * 前端展示文案
     */
    private String label;
    /**
     * 别名
     */
    private String alias;
    /**
     * placeHolder
     */
    private String placeHolder;
    /**
     * 控件类型
     */
    private int type;
    /**
     * config
     */
    private List<Pair<String, String>> data;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Pair<String, String>> getData() {
        return data;
    }

    public void setData(List<Pair<String, String>> data) {
        this.data = data;
    }
}
