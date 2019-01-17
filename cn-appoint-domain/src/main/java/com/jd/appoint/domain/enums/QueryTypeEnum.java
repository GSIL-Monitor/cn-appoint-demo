package com.jd.appoint.domain.enums;

import com.google.common.base.Splitter;
import com.jd.air.common.enums.IntEnum;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public enum QueryTypeEnum implements IntEnum {
    EQ("等于", 1),
    CONTAIN("包含", 2),
    GTE("大于等于", 3),
    GT("大于", 4),
    LT("小于", 5),
    LTE("小于等于", 6),
    IN("IN", 7);
    private int code;

    QueryTypeEnum(String description, int code) {
        this.code = code;
    }

    @Override
    public int getIntValue() {
        return this.code;
    }
}
