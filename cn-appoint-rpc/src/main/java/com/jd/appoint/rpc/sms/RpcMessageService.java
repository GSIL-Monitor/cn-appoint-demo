package com.jd.appoint.rpc.sms;

import com.alibaba.fastjson.JSONArray;
import com.jd.mobilePhoneMsg.sender.client.request.SmsTemplateMessage;
import com.jd.mobilePhoneMsg.sender.client.response.SmsTemplateResponse;
import com.jd.mobilePhoneMsg.sender.client.service.SmsMessageTemplateRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/19 17:27
 */
@Service
public class RpcMessageService {
    private static final Logger logger = LoggerFactory.getLogger(RpcMessageService.class);
    @Resource
    private SmsMessageTemplateRpcService smsMessageTemplateRpcService;
    @Value("#{sms.senderNum}")
    private String senderNum;
    @Value("#{sms.token}")
    private String token;

    /**
     * 发送时候找到需要代理的参数进行组配
     * 发送邮件通知时候和发送短信做的组合
     *
     * @param smsTemplateMessage
     */
    public void sendMsg(SmsTemplateMessage smsTemplateMessage) {
        //TODO需要注入配置信息
        smsTemplateMessage.setSenderNum(senderNum);
        smsTemplateMessage.setToken(token);
        //短信发送接口
        SmsTemplateResponse smsTemplateResponse = smsMessageTemplateRpcService.sendSmsTemplateMessage(smsTemplateMessage);
        if (null != smsTemplateResponse.getResultMsg()
                && "0".equals(smsTemplateResponse.getResultMsg().getErrorCode())
                && "999".equals(smsTemplateResponse.getBaseResultMsg().getErrorCode())) {
            return;
        } else {
            logger.error("发送短信失败：发送返回信息为smsTemplateResponse={},具体错误码查询https://cf.jd.com/pages/viewpage.action?pageId=105716237", JSONArray.toJSONString(smsTemplateResponse));
            throw new RuntimeException("发送短信失败，记录需要重试");
        }
    }


}
