package com.jd.appoint.service.card.dto;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/14 17:16
 */
public class WriteOffRespDto {

    private int code = FAIL;
    /**
     * 核销返回原因
     */
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //核销成功
    private static final int SUCCESS = 1;
    //核销失败
    private static final int FAIL = -1;
    //已经核销过了
    private static final int HAS_SUCCESS = 2;
    //重试核销
    private static final int NEED_RETRY = 3;
}
