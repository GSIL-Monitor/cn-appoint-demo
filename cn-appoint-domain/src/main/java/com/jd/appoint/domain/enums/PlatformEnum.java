package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public enum PlatformEnum implements IntEnum {
    SHOP("供应商", 1),
    STORE("门店", 2),
    TO_C("C端", 3),
    STAFF("小程序端",4);
    private String description;
    private int code;

    PlatformEnum(String descrpition, int code) {
        this.description = descrpition;
        this.code = code;
    }


    @Override
    public String toString() {
        return "PlatformEnum{" +
                "description='" + description + '\'' +
                '}';
    }

    @Override
    public int getIntValue() {
        return this.code;
    }

    public static PlatformEnum getFromCode(Integer code){
        for (PlatformEnum platformEnum:
             PlatformEnum.values()) {
            if(platformEnum.code == code){
                return platformEnum;
            }
        }
        return null;
    }
}
