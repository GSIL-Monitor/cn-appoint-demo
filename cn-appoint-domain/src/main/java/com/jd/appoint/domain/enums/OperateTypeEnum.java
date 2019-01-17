package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public enum OperateTypeEnum implements IntEnum {
    CANCEL("取消", 1),
    CHANGE_SCHEDULE_DATE("改期-日历", 2),
    CHANGE_SCHEDULE_POINT("改期-时间点", 3),
    REMARK("备注", 4),
    LOGISTICS("查看物流", 5),
    APPOINT_AGAIN("再次预约", 6),
    CUSTOM("自定义", 0);

    private String description;
    private int code;

    OperateTypeEnum(String description, int code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public int getIntValue() {
        return this.code;
    }
}
