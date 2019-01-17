package com.jd.appoint.shopapi.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by gaoxiaoqing on 2018/6/15.
 */
public class NewShopAppointUpdateVO {
    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;
    /**
     * 预约类型
     */
    @NotNull
    @Max(2)
    private Integer serverType;

    /**
     * 客户姓名
     */
    @Length(max = 100)
    @NotNull
    private String customerName;

    /**
     * 客户手机号(不校验)
     */
    @NotNull
    private String customerPhone;
    /**
     * 商家id
     */
    @NotNull
    private Long venderId;

    /**
     * 动态配置项
     */
    private Map<String,String> formItems;

    /**
     * 附件URL
     */
    private String attachUrls;

    /**
     * 物流单号
     */
    private String logisticsNO;


    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Map<String, String> getFormItems() {
        return formItems;
    }

    public void setFormItems(Map<String, String> formItems) {
        this.formItems = formItems;
    }

    public String getAttachUrls() {
        return attachUrls;
    }

    public void setAttachUrls(String attachUrls) {
        this.attachUrls = attachUrls;
    }

    public String getLogisticsNO() {
        return logisticsNO;
    }

    public void setLogisticsNO(String logisticsNO) {
        this.logisticsNO = logisticsNO;
    }
}
