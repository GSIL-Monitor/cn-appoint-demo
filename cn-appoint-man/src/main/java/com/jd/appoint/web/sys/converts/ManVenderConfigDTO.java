package com.jd.appoint.web.sys.converts;

import java.util.List;
import java.util.Map;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 18:04
 */
public class ManVenderConfigDTO {
    /**
     * 列头信息
     */
    private List<String> header;

    private List<Map<String,Object>> content ;

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }


    public List<Map<String, Object>> getContent() {
        return content;
    }

    public void setContent(List<Map<String, Object>> content) {
        this.content = content;
    }
}
