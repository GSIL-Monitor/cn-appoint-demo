package com.jd.appoint.service.task.constants;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/5 14:10
 */
public class TaskConstants {
    /**
     * com.jd.appoint.service.mq.impl.AppointJmqProducerImpl#noticeAppointInfo(com.jd.appoint.service.mq.msg.AppointNoticeMsg)
     */
    public final static String RENOTICE_APPOINT_JMQ_NOTICE="JMQ_NOTICEAPPOINTINFO";

    /**
     * 完成节点通知短信
     */
    public final static String RENOTICE_SMS_NOTICE= "SMS_NOTICE";

    /**
     * 导入物流单订阅物流信息任务
     */
    public final static String INPUT_LSNS= "INPUT_LSNS";
}
