package com.jd.appoint.service.order.operate;

/**
 * Created by shaohongsen on 2018/6/14.
 */
public enum OperateResultEnum {
    SUCCESS("成功"),
    FAIL("失败"),
    RETRY("执行报错,重试");

    OperateResultEnum(String description) {
    }
}
