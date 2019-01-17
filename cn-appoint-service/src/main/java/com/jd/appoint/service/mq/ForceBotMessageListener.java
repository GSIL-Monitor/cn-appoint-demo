package com.jd.appoint.service.mq;

import com.jd.jmq.client.consumer.MessageListener;
import com.jd.jmq.common.message.Message;
import org.apache.log4j.Logger;


/**
 * 压测消息监听
 * Created by lijianzhen1 on 2017/8/31.
 */
public class ForceBotMessageListener extends AbstractMqListener implements MessageListener {
    private static final Logger logger = Logger.getLogger(ForceBotMessageListener.class);

    @Override
    void process(Message message) throws Exception {
        logger.info(String.format("收到一条ForceBot的消息,消息主题（队列名）：%s,内容是：%s",
                message.getTopic(), message.getText()));
    }
}
