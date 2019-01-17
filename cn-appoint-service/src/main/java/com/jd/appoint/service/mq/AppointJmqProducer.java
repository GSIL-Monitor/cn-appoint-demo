package com.jd.appoint.service.mq;

import com.jd.appoint.service.mq.msg.AppointNoticeMsg;
import com.jd.jmq.client.producer.MessageProducer;
import com.jd.jmq.common.exception.JMQException;
import com.jd.jmq.common.message.Message;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 19:50
 */

public interface AppointJmqProducer {


    /**
     * 发送预约单通知消息
     *
     * @param appointNoticeMsg
     */
    void noticeAppointInfo(AppointNoticeMsg appointNoticeMsg);

    /**
     * 默认的消息发送
     *
     * @param producer
     * @param message
     * @throws JMQException
     */
    default void sendMessage(MessageProducer producer, Message message) throws JMQException {
        //message.setForceBot(true);
        producer.send(message);
    }
}
