package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;
import com.jd.travel.monitor.ValideGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/5/24.
 * 提交预约 VO
 */
public class SubmitAppointVO extends CommonRequest {
    /**
     * 京东商家编号
     */
    @NotNull
    private Long venderId;
    /**
     * 商品skuid
     */
    @NotNull
    private Long skuId;
    /**
     * erp订单号(定金单 )
     */
    private Long erpOrderId;
    /**
     * 门店编码
     */
    private String storeCode;
    /**
     * 服务人员id
     */
    private String staffCode;
    /**
     * 城市Id
     */
    private String cityId;
    /**
     * 套餐编码
     */
    private String packageCode;

    /**
     * 预约客户姓名
     */
    @NotNull
    private String customerName;

    /**
     * 预约客户手机号
     */
    @NotNull
    @Length(min = 5, max = 16)
    private String customerPhone;
    /**
     * 客户pin
     */
    @NotNull
    private String customerUserPin;
    /**
     * 服务类型：上门(1),到店(2)
     */
    @NotNull
    private Integer serverType;

    @NotNull
    private Date appointStartTime;
    @NotNull
    private Date appointEndTime;
    /**
     * contextId
     */
    private String contextId;
    /**
     * 表单项目
     */
    private Map<String, String> formItems;
    /**
     * 由上一步网关接口返回的ext内容
     */
    private String ext;

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(Long erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
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

    public String getCustomerUserPin() {
        return customerUserPin;
    }

    public void setCustomerUserPin(String customerUserPin) {
        this.customerUserPin = customerUserPin;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Map<String, String> getFormItems() {
        return formItems;
    }

    public void setFormItems(Map<String, String> formItems) {
        this.formItems = formItems;
    }

    public Date getAppointStartTime() {
        return appointStartTime;
    }

    public void setAppointStartTime(Date appointStartTime) {
        this.appointStartTime = appointStartTime;
    }

    public Date getAppointEndTime() {
        return appointEndTime;
    }

    public void setAppointEndTime(Date appointEndTime) {
        this.appointEndTime = appointEndTime;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

}
