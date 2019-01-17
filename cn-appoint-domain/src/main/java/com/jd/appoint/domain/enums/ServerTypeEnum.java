package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;
import com.jd.air.common.enums.annotations.EnumTags;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/9 11:13
 */
@EnumTags(alias = "serverTypeEnum")
public enum ServerTypeEnum implements IntEnum<ServerTypeEnum> {
    SHANGMEN(1, "到家"),
    DAODIAN(2, "到店");

    private int code;
    private String alias;

    @Override
    public int getIntValue() {
        return this.code;
    }

    ServerTypeEnum(int code, String alias) {
        this.code = code;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public static ServerTypeEnum getFromCode(int code) {
        for (ServerTypeEnum serverTypeEnum : ServerTypeEnum.values()) {
            if (serverTypeEnum.code == code) {
                return serverTypeEnum;
            }
        }
        return ServerTypeEnum.DAODIAN;
    }
}
