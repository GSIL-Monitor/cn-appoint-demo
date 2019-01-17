package com.jd.appoint.rpc.card.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 核销最小单元
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/16 10:48
 */
public class VerifyCardUnit implements Serializable {
    /**
     * 卡密信息
     */
    private List<CardDTO> verifyCards;
    /**
     * 交易单号
     */
    private String transactionId;
    /**
     * 核销次数
     */
    private Integer verifyTimes;
    /**
     * 手机号（非必填 核销手机号）
     */
    private String mobile;

    public List<CardDTO> getVerifyCards() {
        return verifyCards;
    }

    public void setVerifyCards(List<CardDTO> verifyCards) {
        this.verifyCards = verifyCards;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getVerifyTimes() {
        return verifyTimes;
    }

    public void setVerifyTimes(Integer verifyTimes) {
        this.verifyTimes = verifyTimes;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
