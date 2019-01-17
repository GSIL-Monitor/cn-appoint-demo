package com.jd.appoint.inner.man.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.jd.adminlte4j.annotation.UIFormItem;

import java.util.List;

/**
 * Created by yangyuan on 5/16/18.
 */
public class ShopBusinessDTO {

    private Long id;

    @UIFormItem(label = "业务编号")
    private String code;

    @UIFormItem(label = "业务类型")
    private String name;

    @UIFormItem(label = "服务类型")
    private boolean onSite;

    private boolean toShop;

    @UIFormItem(label = "预约信息设置")
    @JSONField(serialize = false)
    private List<FormControlItemDTO> controlItemDTOList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isToShop() {
        return toShop;
    }

    public void setToShop(boolean toShop) {
        this.toShop = toShop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnSite() {
        return onSite;
    }

    public void setOnSite(boolean onSite) {
        this.onSite = onSite;
    }

    public List<FormControlItemDTO> getControlItemDTOList() {
        return controlItemDTOList;
    }

    public void setControlItemDTOList(List<FormControlItemDTO> controlItemDTOList) {
        this.controlItemDTOList = controlItemDTOList;
    }

    @Override
    public String toString() {
        return "ShopBusinessDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", onSite=" + onSite +
                ", toShop=" + toShop +
                ", controlItemDTOList=" + controlItemDTOList +
                '}';
    }
}
