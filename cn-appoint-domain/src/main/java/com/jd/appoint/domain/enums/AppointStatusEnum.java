package com.jd.appoint.domain.enums;


import com.jd.air.common.enums.IntEnum;
import com.jd.air.common.enums.annotations.EnumTags;

/**
 * 预约系统状态
 * Created by gaoxiaoqing on 2018/5/4.
 */
@EnumTags(alias = "appointStatusEnum")
public enum AppointStatusEnum implements IntEnum<AppointStatusEnum> {
    NEW_ORDER(1, "新订单"),
    WAIT_ORDER(2, "待处理"),
    WAIT_SERVICE(3, "待服务"),
    RESCHEDULING(4, "改期中"),
    APPOINT_FAILURE(5, "预约失败"),
    CANCELING(6,"取消中"),
    APPOINT_FINISH(8, "预约完成"),
    APPOINT_CANCEL(9, "预约取消");

    private int code;
    private String alias;


    AppointStatusEnum(int value, String alias) {
        this.code = value;
        this.alias = alias;
    }

    public int getIntValue() {
        return this.code;
    }

    public String getAlias() {
        return alias;
    }

    public static AppointStatusEnum getFromCode(int code) {
        for (AppointStatusEnum appointStatusEnum : AppointStatusEnum.values()) {
            if (appointStatusEnum.code == code) {
                return appointStatusEnum;
            }
        }
        return NEW_ORDER;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    /**
     * 是否可操作
     * @return
     */
    public static boolean isOperate(int code){
        if(code == AppointStatusEnum.WAIT_ORDER.getIntValue() ||
                code == AppointStatusEnum.WAIT_SERVICE.getIntValue()){
            return true;
        }
        return false;
    }
    /**
     * C端待服务不可操作
     * @return
     */
    public static boolean isOperateByPlatform(int code,int platform){
        //C端用户不可操作待服务订单
        if(PlatformEnum.TO_C.getIntValue() == platform &&
                code != AppointStatusEnum.WAIT_ORDER.getIntValue()){
            return false;
        }
        if(code == AppointStatusEnum.WAIT_ORDER.getIntValue() ||
                code == AppointStatusEnum.WAIT_SERVICE.getIntValue()){
            return true;
        }
        return false;
    }
}
