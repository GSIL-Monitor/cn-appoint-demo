package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;
import com.jd.air.common.enums.annotations.EnumTags;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/9 14:12
 */
@EnumTags(alias = "serverStatusEnum")
public enum ServerStatusEnum implements IntEnum<ServerStatusEnum> {
    DAILVYUE(0, "待履约"),
    LVYUEZHONG(1, "履约中"),
    LVYUEWANCHENG(2, "履约完成"),
    LVYUESHIBAI(3, "履约失败"),
    GAIPAI(4, "改派");

    private int value;
    private String alias;

    @Override
    public int getIntValue() {
        return this.value;
    }

    ServerStatusEnum(int value, String alias) {
        this.value = value;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
