package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public enum CustomTypeEnum implements IntEnum {
    OPEN_URL("打开链接", 0),
    AJAX_API("调用接口", 1),
    OPEN_DIV("打开DIV", 2);
    private String description;
    private int code;

    CustomTypeEnum(String description, int code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public int getIntValue() {
        return this.code;
    }
}
