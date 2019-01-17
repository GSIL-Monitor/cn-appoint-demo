package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * 预约记录类型
 * Created by gaoxiaoqing on 2018/5/15.
 */
public enum BuriedSourceEnum implements IntEnum<BuriedSourceEnum>{

    SHOP(1,"shop"),     //Shop端用户
    CUSTOMER(2,"customer"); //用户

    private int code;
    private String name;

    BuriedSourceEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    BuriedSourceEnum(int code) {
        this.code = code;
    }

    @Override
    public int getIntValue() {
        return code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取埋点类型
     * @param buriedType
     * @return
     */
    public static String getBuriedTypeName(int buriedType){
        for(BuriedSourceEnum burryTypeEnum : BuriedSourceEnum.values()){
            if(burryTypeEnum.getCode() == buriedType){
                return burryTypeEnum.name;
            }
        }
        return null;
    }
}
