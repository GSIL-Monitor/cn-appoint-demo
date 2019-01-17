package com.jd.appoint.domain.enums;

/**
 * Created by luqiang3 on 2018/7/17.
 * 卡密校验结果
 */
public enum CheckCardResultEnum {

    /**
     * 成功
     */
    SUCCESS,
    /**
     * 失败
     */
    FAIL,
    /**
     * 凭证或密码错误
     */
    CODE_PASSWORD_ERROR,
    /**
     * 卡号不存在
     */
    CODE_NOT_EXISTS,
    /**
     * 已被预约
     */
    APPOINTED,
    /**
     * 已过期
     */
    EXPIRED,
    /**
     * 凭证无效
     */
    DISABLE;
}
