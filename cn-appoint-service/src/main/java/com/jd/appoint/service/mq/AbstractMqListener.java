package com.jd.appoint.service.mq;

import com.jd.jmq.client.consumer.MessageListener;
import com.jd.jmq.common.message.Message;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/3/28 16:20
 */
public abstract class AbstractMqListener implements MessageListener {
    /**
     * 消费方法。注意: 消费不成功请抛出异常，JMQ会自动重试
     *
     * @param messages
     * @throws Exception
     */
    @Override
    public void onMessage(List<Message> messages) throws Exception {
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        for (Message message : messages) {
            process(message);
        }
    }

    /**
     * 默认执行的参数
     *
     * @param message
     */
    abstract void process(Message message) throws Exception;
}
