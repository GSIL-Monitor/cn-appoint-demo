package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by luqiang3 on 2018/5/5.
 * 数据库服务时间展示模式
 */
public enum TimeShowTypeEnum implements IntEnum<TimeShowTypeEnum> {

    /**
     * 点
     */
    POINT(1),
    /**
     * 区间
     */
    RANGE(2),

    /**
     * 天
     */
    DAY(3);

    private int value;

    TimeShowTypeEnum(int value){
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
    public static TimeShowTypeEnum byValue(Integer value){
        if(null == value){
            return null;
        }
        for(TimeShowTypeEnum timeShowTypeEnum : TimeShowTypeEnum.values()){
            if(value == timeShowTypeEnum.getIntValue()){
                return timeShowTypeEnum;
            }
        }
        return null;
    }
}
