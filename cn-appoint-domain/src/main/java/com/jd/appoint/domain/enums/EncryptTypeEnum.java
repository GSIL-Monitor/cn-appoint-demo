package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;
import com.jd.appoint.common.utils.EnumDisplay;

/**
 * Created by yangyuan on 5/14/18.
 */
public enum EncryptTypeEnum implements IntEnum ,EnumDisplay {
    NO_ENCRYPT(1,"不加密"),
    SAFETY_ENCRYPT(2,"安全部加密"),
    JMI_ENCRYPT(3,"户簿加密");

    private int value;

    private String displayName;


    EncryptTypeEnum(int value, String diaplayName) {
        this.value = value;
        this.displayName = diaplayName;
    }

    public String getName() {
        return displayName;
    }

    @Override
    public int getIntValue() {
        return value;
    }

    public static EncryptTypeEnum ofValue(int value) {
        for (EncryptTypeEnum typeEnum : EncryptTypeEnum.values()) {
            if (typeEnum.value == value) {
                return typeEnum;
            }
        }
        return null;
    }

}
