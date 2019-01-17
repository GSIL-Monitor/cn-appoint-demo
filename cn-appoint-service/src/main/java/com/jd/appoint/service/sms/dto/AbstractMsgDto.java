package com.jd.appoint.service.sms.dto;

import java.util.Map;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/20 16:35
 */
public class AbstractMsgDto {
    /**
     * 不满足的配置信息在
     * 短信内容信息 在发送时候映射严格顺序短信模板的{0},{1}
     */
    protected Map<String, String> msgInfoDto;


    public Map<String, String> getMsgInfoDto() {
        return msgInfoDto;
    }

    public void setMsgInfoDto(Map<String, String> msgInfoDto) {
        this.msgInfoDto = msgInfoDto;
    }

    /**
     * 通知的手机号
     */
    protected String mobileNum;
    /**
     * 业务商家ID
     */
    protected Long venderId;
    /**
     * 业务代码
     */
    protected String businessCode;
    /**
     * 数据库中存放的关键的字
     */
    protected String key;

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
