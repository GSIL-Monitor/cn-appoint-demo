package com.jd.appoint.rpc.card.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/16 10:36
 */
public class UnLockCardDTO extends CardRountBase implements Serializable {
    /**
     * 核销的凭证列表（必填）
     */
    private List<CardDTO> unlockCards;
    /**
     * 预约系统的预约号 用在别的系统的幂等操作
     */
    @NotNull
    private String transactionId;

    public List<CardDTO> getUnlockCards() {
        return unlockCards;
    }

    public void setUnlockCards(List<CardDTO> unlockCards) {
        this.unlockCards = unlockCards;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
