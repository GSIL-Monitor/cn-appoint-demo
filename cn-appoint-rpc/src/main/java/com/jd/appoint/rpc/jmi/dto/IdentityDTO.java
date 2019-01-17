package com.jd.appoint.rpc.jmi.dto;

import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;

/**
 * Created by shaohongsen on 2018/5/21.
 */
public class IdentityDTO {
    /**
     * 京东用户账号
     */
    private String userPin;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件类型
     */
    private JICCPapersTypeEnum certType;
    /**
     * 证件号码
     */
    private String certNum;

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JICCPapersTypeEnum getCertType() {
        return certType;
    }

    public void setCertType(JICCPapersTypeEnum certType) {
        this.certType = certType;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    @Override
    public String toString() {
        return "IdentityDTO{" +
                "userPin='" + userPin + '\'' +
                ", name='" + name + '\'' +
                ", certType=" + certType +
                ", certNum='" + certNum + '\'' +
                '}';
    }
}
