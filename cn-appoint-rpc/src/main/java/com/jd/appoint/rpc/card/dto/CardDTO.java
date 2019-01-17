package com.jd.appoint.rpc.card.dto;

import java.io.Serializable;

/**
 * 卡密校验传递的参数
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/15 16:47
 */
public class CardDTO extends CardRountBase implements Serializable {
    private String cardNumber;
    private String cardPwd;


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }
}
