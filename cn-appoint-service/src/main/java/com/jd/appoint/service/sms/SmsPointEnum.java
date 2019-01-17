package com.jd.appoint.service.sms;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/21 10:59
 */
public enum SmsPointEnum {
    NEW_ORDER(1, "新订单", ""),
    INPUT_CARD(10, "导入新订单的卡密", "NEW_INPUT_CARD"),
    WAIT_ORDER(2, "待派单", ""),
    WAIT_SERVICE(3, "待服务", ""),
    DAODIAN_SHENHE(31,"到店审核","DAODIAN_SHENHE"),
    DAOJIA_SHENHE(32,"到家审核","DAOJIA_SHENHE"),
    RESCHEDULING(4, "改期中", ""),
    APPOINT_FAILURE(5, "预约失败", ""),
    CANCELING(6, "取消中", ""),
    APPOINT_DAZHAIXIE_FINISH(81, "预约完成节点导入物流单", "APPOINT_DAZHAIXIE_FINISH"),
    APPOINT_FAHUO_FINISH(82, "预约发货完成", "APPOINT_FAHUO_FINISH"),
    UPDATE_LOGISTICS_NO(83, "物流单号修改", "UPDATE_LOGISTICS_NO"),
    APPOINT_CANCEL(9, "预约取消", ""),
    APPOINT_CANCEL_DEALING(10, "预约取消中", "");

    private int code;
    private String alias;
    private String key;

    SmsPointEnum(int value, String alias, String key) {
        this.code = value;
        this.alias = alias;
        this.key = key;
    }

    public int getIntValue() {
        return this.code;
    }

    public String getAlias() {
        return alias;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
