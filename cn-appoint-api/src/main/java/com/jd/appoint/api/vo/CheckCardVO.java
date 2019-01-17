package com.jd.appoint.api.vo;

import com.jd.appoint.vo.CommonRequest;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/17 21:43
 */
public class CheckCardVO extends CommonRequest {

    /**
     * 商家ID
     */
    private Long venderId;
    /**
     * 卡密号
     */
    private String cardNumber;
    /**
     * 卡密密码
     */
    private String cartPwd;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCartPwd() {
        return cartPwd;
    }

    public void setCartPwd(String cartPwd) {
        this.cartPwd = cartPwd;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }
}
