package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;
import com.jd.appoint.common.utils.EnumDisplay;

/**
 * Created by yangyuan on 6/6/18.
 */
public enum ItemTypeEnum implements IntEnum,EnumDisplay {
    TEXT(1, "文本框"),
    BUTTON(2, "单选按钮"),
    SELECT(3, "下拉框"),
    SELECT_CITY(4, "下拉框_城市"),
    DATE(5, "日期"),
    TEXT_AREA(6, "文本区");

    private int value;

    private String displayName;

    ItemTypeEnum(int value, String displayName){
        this.value = value;
        this.displayName = displayName;
    }

    @Override
    public String getName(){
        return displayName;
    }

    @Override
    public int getIntValue(){
        return value;
    }

    public static ItemTypeEnum ofValue(int value) {
        for (ItemTypeEnum typeEnum : ItemTypeEnum.values()) {
            if (typeEnum.value == value) {
                return typeEnum;
            }
        }
        return null;
    }
}
