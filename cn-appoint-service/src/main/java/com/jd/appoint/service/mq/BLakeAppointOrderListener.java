package com.jd.appoint.service.mq;

import com.jd.appoint.service.mq.msg.BlakeOrderMsg;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.binlog.client.EntryMessage;
import com.jd.binlog.client.MessageDeserialize;
import com.jd.binlog.client.WaveEntry;
import com.jd.binlog.client.impl.JMQMessageDeserialize;
import com.jd.jmq.client.consumer.MessageListener;
import com.jd.jmq.common.message.Message;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * BinLake订单状态监控
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/25 9:49
 */
public class BLakeAppointOrderListener extends AbstractMqListener implements MessageListener {
    private static final String Appoint_BLakeAppointOrderListener = "com.jd.appoint.service.mq.BLakeAppointOrderListener";

    private static Logger logger = LoggerFactory.getLogger(BLakeAppointOrderListener.class);
    private static MessageDeserialize<Message> deserialize = new JMQMessageDeserialize();

    @Resource
    private EsOrderService esOrderService;

    @Override
    public void onMessage(List<Message> list) throws Exception {
        CallerInfo profiler = null;
        Set<String> sets = new HashSet<>();
        try {
            profiler = Profiler.registerInfo(Appoint_BLakeAppointOrderListener, false, true);
            for (EntryMessage entryMessage : deserialize.deserialize(list)) {
                logger.info("binlake entryMessage:{}", entryMessage);
                //事物开始
                if (WaveEntry.EntryType.TRANSACTIONBEGIN.equals(entryMessage.getEntryType())) {
                } else {
                    //结束事物
                    if (WaveEntry.EntryType.TRANSACTIONEND.equals(entryMessage.getEntryType())) {
                        //没有结束事物执行else
                    } else {
                        BlakeOrderMsg blakeOrderMo = new BlakeOrderMsg(entryMessage.getHeader(), entryMessage.getRowChange());
                        sets.addAll(blakeOrderMo.getAppointOrderId());
                    }
                }
            }
            //已经达到缓存数据循环或者时间片到达
            esOrderService.batchIndex(sets);
        } catch (Exception e) {
            //由于是严格顺序消息
            Profiler.functionError(profiler);
            logger.error("收到Binlake的消息处理有异常，具体异常信息为{}", e);
        } finally {
            Profiler.registerInfoEnd(profiler);
        }
    }


    @Override
    void process(Message message) throws Exception {
    }
}
