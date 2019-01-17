package com.jd.appoint.vo.dynamic;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public class ServerTypeRequest extends CommonRequest {
    /**
     * 服务类型： 到家(1),到店(2)
     */
    @NotNull
    private Integer serverType;

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }
}
