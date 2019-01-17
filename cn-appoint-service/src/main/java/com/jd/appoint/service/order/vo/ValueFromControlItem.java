package com.jd.appoint.service.order.vo;

import com.jd.appoint.domain.enums.EncryptTypeEnum;

/**
 * Created by shaohongsen on 2018/5/22.
 * 含值 表单控制项
 */
public class ValueFromControlItem {
    /**
     * 表单控制id
     */
    private Long formControlId;

    /**
     * 属性名别名：比如 alias
     */
    private String attrNameAlias;

    /**
     * 属性值
     */
    private String attrValue;
    /**
     * 正则
     */
    private String regular;
    /**
     * 加密类型
     */
    private EncryptTypeEnum encryptType;

    private boolean needInput;

    private boolean orderFiled;

    public Long getFormControlId() {
        return formControlId;
    }

    public void setFormControlId(Long formControlId) {
        this.formControlId = formControlId;
    }

    public String getAttrNameAlias() {
        return attrNameAlias;
    }

    public void setAttrNameAlias(String attrNameAlias) {
        this.attrNameAlias = attrNameAlias;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public EncryptTypeEnum getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(EncryptTypeEnum encryptType) {
        this.encryptType = encryptType;
    }

    public boolean isNeedInput() {
        return needInput;
    }

    public void setNeedInput(boolean needInput) {
        this.needInput = needInput;
    }

    public boolean isOrderFiled() {
        return orderFiled;
    }

    public void setOrderFiled(boolean orderFiled) {
        this.orderFiled = orderFiled;
    }
}
