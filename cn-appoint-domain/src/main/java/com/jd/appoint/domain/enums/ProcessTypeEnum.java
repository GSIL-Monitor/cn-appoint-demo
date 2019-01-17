package com.jd.appoint.domain.enums;

import com.jd.air.common.enums.IntEnum;

/**
 * 流程记录节点
 * Created by gaoxiaoqing on 2018/5/17.
 */
public enum ProcessTypeEnum implements IntEnum<ProcessTypeEnum>{
    //预约流传记录细新
    NEW_APPOINT_ORDER(6,"提交预约单，状态更新为【{statusName}】，操作人为【{operateUser}】"),
    CHANGE_APPOINT_STATUS(7,"状态更新为【{statusName}】，操作人为【{operateUser}】"),
    CHANGE_SERVICE_STAFF(8,"【{operateUser}】将服务人员设置为【{staffName}】"),
    CHANGE_SERVICE_TIME(9,"【{operateUser}】将预约时间设置为【{appointTime}】"), //时间格式：2018-01-01 12:00-13:00
    CHANGE_LOGISTICS(10,"【{operateUser}】更新物流信息，快递公司更新为【{logisticsCompany}】，物流单号更新为【{logisticsNo}】");

    ProcessTypeEnum(int code, String content) {
        this.code = code;
        this.content = content;
    }

    private int code;
    private String content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int getIntValue() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取记录内容
     * @param code
     * @return
     */
    public static String getRecordContent(int code){
        for (ProcessTypeEnum recordProcessEnum : ProcessTypeEnum.values()){
            if(recordProcessEnum.code == code){
                return recordProcessEnum.content;
            }
        }
        return null;
    }
}
