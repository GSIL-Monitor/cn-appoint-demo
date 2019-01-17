package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;
import com.jd.air.common.enums.annotations.EnumTags;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/9 11:13
 */
public enum DZXServerTypeEnum {
    邮寄到家(1),
    到店提货(2);

    private int code;

    DZXServerTypeEnum(int code) {
        this.code = code;
    }

    public static DZXServerTypeEnum getFromCode(int code) {
        for (DZXServerTypeEnum serverTypeEnum : DZXServerTypeEnum.values()) {
            if (serverTypeEnum.code == code) {
                return serverTypeEnum;
            }
        }
        return null;
    }
}
