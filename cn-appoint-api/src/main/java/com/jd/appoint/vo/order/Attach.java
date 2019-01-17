package com.jd.appoint.vo.order;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/3.
 * 订单附件信息
 */
public class Attach implements Serializable {

    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;

    /**
     * 附件地址，多个英文逗号“，”分隔
     */
    @NotNull
    private String urls;
    /**
     * 覆盖 true，追加 false，
     */
    @NotNull
    private Boolean overwrite;

    /**
     * 附件描述
     */
    private String desc;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
