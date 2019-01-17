package com.jd.appoint.rpc.card.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/16 10:36
 */
public class LockCardDTO extends CardRountBase implements Serializable {
    /**
     * 锁定凭证列表
     */
    private List<CardDTO> lockCards;
    /**
     * 预约系统的预约号
     */
    @NotNull
    private String transactionId;

    public void setLockCards(List<CardDTO> lockCards) {
        this.lockCards = lockCards;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<CardDTO> getLockCards() {
        return lockCards;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
