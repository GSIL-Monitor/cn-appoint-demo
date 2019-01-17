package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by luqiang3 on 2018/5/7.
 * 时间单位枚举
 */
public enum TimeUnitEnum implements IntEnum<TimeUnitEnum> {

    /**
     * 分钟
     */
    MINUTE(1),
    /**
     * 小时
     */
    HOUR(2);

    private int value;

    TimeUnitEnum(int value){
        this.value = value;
    }

    @Override
    public int getIntValue() {
        return this.value;
    }

    /**
     * 通过值获得对应的枚举
     * @param value
     * @return
     */
    public static TimeUnitEnum byValue(Integer value){
        if(null == value){
            return null;
        }
        for(TimeUnitEnum timeUnitEnum : TimeUnitEnum.values()){
            if(value == timeUnitEnum.getIntValue()){
                return timeUnitEnum;
            }
        }
        return null;
    }
}
