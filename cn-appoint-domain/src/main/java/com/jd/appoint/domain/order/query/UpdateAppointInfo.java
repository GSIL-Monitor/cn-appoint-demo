package com.jd.appoint.domain.order.query;


import java.util.Map;

/**
 * Created by gaoxiaoqing on 2018/6/15.
 */
public class UpdateAppointInfo {
    /**
     * 预约单号
     */
    private Long appointOrderId;

    /**
     * 商家id
     */
    private Long venderId;

    /**
     * 商家备注
     */
    private String venderMemo;

    /**
     * 动态表单项
     */
    private Map<String,String> formItems;

    /**
     * 业务Code
     */
    private String businessCode;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getVenderMemo() {
        return venderMemo;
    }

    public void setVenderMemo(String venderMemo) {
        this.venderMemo = venderMemo;
    }

    public Map<String, String> getFormItems() {
        return formItems;
    }

    public void setFormItems(Map<String, String> formItems) {
        this.formItems = formItems;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
