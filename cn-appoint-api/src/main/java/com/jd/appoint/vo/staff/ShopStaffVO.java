package com.jd.appoint.vo.staff;

import com.jd.appoint.shopapi.ShopStaffFacade;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ShopStaffVO implements Serializable {

    /**
     * 服务人员ID
     */
    private Long id;
    /**
     * 商家ID
     */
    @NotNull
    private Long venderId;

    /**
     * 服务人员京东账号
     */
    private String userPin;

    /**
     * 服务人员姓名
     */
    @NotNull
    @Length(max=50)
    private String serverName;

    /**
     * 服务人员电话
     */
    @NotNull
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "手机号不合法")
    private String serverPhone;

    /**
     * 户簿唯一id
     */
    @NotNull
    private String securityId;

    /**
     * 门店ID
     */
    private Long storeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPhone() {
        return serverPhone;
    }

    public void setServerPhone(String serverPhone) {
        this.serverPhone = serverPhone;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
