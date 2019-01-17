package com.jd.appoint.service.order.exceptions;

/**
 * @author lijizhen1@jd.com
 * @date 2018/7/4 19:35
 */
public class InputLnsException extends Exception {

    private Long appointOrderId;
    private String message;

    public InputLnsException(Long appointOrderId, String message) {
        this.appointOrderId=appointOrderId;
        this.message = message;
    }

    public Long getAppointOrderId() {
        return appointOrderId;
    }
}
