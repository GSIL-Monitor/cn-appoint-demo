package com.jd.appoint.domain.rpc;

import com.jd.appoint.domain.enums.ProcessTypeEnum;

import java.util.Map;

/**
 * Created by gaoxiaoqing on 2018/7/3.
 */
public class BuriedAppoint {
    /**
     * 预约单ID
     */
    private Long appointOrderId;
    /**
     * 流程类型
     */
    private ProcessTypeEnum processTypeEnum;
    /**
     * 参数
     */
    private Map<String,String> params;


    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public ProcessTypeEnum getProcessTypeEnum() {
        return processTypeEnum;
    }

    public void setProcessTypeEnum(ProcessTypeEnum processTypeEnum) {
        this.processTypeEnum = processTypeEnum;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
