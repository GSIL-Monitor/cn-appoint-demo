package com.jd.appoint.inner.man.dto;

import com.jd.adminlte4j.annotation.DictProvider;
import com.jd.adminlte4j.annotation.UIFormItem;
import com.jd.adminlte4j.model.form.FormItemType;
import com.jd.appoint.inner.man.enums.EnumUtils;

/**
 * Created by yangyuan on 5/17/18.
 */
public class FormControlItemDTO {

    private Long id;

    @UIFormItem(label = "填写信息项",placeholder = "添加信息项")
    private String name;

    @UIFormItem(label = "别名")
    private String alias;

    @UIFormItem(hidden = true)
    private String businessCode;

    @UIFormItem(label = "到家是否展示",type = FormItemType.SWITCH)
    private Boolean onSiteDisplay;

    @UIFormItem(label = "到店是否展示",type = FormItemType.SWITCH)
    private Boolean toShopDisplay;

    @UIFormItem(label = "是否为必填",type = FormItemType.SWITCH)
    private Boolean needInput;

    @UIFormItem(label = "输入提示")
    private String tips;

    @UIFormItem(label = "错误提示")
    private String errorMsg;

    @UIFormItem(label = "正则",span = 1)
    private String regular;

    @UIFormItem(type = FormItemType.SELECT, label = "加密类型")
    @DictProvider(type = EnumUtils.class, method = "getEncrytType")
    private int encryptType;

    private int status;

    @UIFormItem(type = FormItemType.SELECT, label = "控件类型")
    @DictProvider(type = EnumUtils.class, method = "getItemType")
    private int itemType;

    @UIFormItem(label = "控件数据")
    private String itemData;

    @UIFormItem(label = "商家ID")
    private Long venderId;

    @UIFormItem(label = "页码")
    private String pageNo;

    @UIFormItem(label = "排序")
    private Integer priority;

    @UIFormItem(label = "是否为订单属性",type = FormItemType.SWITCH)
    private Boolean orderField;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Boolean getOnSiteDisplay() {
        return onSiteDisplay;
    }

    public void setOnSiteDisplay(Boolean onSiteDisplay) {
        this.onSiteDisplay = onSiteDisplay;
    }

    public Boolean getToShopDisplay() {
        return toShopDisplay;
    }

    public void setToShopDisplay(Boolean toShopDisplay) {
        this.toShopDisplay = toShopDisplay;
    }

    public Boolean getNeedInput() {
        return needInput;
    }

    public void setNeedInput(Boolean needInput) {
        this.needInput = needInput;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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

    public int getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(int encryptType) {
        this.encryptType = encryptType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Boolean getOrderField() {
        return orderField;
    }

    public void setOrderField(Boolean orderField) {
        this.orderField = orderField;
    }

    public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public static class Builder{

        private String name;

        private String alias;

        private int priority;

        private boolean forSelect;

        private boolean needInput;

        private boolean onSiteDisplay;

        public Builder(String name, String alias){
            this.name = name;
            this.alias = alias;
        }


        public FormControlItemDTO build(){
            FormControlItemDTO formControlItemDTO = new FormControlItemDTO();
            formControlItemDTO.setName(name);
            formControlItemDTO.setAlias(alias);
            formControlItemDTO.setPriority(priority);
            formControlItemDTO.setNeedInput(needInput);
            formControlItemDTO.setOnSiteDisplay(onSiteDisplay);
            return formControlItemDTO;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder alias(String alias){
            this.alias = alias;
            return this;
        }

        public Builder priority(int priority){
            this.priority = priority;
            return this;
        }

        public Builder forSelect(boolean forSelect){
            this.forSelect = forSelect;
            return this;
        }

        public Builder needInput(boolean needInput){
            this.needInput = needInput;
            return this;
        }

        public Builder onSiteDisplay(boolean onSiteDisplay){
            this.onSiteDisplay = onSiteDisplay;
            return this;
        }
    }


    @Override
    public String toString() {
        return "FormControlItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", onSiteDisplay=" + onSiteDisplay +
                ", toShopDisplay=" + toShopDisplay +
                ", needInput=" + needInput +
                ", tips='" + tips + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", regular='" + regular + '\'' +
                ", encryptType=" + encryptType +
                ", status=" + status +
                ", itemType=" + itemType +
                ", itemData='" + itemData + '\'' +
                ", venderId=" + venderId +
                ", pageNo=" + pageNo +
                ", priority=" + priority +
                ", orderField=" + orderField +
                '}';
    }
}
