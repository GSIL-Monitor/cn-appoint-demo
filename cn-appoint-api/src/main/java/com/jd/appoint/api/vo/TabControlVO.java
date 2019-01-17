package com.jd.appoint.api.vo;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/12.
 */
public class TabControlVO {
    /**
     * 服务类型
     */
    private Integer serverType;
    /**
     * 服务类型中文
     */
    private String chServerType;
    /**
     * 此业务类型下，需要填写的内容
     */
    private List<DynamicFormItemVo> formItemVOList;

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getChServerType() {
        return chServerType;
    }

    public void setChServerType(String chServerType) {
        this.chServerType = chServerType;
    }

    public List<DynamicFormItemVo> getFormItemVOList() {
        return formItemVOList;
    }

    public void setFormItemVOList(List<DynamicFormItemVo> formItemVOList) {
        this.formItemVOList = formItemVOList;
    }
}
