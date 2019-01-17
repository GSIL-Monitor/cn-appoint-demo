package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * Created by gaoxiaoqing on 2018/5/24.
 */
public enum  BuriedRecordEnum implements IntEnum<BuriedRecordEnum>{
    APPOINT_PROCESS(1,"_process");

    private int code;
    private String value;

    BuriedRecordEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getBuriedRecord(int code){
        for (BuriedRecordEnum buriedRecordEnum : BuriedRecordEnum.values()){
            if(code == buriedRecordEnum.getCode()){
                return buriedRecordEnum.getValue();
            }
        }
        return null;
    }


    @Override
    public int getIntValue() {
        return code;
    }
}
