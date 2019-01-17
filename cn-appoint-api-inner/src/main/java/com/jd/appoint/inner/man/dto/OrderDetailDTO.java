package com.jd.appoint.inner.man.dto;

import com.jd.adminlte4j.annotation.*;
import com.jd.adminlte4j.model.form.FormItemType;
import com.jd.appoint.inner.man.enums.EnumUtils;

import java.util.Date;

/**
 * Created by luqiang3 on 2018/5/19.
 */
@Form(span = 3)
public class OrderDetailDTO {

    @UIFormItem(label = "预约单号")
    private Long id;

    @UIFormItem(type = FormItemType.SELECT, label = "业务类型")
    @DictData({@DictEntry(code = "618618",value = "服装定制"), @DictEntry(code = "123123", value = "体检预约")})
    private String businessCode;

    @UIFormItem(type = FormItemType.SELECT, label = "服务类型")
    @DictProvider(type = EnumUtils.class, method = "getServerType")
    private Integer serverType;

    @UIFormItem(type = FormItemType.SELECT, label = "订单状态")
    @DictProvider(type = EnumUtils.class, method = "getOrderStatus")
    private Integer appointStatus;

    @UIFormItem(label = "商家ID")
    private Long venderId;

    @UIFormItem(label = "用户Pin")
    private String customerUserPin;

    @UIFormItem(label = "用户姓名")
    private String customerName;

    @UIFormItem(label = "用户地址")
    private String customerAddress;

    @UIFormItem(label = "服务开始时间")
    @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm")
    private Date appointStartTime;

    @UIFormItem(label = "服务结束时间")
    @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm")
    private Date appointEndTime;

    @UIFormItem(label = "服务人员姓名")
    private String staffName;

    @UIFormItem(label = "服务人员Pin")
    private String staffUserPin;

    @UIFormItem(label = "SkuId")
    private Long skuId;

    @UIFormItem(label = "定金单ID")
    private Long erpOrderId;

    @UIFormItem(label = "尾款单ID")
    private Long endOrderId;

    @UIFormItem(label = "商家订单ID")
    private String outerOrderId;

    @UIFormItem(label = "下单时间")
    @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date created;

    /**
     * 预约客户手机号
     */
    private String customerPhone;

    /**
     * 附件url，多张以,分割
     */
    private String attrUrls;

    /**
     * 城市Id
     */
    private String cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 卡密
     */
    private String cardPassword;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐编码
     */
    private String packageCode;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 门店编码
     */
    private String storeCode;

    /**
     * 门店地址
     */
    private String storeAddress;

    /**
     * 门店电话
     */
    private String storePhone;

    /**
     * 服务人家id
     */
    private String staffCode;

    /**
     * 服务人员电话
     */
    private String staffPhone;

    /**
     * 修改时间
     */
    private Date modified;

    //get set
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerUserPin() {
        return customerUserPin;
    }

    public void setCustomerUserPin(String customerUserPin) {
        this.customerUserPin = customerUserPin;
    }

    public Long getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(Long erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

    public Long getEndOrderId() {
        return endOrderId;
    }

    public void setEndOrderId(Long endOrderId) {
        this.endOrderId = endOrderId;
    }

    public Integer getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(Integer appointStatus) {
        this.appointStatus = appointStatus;
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

    public String getAttrUrls() {
        return attrUrls;
    }

    public void setAttrUrls(String attrUrls) {
        this.attrUrls = attrUrls;
    }

    public String getOuterOrderId() {
        return outerOrderId;
    }

    public void setOuterOrderId(String outerOrderId) {
        this.outerOrderId = outerOrderId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getStaffUserPin() {
        return staffUserPin;
    }

    public void setStaffUserPin(String staffUserPin) {
        this.staffUserPin = staffUserPin;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
