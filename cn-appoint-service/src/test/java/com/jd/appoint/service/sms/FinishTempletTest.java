package com.jd.appoint.service.sms;

import com.jd.appoint.service.sms.templets.SendMessageService;
import org.junit.Test;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/20 17:11
 */
public class FinishTempletTest extends JUnit4SpringContextTests {

    @Resource
    private SendMessageService sendMessageService;

    @Test
    public void TestsendMsg() {
        sendMessageService.sendMsg(22L, SmsPointEnum.APPOINT_FAHUO_FINISH);
    }
}
