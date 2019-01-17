package com.jd.appoint.domain.shop;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.EncryptTypeEnum;
import com.jd.appoint.domain.enums.ItemTypeEnum;
import com.jd.appoint.domain.enums.StatusEnum;

/**
 * Created by yangyuan on 5/8/18.
 */
public class ShopFormControlItemPO extends BaseEntity{

    private String name;

    private String alias;

    private String businessCode;

    private Boolean onSiteDisplay;

    private Boolean toShopDisplay;

    private Boolean needInput;

    private String tips;

    private String errorMsg;

    private String regular;

    private EncryptTypeEnum encryptType;

    private Integer priority;

    private StatusEnum status;

    private ItemTypeEnum itemType;

    private String itemData;

    private Long venderId;

    private String pageNo;

    /**
     * 是否为订单静态数据
     */
    private Boolean orderField;



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
        if(onSiteDisplay == null){
            return false;
        }
        return onSiteDisplay;
    }

    public void setOnSiteDisplay(Boolean onSiteDisplay) {
        this.onSiteDisplay = onSiteDisplay;
    }

    public Boolean getToShopDisplay() {
        if(toShopDisplay == null){
            return false;
        }
        return toShopDisplay;
    }

    public void setToShopDisplay(Boolean toShopDisplay) {
        this.toShopDisplay = toShopDisplay;
    }

    public Boolean getNeedInput() {
        if(needInput == null){
            return false;
        }
        return needInput;
    }

    public void setNeedInput(Boolean needInput) {
        this.needInput = needInput;
    }


    public Integer getPriority() {
        if(priority == null){
            return 1;
        }
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public StatusEnum getStatus() {
        if(status == null){
            return StatusEnum.ENABLE;
        }
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getTips() {
        if(tips == null){
            return "";
        }
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getErrorMsg() {
        if(errorMsg == null){
            return "";
        }
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

    public EncryptTypeEnum getEncryptType() {
        if(encryptType == null){
            return EncryptTypeEnum.NO_ENCRYPT;
        }
        return encryptType;
    }

    public void setEncryptType(EncryptTypeEnum encryptType) {
        this.encryptType = encryptType;
    }


    public ItemTypeEnum getItemType() {
        return itemType;
    }

    public void setItemType(ItemTypeEnum itemType) {
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
        if(pageNo == null){
            return "";
        }
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public String toString() {
        return "ShopFormControlItemPO{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", onSiteDisplay=" + onSiteDisplay +
                ", toShopDisplay=" + toShopDisplay +
                ", needInput=" + needInput +
                ", tips='" + tips + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", regular='" + regular + '\'' +
                ", encryptType=" + encryptType +
                ", priority=" + priority +
                ", status=" + status +
                ", itemType=" + itemType +
                ", itemData='" + itemData + '\'' +
                ", venderId=" + venderId +
                ", pageNo='" + pageNo + '\'' +
                ", orderField=" + orderField +
                "} " + super.toString();
    }
}
