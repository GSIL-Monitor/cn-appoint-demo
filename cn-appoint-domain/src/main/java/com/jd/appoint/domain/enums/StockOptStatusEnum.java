package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by luqiang3 on 2018/6/14.
 * 库存操作状态
 */
public enum StockOptStatusEnum implements IntEnum<StockOptStatusEnum> {

    /**
     * 扣减
     */
    DECREASE(1),
    /**
     * 回冲
     */
    INCREASE(2);

    private int value;

    StockOptStatusEnum(int value){
        this.value = value;
    }

    @Override
    public int getIntValue() {
        return value;
    }
}
