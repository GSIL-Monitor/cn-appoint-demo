package com.jd.appoint.vo.order;

/**
 * 附件信息
 * Created by gaoxiaoqing on 2018/7/4.
 */
public class AttachVO {
    /**
     * 附件链接
     */
    private String attrUrls;

    /**
     * 是否重写
     */
    private Boolean overwrite;

    public String getAttrUrls() {
        return attrUrls;
    }

    public void setAttrUrls(String attrUrls) {
        this.attrUrls = attrUrls;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }
}
