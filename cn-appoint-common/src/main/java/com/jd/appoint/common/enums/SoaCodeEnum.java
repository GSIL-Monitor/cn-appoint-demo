package com.jd.appoint.common.enums;

import com.jd.travel.base.errors.Errors;

/**
 * Created by luqiang3 on 2018/5/21.
 * 接口响应码配置,api和shopapi统一使用
 */
public enum SoaCodeEnum implements Errors {

    LOGIN_FAIL(1001, "服务人员登录失败"),
    DATA_DUPLICATED(1002, "数据重复"),
    DATA_DELETE_UNEXISTING(1003, "要删除的数据不存在"),
    USERPIN_MISMATCH(1004, "userPin不匹配"),
    CONFIG_NOT_FOUND(1005, "无法获取对应的商家配置"),
    MOBILE_NOT_EXIST(1006, "手机号不存在"),
    USERPIN_NOT_EXIST(1007, "userPin不存在");

    private int code;
    private String reason;

    SoaCodeEnum(int code, String reason){
        this.code = code;
        this.reason = reason;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getReason() {
        return reason;
    }
}
