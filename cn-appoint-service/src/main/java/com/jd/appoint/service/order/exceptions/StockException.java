package com.jd.appoint.service.order.exceptions;

/**
 * Created by luqiang3 on 2018/8/2.
 */
public class StockException extends Exception {

    private String message;

    public StockException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
