package com.jd.appoint.api.vo;

import com.jd.appoint.vo.CommonRequest;
import com.jd.appoint.vo.dynamic.ServerTypeVO;

import javax.validation.constraints.NotNull;

/**
 * Created by shaohongsen on 2018/6/11.
 */
public class ProcessConfigRequest {
    /**
     * 当前请求页面编号
     */
    private String pageNo;
    /**
     * contexId
     */
    @NotNull
    private String contextId;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
}
