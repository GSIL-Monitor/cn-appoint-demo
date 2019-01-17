package com.jd.appoint.shopapi.vo;

/**
 * Created by yangyuan on 6/15/18.
 */
public enum StatusEnum {
    /**
     * 有效
     */
    ENABLE(1),
    /**
     * 无效
     */
    DISABLE(2);

    private int value;

    public int getValue(){
        return value;
    }

    StatusEnum(int value){
        this.value = value;
    }

    public static StatusEnum getFromCode(int code){
        for(StatusEnum statusEnum:StatusEnum.values()){
            if(statusEnum.value == code){
                return statusEnum;
            }
        }
        return null;
    }
}
