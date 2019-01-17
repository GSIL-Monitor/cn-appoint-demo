package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public enum InputTypeEnum implements IntEnum {
    TEXT("文本框", 1),
    SELECT("下拉选择框", 2),
    DATE("日期选择框", 3),
    DOUBLE_DATE("日期双选框", 4),
    DYNAMIC_SELECT("动态下拉框", 5);
    private String description;
    private int code;

    InputTypeEnum(String descrpition, int code) {
        this.description = descrpition;
        this.code = code;
    }


    @Override
    public String toString() {
        return "InputTypeEnum{" +
                "description='" + description + '\'' +
                '}';
    }

    @Override
    public int getIntValue() {
        return this.code;
    }
}
