package com.jd.appoint.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/2.
 * 公共请求参数
 */
public class CommonRequest implements Serializable{

    /**
     * 业务编码，由预约系统分配
     */
    @NotNull
    protected String businessCode;

    //get set
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
