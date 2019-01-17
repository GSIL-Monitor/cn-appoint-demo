package com.jd.appoint.domain.order;

import com.jd.appoint.domain.base.BaseEntity;

public class AppointOrderServiceItemPO extends BaseEntity {

    /**
     * <pre>
     *
     * 表字段 : appoint_order_service_item.appoint_order_id
     * </pre>
     */
    private Long appointOrderId;

    /**
     * <pre>
     * 城市Id
     * 表字段 : appoint_order_service_item.city_id
     * </pre>
     */
    private String cityId;

    /**
     * <pre>
     * 城市名称
     * 表字段 : appoint_order_service_item.city_name
     * </pre>
     */
    private String cityName;

    /**
     * <pre>
     * 卡号
     * 表字段 : appoint_order_service_item.card_no
     * </pre>
     */
    private String cardNo;

    /**
     * <pre>
     * 卡密
     * 表字段 : appoint_order_service_item.card_password
     * </pre>
     */
    private String cardPassword;

    /**
     * <pre>
     * 套餐名称
     * 表字段 : appoint_order_service_item.package_name
     * </pre>
     */
    private String packageName;

    /**
     * <pre>
     * 套餐编码
     * 表字段 : appoint_order_service_item.package_code
     * </pre>
     */
    private String packageCode;

    /**
     * <pre>
     * 门店名称
     * 表字段 : appoint_order_service_item.store_name
     * </pre>
     */
    private String storeName;

    /**
     * <pre>
     * 门店编码
     * 表字段 : appoint_order_service_item.store_code
     * </pre>
     */
    private String storeCode;

    /**
     * <pre>
     * 门店地址
     * 表字段 : appoint_order_service_item.store_address
     * </pre>
     */
    private String storeAddress;

    /**
     * <pre>
     * 门店电话
     * 表字段 : appoint_order_service_item.store_phone
     * </pre>
     */
    private String storePhone;

    /**
     * <pre>
     * 服务人员名称
     * 表字段 : appoint_order_service_item.staff_name
     * </pre>
     */
    private String staffName;

    /**
     * <pre>
     * 服务人家id
     * 表字段 : appoint_order_service_item.staff_code
     * </pre>
     */
    private String staffCode;

    /**
     * <pre>
     * 服务人员电话
     * 表字段 : appoint_order_service_item.staff_phone
     * </pre>
     */
    private String staffPhone;

    /**
     * <pre>
     * 服务用户的pin
     * 表字段 : appoint_order_service_item.staff_user_pin
     * </pre>
     */
    private String staffUserPin;

    /**
     * <pre>
     * 数据版本
     * 表字段 : appoint_order_service_item.data_version
     * </pre>
     */
    private Integer dataVersion;
    /**
     * 物流来源
     * 表字段 : appoint_order_service_item.logistics_source
     * </pre>
     */
    private String logisticsSource;
    /**
     * 物流单号
     * 表字段 : appoint_order_service_item.logistics_no
     * </pre>
     */
    private String logisticsNo;
    /**
     * 商家备注
     * 表字段 : appoint_order_service_item.vender_memo
     * </pre>
     */
    private String venderMemo;
    /**
     * 物流公司站点id
     * 表字段 : appoint_order_service_item.vender_memo
     * </pre>
     */
    private Integer logisticsSiteId;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
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

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getLogisticsSource() {
        return logisticsSource;
    }

    public void setLogisticsSource(String logisticsSource) {
        this.logisticsSource = logisticsSource;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getVenderMemo() {
        return venderMemo;
    }

    public void setVenderMemo(String venderMemo) {
        this.venderMemo = venderMemo;
    }

    public Integer getLogisticsSiteId() {
        return logisticsSiteId;
    }

    public void setLogisticsSiteId(Integer logisticsSiteId) {
        this.logisticsSiteId = logisticsSiteId;
    }
}