package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by luqiang3 on 2018/5/5.
 * 数据库status枚举，用于表示数据的有效性，所有表统一用这个
 */
public enum StatusEnum implements IntEnum<StatusEnum> {

    /**
     * 有效
     */
    ENABLE(1),
    /**
     * 无效
     */
    DISABLE(2),
    /**
     * 删除
     */
    DELETE(9);

    private int value;

    StatusEnum(int value){
        this.value = value;
    }

    @Override
    public int getIntValue() {
        return value;
    }

    /**
     * 通过值获得对应的枚举
     * @param value
     * @return
     */
    public static StatusEnum byValue(Integer value){
        if(null == value){
            return null;
        }
        for(StatusEnum statusEnum : StatusEnum.values()){
            if(value == statusEnum.getIntValue()){
                return statusEnum;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

}
