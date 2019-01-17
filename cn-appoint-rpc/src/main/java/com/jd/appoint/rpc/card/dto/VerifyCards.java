package com.jd.appoint.rpc.card.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 将要核销的列表
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/16 10:44
 */
public class VerifyCards extends CardRountBase implements Serializable {

    private List<VerifyCardUnit> verifyCards;

    public List<VerifyCardUnit> getVerifyCards() {
        return verifyCards;
    }

    public void setVerifyCards(List<VerifyCardUnit> verifyCards) {
        this.verifyCards = verifyCards;
    }
}
