package com.jd.appoint.domain.rpc;

/**
 * 埋点信息
 * Created by gaoxiaoqing on 2018/5/17.
 */
public class BuriedInfo {

    /**
     * 埋点内容
     */
    private String content;
    /**
     * 埋点来源
     */
    private Integer sourceType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }
}
