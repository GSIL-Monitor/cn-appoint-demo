package com.jd.appoint.service.mq.impl;

import com.jd.appoint.domain.enums.TasksStatusEnum;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import com.jd.appoint.service.mq.AppointJmqProducer;
import com.jd.appoint.service.mq.JmqConstants;
import com.jd.appoint.service.mq.msg.AppointNoticeMsg;
import com.jd.appoint.service.task.TasksService;
import com.jd.appoint.service.task.constants.TaskConstants;
import com.jd.fastjson.JSONArray;
import com.jd.jmq.client.producer.MessageProducer;
import com.jd.jmq.common.exception.JMQException;
import com.jd.jmq.common.message.Message;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 预约状态变更
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/16 19:52
 */
@Service
public class AppointJmqProducerImpl implements AppointJmqProducer {
    private static Logger logger = LoggerFactory.getLogger(AppointJmqProducer.class);
    @Resource(name = "appointProducer")
    private MessageProducer producer;
    @Resource
    private TasksService tasksService;


    @Override
    public void noticeAppointInfo(AppointNoticeMsg appointNoticeMsg) {
        //查询出预约单
        appointNoticeMsg.setAppointId(appointNoticeMsg.getAppointId());
        appointNoticeMsg.setAppointStatus(appointNoticeMsg.getAppointStatus());
        appointNoticeMsg.setOccurrenceTime(new Date());
        try {
            sendMessage(producer, new Message(JmqConstants.APPOINT_STATUS_NOTICE,
                    JSONArray.toJSONString(appointNoticeMsg),
                    String.valueOf(appointNoticeMsg.getAppointId())));
        } catch (JMQException e) {
            UmpAlarmUtils.alarm("topic.com.jd.appoint.service.mq.impl.noticeAppointStatus", "预约状态通知发送失败");
            logger.error("发送消息topic={}失败：消息内容message={}", JmqConstants.APPOINT_STATUS_NOTICE, JSONArray.toJSONString(appointNoticeMsg));
            //添加任务降级
            TaskInfoPo taskInfoPo = new TaskInfoPo();
            taskInfoPo.setContent(JSONArray.toJSONString(appointNoticeMsg));
            taskInfoPo.setFunctionId(TaskConstants.RENOTICE_APPOINT_JMQ_NOTICE);
            taskInfoPo.setTaskStatus(TasksStatusEnum.DEAL_DOING);
            taskInfoPo.setMaxRetry(3);
            tasksService.addTask(taskInfoPo);
        }
    }

}
