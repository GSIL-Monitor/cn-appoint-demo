package com.jd.appoint.service.sms;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信的模板映射信息
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/19 18:14
 */
public class SmsTemplet {
    private static final Logger logger = LoggerFactory.getLogger(SmsTemplet.class);
    /**
     * 模本ID
     */
    private Long templetId;
    /**
     * 模板参数配置
     */
    private String[] templateParams;


    public SmsTemplet(String smsTemplet) {
        Templet templet = null;
        try {
            templet = JSONArray.parseObject(smsTemplet, Templet.class);
            this.templetId = templet.getTempletId();
            this.templateParams = templet.templateParams.split(",");
        } catch (Exception e) {
            throw e;
        }
    }

    public Long getTempletId() {
        return templetId;
    }

    public void setTempletId(Long templetId) {
        this.templetId = templetId;
    }

    public String[] getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(String[] templateParams) {
        this.templateParams = templateParams;
    }

    static class Templet {
        private Long templetId;
        private String templateParams;

        public Long getTempletId() {
            return templetId;
        }

        public void setTempletId(Long templetId) {
            this.templetId = templetId;
        }

        public String getTemplateParams() {
            return templateParams;
        }

        public void setTemplateParams(String templateParams) {
            this.templateParams = templateParams;
        }
    }
}
