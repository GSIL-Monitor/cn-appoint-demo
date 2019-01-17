package com.jd.appoint.api.vo;

import com.jd.appoint.vo.ControlItemData;
import com.jd.appoint.vo.ControlItemType;

/**
 * Created by shaohongsen on 2018/6/12.
 */
public class FormItemVO {
    /**
     * 表单显示名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;
    /**
     * 是否必填
     */
    private boolean needInput;
    /**
     * 错误提示
     */
    private String errorMsg;

    /**
     * 过滤正则
     */
    private String regular;

    /**
     * 提示
     */
    private String tips;

    /**
     * 控件类型
     */
    private ControlItemType controlItemType;

    /**
     * 控件数据
     */
    private ControlItemData controlItemData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isNeedInput() {
        return needInput;
    }

    public void setNeedInput(boolean needInput) {
        this.needInput = needInput;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public ControlItemType getControlItemType() {
        return controlItemType;
    }

    public void setControlItemType(ControlItemType controlItemType) {
        this.controlItemType = controlItemType;
    }

    public ControlItemData getControlItemData() {
        return controlItemData;
    }

    public void setControlItemData(ControlItemData controlItemData) {
        this.controlItemData = controlItemData;
    }
}
