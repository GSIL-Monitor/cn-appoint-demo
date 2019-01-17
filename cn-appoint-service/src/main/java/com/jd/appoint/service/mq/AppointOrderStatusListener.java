package com.jd.appoint.service.mq;

import com.google.common.collect.Sets;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.service.mq.msg.BlakeOrderMsg;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.binlog.client.EntryMessage;
import com.jd.binlog.client.MessageDeserialize;
import com.jd.binlog.client.WaveEntry;
import com.jd.binlog.client.impl.JMQMessageDeserialize;
import com.jd.jmq.client.consumer.MessageListener;
import com.jd.jmq.common.message.Message;
import com.jd.jmq.fastjson.JSONArray;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * BinLake订单状态监控
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/25 9:49
 */
public class AppointOrderStatusListener extends AbstractMqListener implements MessageListener {

    @Override
    public void onMessage(List<Message> list) throws Exception {
        System.out.println(list);
    }


    @Override
    void process(Message message) throws Exception {
    }

}
