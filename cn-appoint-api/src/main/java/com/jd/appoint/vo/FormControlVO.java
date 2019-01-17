package com.jd.appoint.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangyuan on 5/11/18.
 */
public class FormControlVO implements Serializable{

    private static final long serialVersionUID = 1l;

    /**
     * 是否上门
     */
    private boolean onSite;

    /**
     * 是否上店
     */
    private boolean toShop;

    /**
     * 具体表单控制项
     */
    private List<FormControlItemVO> itemVOList;

    public boolean isOnSite() {
        return onSite;
    }

    public void setOnSite(boolean onSite) {
        this.onSite = onSite;
    }

    public List<FormControlItemVO> getItemVOList() {
        return itemVOList;
    }

    public void setItemVOList(List<FormControlItemVO> itemVOList) {
        this.itemVOList = itemVOList;
    }

    public boolean isToShop() {
        return toShop;
    }

    public void setToShop(boolean toShop) {
        this.toShop = toShop;
    }

    @Override
    public String toString() {
        return "FormItemVO{" +
                "onSite=" + onSite +
                ", toShop=" + toShop +
                ", itemVOList=" + itemVOList +
                '}';
    }
}
