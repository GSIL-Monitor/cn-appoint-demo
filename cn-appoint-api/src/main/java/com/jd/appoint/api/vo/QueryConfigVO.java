package com.jd.appoint.api.vo;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/15 10:42
 */
public class QueryConfigVO extends CommonRequest implements Serializable {

    /**
     * 商家ID
     */
    @NotNull
    private Long venderId;

    /**
     * 键
     */
    @NotNull
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }
}
