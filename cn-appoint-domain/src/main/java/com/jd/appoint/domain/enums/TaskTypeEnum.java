package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;
import com.jd.air.common.enums.annotations.EnumTags;

/**
 * Created by gaoxiaoqing on 2018/7/2.
 */
@EnumTags(alias = "taskTypeEnum")
public enum TaskTypeEnum implements IntEnum<TaskTypeEnum>{

    RESCHDULE(1,"改期");

    private int code;
    private String alias;

    TaskTypeEnum(int code, String alias) {
        this.code = code;
        this.alias = alias;
    }

    @Override
    public int getIntValue() {
        return code;
    }


}
