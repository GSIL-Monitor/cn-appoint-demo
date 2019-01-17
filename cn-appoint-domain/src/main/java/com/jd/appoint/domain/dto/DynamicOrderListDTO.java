package com.jd.appoint.domain.dto;

import java.util.Map;

/**
 * 动态预约单列表转换类
 * Created by gaoxiaoqing on 2018/6/27.
 */
public class DynamicOrderListDTO {
    /**
     * erp订单号
     */
    private Long erpOrderId;

    /**
     * 预约ID
     */
    private Long appointOrderId;

    /**
     * 京东商家编号
     */
    private Long venderId;

    /**
     * 商家名称
     */
    private String venderName;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 预约状态:
     * NEW_ORDER(1, "新订单"),
     * WAIT_ORDER(2, "待派单"),
     * WAIT_SERVICE(3, "待服务"),
     * SERVICING(4,"服务中"),
     * APPOINT_FAILURE(5,"预约失败"),
     * APPOINT_FINISH(8, "预约完成"),
     * APPOINT_CANCEL(9, "预约取消");
     */
    private Integer appointStatus;

    /**
     * 预约状态名称
     */
    private String appointStatusName;

    /**
     * 业务类型code
     */
    private String businessCode;

    /**
     *列表展示内容
     */
    private Map<String,String>  formMaps;

    public Long getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(Long erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

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

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(Integer appointStatus) {
        this.appointStatus = appointStatus;
    }

    public String getAppointStatusName() {
        return appointStatusName;
    }

    public void setAppointStatusName(String appointStatusName) {
        this.appointStatusName = appointStatusName;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Map<String, String> getFormMaps() {
        return formMaps;
    }

    public void setFormMaps(Map<String, String> formMaps) {
        this.formMaps = formMaps;
    }
}
