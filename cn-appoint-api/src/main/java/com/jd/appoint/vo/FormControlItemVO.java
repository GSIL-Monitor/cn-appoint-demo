package com.jd.appoint.vo;


import java.io.Serializable;

/**
 * Created by yangyuan on 5/11/18.
 */
public class FormControlItemVO implements Serializable{


    private static final long serialVersionUID = -4230613936329018899L;

    /**
     * 表单显示名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 是否上门显示
     */
    private boolean onSiteDisplay;

    /**
     * 是否上店显示
     */
    private boolean toShopDisplay;

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

    public boolean isOnSiteDisplay() {
        return onSiteDisplay;
    }

    public void setOnSiteDisplay(boolean onSiteDisplay) {
        this.onSiteDisplay = onSiteDisplay;
    }

    public boolean isToShopDisplay() {
        return toShopDisplay;
    }

    public void setToShopDisplay(boolean toShopDisplay) {
        this.toShopDisplay = toShopDisplay;
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

    @Override
    public String toString() {
        return "FormControlItemVO{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", onSiteDisplay=" + onSiteDisplay +
                ", toShopDisplay=" + toShopDisplay +
                ", needInput=" + needInput +
                ", errorMsg='" + errorMsg + '\'' +
                ", regular='" + regular + '\'' +
                ", tips='" + tips + '\'' +
                ", controlItemType=" + controlItemType +
                ", controlItemData=" + controlItemData +
                '}';
    }
}

