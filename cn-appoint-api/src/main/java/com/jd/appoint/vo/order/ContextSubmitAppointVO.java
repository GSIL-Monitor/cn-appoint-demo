package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/5/24.
 * 提交预约 VO
 */
public class ContextSubmitAppointVO {
    /**
     * contextId
     */
    @NotNull
    private String contextId;
    /**
     * 提交的其他参数
     */
    private Map<String, String> map;

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
