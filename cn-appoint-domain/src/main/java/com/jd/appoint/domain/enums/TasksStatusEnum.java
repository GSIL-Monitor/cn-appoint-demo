package com.jd.appoint.domain.enums;


import com.jd.air.common.enums.IntEnum;
import com.jd.air.common.enums.annotations.EnumTags;

/**
 * 预约系统状态
 * Created by gaoxiaoqing on 2018/5/4.
 */
@EnumTags(alias = "tasksStatusEnum")
public enum TasksStatusEnum implements IntEnum<TasksStatusEnum> {

    DEAL_DOING(1, "待处理"),
    DEAL_DONE(2, "已处理");

    private int code;
    private String alias;


    TasksStatusEnum(int value, String alias) {
        this.code = value;
        this.alias = alias;
    }

    public int getIntValue() {
        return this.code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    public static TasksStatusEnum getFromCode(int code){
        for (TasksStatusEnum tasksStatusEnum : TasksStatusEnum.values()){
            if(tasksStatusEnum.getIntValue() == code){
                return tasksStatusEnum;
            }
        }
        return TasksStatusEnum.DEAL_DOING;
    }
}
