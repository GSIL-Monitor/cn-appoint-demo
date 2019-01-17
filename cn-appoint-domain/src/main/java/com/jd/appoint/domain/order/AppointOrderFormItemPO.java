package com.jd.appoint.domain.order;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;

/**
 * @author author
 */
public class AppointOrderFormItemPO extends BaseEntity {

    private static final long serialVersionUID = 1525917088861L;


    /**
     * 预约单Id
     */
    private Long appointOrderId;

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
     * 状态，预留字段
     */
    private StatusEnum status;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

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


    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
