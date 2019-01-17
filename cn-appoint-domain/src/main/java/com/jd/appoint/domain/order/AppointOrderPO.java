package com.jd.appoint.domain.order;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.DateForm;

import java.util.Date;

/**
 * @author author
 */
public class AppointOrderPO extends BaseEntity {

    private static final long serialVersionUID = 1525916223356L;

    /**
     * 商品skuid
     * isNullAble:1
     */
    private Long skuId;
    /**
     * 商品sku名称
     */
    private String skuName;

    /**
     * 服务类型：上门(1),到店(2)
     * isNullAble:1
     */
    private Integer serverType;

    /**
     * 业务类型code
     * isNullAble:1
     */
    private String businessCode;

    /**
     * 京东商家编号
     * isNullAble:0
     */
    private Long venderId;

    /**
     * 预约客户姓名
     * isNullAble:1
     */
    private String customerName;

    /**
     * 预约客户手机号
     * isNullAble:1
     */
    private String customerPhone;

    /**
     * 客户pin
     * isNullAble:0
     */
    private String customerUserPin;

    /**
     * erp订单号
     * isNullAble:1
     */
    private Long erpOrderId;

    /**
     * end订单号
     * isNullAble:1
     */
    private Long endOrderId;

    /**
     * 预约状态:1.新订单；2.待派单；3.待服务；8.预约取消；9.预约完成
     * isNullAble:0
     */
    private AppointStatusEnum appointStatus;

    /**
     * 预约开始时间
     * isNullAble:1
     */
    private Date appointStartTime;

    /**
     * 预约结束时间
     * isNullAble:1
     */
    private Date appointEndTime;
    /**
     * 预约完成时间
     */
    private Date appointFinishTime;

    /**
     * 附件url，多张以,分割
     * isNullAble:1
     */
    private String attrUrls;

    /**
     * 外部订单编号
     * isNullAble:1
     */
    private String outerOrderId;


    /**
     * 数据版本
     * isNullAble:1
     */
    private Integer dataVersion;

    /**
     * 预约单前置状态
     */
    private AppointStatusEnum preAppointStatus;
    /**
     * 唯一索引，需要幂等提交订单的业务线用
     */
    private String uniqueKey;


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

    public AppointStatusEnum getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(AppointStatusEnum appointStatus) {
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

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getEndOrderId() {
        return endOrderId;
    }

    public void setEndOrderId(Long endOrderId) {
        this.endOrderId = endOrderId;
    }

    public Date getAppointFinishTime() {
        return appointFinishTime;
    }

    public void setAppointFinishTime(Date appointFinishTime) {
        this.appointFinishTime = appointFinishTime;
    }


    public String getStaffPin() {
        return staffPin;
    }

    public void setStaffPin(String staffPin) {
        this.staffPin = staffPin;
    }

    public AppointStatusEnum getPreAppointStatus() {
        return preAppointStatus;
    }

    public void setPreAppointStatus(AppointStatusEnum preAppointStatus) {
        this.preAppointStatus = preAppointStatus;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }


    /**
     * 执行的操作人
     */
    private transient String operateUser;
    /**
     * 瞬时状态，不是持久化的字段
     */
    private transient String staffPin;
    /**
     * 门店代码
     */
    private transient String storeCode;

    /**
     * 标识处理业务的来源，
     */
    private transient DateForm dateForm = null;

    public DateForm getDateForm() {
        return dateForm;
    }

    public void setDateForm(DateForm dateForm) {
        this.dateForm = dateForm;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
