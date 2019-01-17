package com.jd.appoint.domain.shop;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;

import java.util.List;

/**
 * Created by yangyuan on 5/8/18.
 */
public class ShopBusinessPO extends BaseEntity{

    private String code;

    private String name;

    private Boolean onSite;

    private Boolean toShop;

    private StatusEnum status;

    private List<ShopFormControlItemPO> itemList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isOnSite() {
        return onSite;
    }

    public void setOnSite(Boolean onSite) {
        this.onSite = onSite;
    }

    public Boolean isToShop() {
        return toShop;
    }

    public void setToShop(Boolean toShop) {
        this.toShop = toShop;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShopFormControlItemPO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ShopFormControlItemPO> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "ShopBusinessPO{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", onSite=" + onSite +
                ", toShop=" + toShop +
                ", status=" + status +
                "} " + super.toString();
    }
}
