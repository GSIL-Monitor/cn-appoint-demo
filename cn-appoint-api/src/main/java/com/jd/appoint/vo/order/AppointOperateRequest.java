package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;


/**
 * Created by gaoxiaoqing on 2018/6/11.
 */
public class AppointOperateRequest extends CommonRequest {
    /**
     * 服务类型
     */
    private Integer serverType;

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }
}
