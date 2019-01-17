package com.jd.appoint.store.domain;

import java.util.Date;

/**
 * Description:
 * Created by: yanghuai.
 * Created DateTime: 2017/3/9 14:19.
 * Project name: pop-o2o-stores.
 */
public class StoreUserInfo {

    /**
     * 用户pin
     */
    private String pin;

    /**
     * 商家编号
     */
    private long venderId;
    /**
     * 公司编号
     */
    private long companyId;
    /**
     * 店铺ID
     */
    private long shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 用户状态
     */
    private int userStatus;
    /**
     * 商家状态
     */
    private int venderStatus;
    /**
     * 店铺状态
     */
    private int shopStatus;

    /**
     * 商家类型
     */
    private int colType;

    /**
     * 账户类型（管理员为1，子账号为0）
     */
    private int userType;
    /**
     * 业务类型
     */
    private int cod = 0;

    /**
     * 权限码串
     */
    private String authCode;

    /**
     * 扩展字段
     */
    private String extJson;

    /**
     * 门店编号 0:非门店帐号; 大于0:门店编号
     */
    private Long storeId;

    /**
     * 帐号属性, 最大20个字符,每位取值为16进制(0-F)代表不同的属性值; 0 代表未设值 第一位: 1 门店导购员帐号
     */
    private String accountAttr;


    /**
     * 门店名称
     */
    private String name;

    /**
     * 地址编号
     */
    private Long addCode;

    /**
     * 地址信息
     */
    private String addName;

    /**
     * 电话号码（座机 或 手机）
     */
    private String phone;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 坐标
     */
    private String coordinate;

    private Date modified;

    private Date created;

    private Integer status;

    private Long id;


    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getVenderId() {
        return venderId;
    }

    public void setVenderId(long venderId) {
        this.venderId = venderId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getVenderStatus() {
        return venderStatus;
    }

    public void setVenderStatus(int venderStatus) {
        this.venderStatus = venderStatus;
    }

    public int getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(int shopStatus) {
        this.shopStatus = shopStatus;
    }

    public int getColType() {
        return colType;
    }

    public void setColType(int colType) {
        this.colType = colType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getAccountAttr() {
        return accountAttr;
    }

    public void setAccountAttr(String accountAttr) {
        this.accountAttr = accountAttr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddCode() {
        return addCode;
    }

    public void setAddCode(Long addCode) {
        this.addCode = addCode;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StoreUserInfo{" +
                "pin='" + pin + '\'' +
                ", venderId=" + venderId +
                ", companyId=" + companyId +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", userStatus=" + userStatus +
                ", venderStatus=" + venderStatus +
                ", shopStatus=" + shopStatus +
                ", colType=" + colType +
                ", userType=" + userType +
                ", cod=" + cod +
                ", authCode='" + authCode + '\'' +
                ", extJson='" + extJson + '\'' +
                ", storeId=" + storeId +
                ", accountAttr='" + accountAttr + '\'' +
                ", name='" + name + '\'' +
                ", addCode=" + addCode +
                ", addName='" + addName + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", coordinate='" + coordinate + '\'' +
                ", modified=" + modified +
                ", created=" + created +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
