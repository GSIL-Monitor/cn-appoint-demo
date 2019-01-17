package com.jd.appoint.shop.vo;

/**
 * Created by bjliuyong on 2018/6/7.
 */
public class ClientInfo {

    private String ip  ;
    private String operator  ;

    public ClientInfo(String ip, String operator) {
        this.ip = ip;
        this.operator = operator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
