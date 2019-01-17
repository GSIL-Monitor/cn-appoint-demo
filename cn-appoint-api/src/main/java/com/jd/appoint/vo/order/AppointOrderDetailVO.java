package com.jd.appoint.vo.order;

import com.google.common.collect.Maps;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/5/10.
 */
public class AppointOrderDetailVO {

    private Long id;

    /**
     * 商品skuid
     */
    private Long skuId;
    /**
     * 商品skuName
     */
    private String skuName;

    /**
     * 服务类型：上门(1),到店(2)
     */
    @NotNull
    private Integer serverType;

    /**
     * 业务类型code
     */
    @NotNull
    private String businessCode;

    /**
     * 京东商家编号
     */
    @NotNull
    private Long venderId;

    /**
     * 预约客户姓名
     */
    private String customerName;

    /**
     * 预约客户手机号
     */
    @NotNull
    private String customerPhone;
    /**
     * 客户pin
     */
    @NotNull
    private String customerUserPin;

    /**
     * erp订单号(定金单 )
     */
    private Long erpOrderId;
    /**
     * 尾款单号
     */
    private Long endOrderId;

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
     * 中文状态映射
     */
    private String chAppointStatus;

    /**
     * 预约开始时间
     */
    @NotNull
    private Date appointStartTime;

    /**
     * 预约结束时间
     */
    private Date appointEndTime;

    /**
     * 预约完成时间
     */
    private Date appointFinishTime;

    /**
     * 附件url，多张以,分割
     */
    private String attrUrls;

    /**
     * 外部订单编号
     */
    private String outerOrderId;


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
     * 服务人员名称
     */
    private String staffName;

    /**
     * 服务人家id
     */
    private String staffCode;

    /**
     * 服务人员电话
     */
    private String staffPhone;

    /**
     * 服务用户的pin
     */
    private String staffUserPin;

    /**
     * 商家备注
     */
    private String venderMemo;

    /**
     * 创建时间
     */
    protected Date created;

    /**
     * 修改时间
     */
    protected Date modified;
    /**
     * 表单提交项
     */
    private Map<String, String> formItems;
    /**
     * 物流公司
     */
    private String logisticsSource;

    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 物流公司站点id
     */
    private Integer logisticsSiteId;

    /**
     * 前置状态
     */
    private Integer preAppointStatus;

    private String chServerType;

    private Integer source = 1;
    private String chSource;

    private String oriCardNo;

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

    public String getChAppointStatus() {
        return chAppointStatus;
    }

    public void setChAppointStatus(String chAppointStatus) {
        this.chAppointStatus = chAppointStatus;
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

    public Date getAppointFinishTime() {
        return appointFinishTime;
    }

    public void setAppointFinishTime(Date appointFinishTime) {
        this.appointFinishTime = appointFinishTime;
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

    public Map<String, String> getFormItems() {
        return formItems;
    }

    public void setFormItems(Map<String, String> formItems) {
        this.formItems = formItems;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getLogisticsSiteId() {
        return logisticsSiteId;
    }

    public void setLogisticsSiteId(Integer logisticsSiteId) {
        this.logisticsSiteId = logisticsSiteId;
    }

    public Integer getPreAppointStatus() {
        return preAppointStatus;
    }

    public void setPreAppointStatus(Integer preAppointStatus) {
        this.preAppointStatus = preAppointStatus;
    }

    public static AppointOrderDetailVO convert(SubmitAppointVO data) {
        AppointOrderDetailVO appointOrderDetailVO = new AppointOrderDetailVO();
        appointOrderDetailVO.setAppointEndTime(data.getAppointEndTime());
        appointOrderDetailVO.setAppointStartTime(data.getAppointStartTime());
        appointOrderDetailVO.setBusinessCode(data.getBusinessCode());
        appointOrderDetailVO.setVenderId(data.getVenderId());
        appointOrderDetailVO.setSkuId(data.getSkuId());
        appointOrderDetailVO.setErpOrderId(data.getErpOrderId());
        appointOrderDetailVO.setStoreCode(data.getStoreCode());
        appointOrderDetailVO.setStaffCode(data.getStaffCode());
        appointOrderDetailVO.setCityId(data.getCityId());
        appointOrderDetailVO.setPackageCode(data.getPackageCode());
        appointOrderDetailVO.setCustomerName(data.getCustomerName());
        appointOrderDetailVO.setCustomerUserPin(data.getCustomerUserPin());
        appointOrderDetailVO.setCustomerPhone(data.getCustomerPhone());
        appointOrderDetailVO.setServerType(data.getServerType());
        appointOrderDetailVO.setFormItems(data.getFormItems() == null ? Maps.newHashMap() : data.getFormItems());
        return appointOrderDetailVO;
    }

    public String getChServerType() {
        return chServerType;
    }

    public void setChServerType(String chServerType) {
        this.chServerType = chServerType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getChSource() {
        return chSource;
    }

    public void setChSource(String chSource) {
        this.chSource = chSource;
    }

    public String getOriCardNo() {
        return oriCardNo;
    }

    public void setOriCardNo(String oriCardNo) {
        this.oriCardNo = oriCardNo;
    }
}
