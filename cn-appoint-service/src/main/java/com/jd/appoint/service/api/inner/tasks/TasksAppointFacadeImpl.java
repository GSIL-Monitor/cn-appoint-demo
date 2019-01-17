package com.jd.appoint.service.api.inner.tasks;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.appoint.common.security.LocalSecurityClient;
import com.jd.appoint.domain.dto.OrderShipsDto;
import com.jd.appoint.domain.dto.ReschuleDTO;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.DateForm;
import com.jd.appoint.domain.enums.TaskTypeEnum;
import com.jd.appoint.domain.enums.TasksStatusEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import com.jd.appoint.inner.tasks.TasksAppointFacade;
import com.jd.appoint.rpc.ExpressService;
import com.jd.appoint.rpc.sms.RpcMessageService;
import com.jd.appoint.service.mq.AppointJmqProducer;
import com.jd.appoint.service.mq.msg.AppointNoticeMsg;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.sms.SmsPointEnum;
import com.jd.appoint.service.sms.templets.SendMessageService;
import com.jd.appoint.service.stock.StockInfoService;
import com.jd.appoint.service.task.TasksService;
import com.jd.appoint.service.task.constants.TaskConstants;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.mobilePhoneMsg.sender.client.request.SmsTemplateMessage;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author lijizhen1@jd.com
 * @date 2018/6/5 15:24
 */
@Service("tasksAppointFacade")
public class TasksAppointFacadeImpl implements TasksAppointFacade {

    private static final Logger logger = LoggerFactory.getLogger(TasksAppointFacadeImpl.class);

    @Resource
    private TasksService tasksService;
    @Resource
    private AppointJmqProducer appointJmqProducer;
    @Resource
    protected RpcMessageService rpcMessageService;
    @Resource
    private AppointOrderService appointOrderService;
    @Autowired
    private LocalSecurityClient localSecurityClient;
    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private SendMessageService sendMessageService;

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA,
            logCollector = @LogCollector(description = "消息通知", classify = TasksAppointFacadeImpl.class))
    public SoaResponse<Boolean> noticeAppointInfo(SoaRequest soaRequest) {
        List<TaskInfoPo> taskInfoPos =
                tasksService.findByFunctionId(TaskConstants.RENOTICE_APPOINT_JMQ_NOTICE);
        if (CollectionUtils.isNotEmpty(taskInfoPos))
            taskInfoPos.stream().forEach(v -> {
                AppointNoticeMsg appointNoticeMsg = JSONObject.parseObject(v.getContent(), AppointNoticeMsg.class);
                //执行调用，
                try {
                    appointJmqProducer.noticeAppointInfo(appointNoticeMsg);
                    tasksService.consumerTask(v.getId());
                } catch (Exception e) {
                    tasksService.failConsumerTask(v.getId());
                }
            });
        return new SoaResponse<>();
    }


    @Override
    @UmpMonitor(serverType = ServerEnum.SOA,
            logCollector = @LogCollector(description = "重复短信通知", classify = TasksAppointFacadeImpl.class))
    public SoaResponse<Boolean> renoticeSms(SoaRequest soaRequest) {
        List<TaskInfoPo> taskInfoPos =
                tasksService.findByFunctionId(TaskConstants.RENOTICE_SMS_NOTICE);
        if (CollectionUtils.isNotEmpty(taskInfoPos))
            taskInfoPos.stream().forEach(v -> {
                SmsTemplateMessage appointNoticeMsg =
                        JSONObject.parseObject(localSecurityClient.decrypt(v.getContent()), SmsTemplateMessage.class);
                //执行调用，
                try {
                    rpcMessageService.sendMsg(appointNoticeMsg);
                    tasksService.consumerTask(v.getId());
                } catch (Exception e) {
                    tasksService.failConsumerTask(v.getId());
                }
            });
        return new SoaResponse<>();
    }

    @UmpMonitor(logCollector =
    @LogCollector(description = "改期中订单任务", classify = TasksAppointFacadeImpl.class))
    @Override
    public SoaResponse reschduling(SoaRequest soaRequest) {
        //获取待处理的任务
        List<TaskInfoPo> taskInfoPoList =
                tasksService.findTaskByTypeAndStatus(TaskTypeEnum.RESCHDULE.toString(), TasksStatusEnum.DEAL_DOING.getIntValue());
        taskInfoPoList.forEach(taskInfoPo -> {
            //增加任务重试次数
            tasksService.failConsumerTask(taskInfoPo.getId());
            //解析任务信息
            ReschuleDTO reschuleDTO = JSON.parseObject(taskInfoPo.getContent(), ReschuleDTO.class);
            AppointOrderDetailVO appointOrderDetailVO = appointOrderService.detail(reschuleDTO.getAppointOrderId());
            if (!validateTask(taskInfoPo)) {
                return;
            }
            //改期流程
            appointOrderService.venderReschdule(appointOrderDetailVO, taskInfoPo.getId());
        });
        return new SoaResponse();
    }

    /**
     * 校验任务有效性
     *
     * @param taskInfoPo
     * @return
     */
    private boolean validateTask(TaskInfoPo taskInfoPo) {
        //重试次数超过最大重试次数
        if (taskInfoPo.getRetryTimes() >= taskInfoPo.getMaxRetry()) {
            logger.error("改期重试次数达到上限，taskId={}", taskInfoPo.getId());
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_ORDER_RESCHULE);
            return false;
        }
        //下单时间大于2分钟
        long createTime = taskInfoPo.getCreated().getTime();
        long nowTime = System.currentTimeMillis();
        long minute = (nowTime - createTime) / 1000 * 60;
        if (minute < 2) {
            return false;
        }
        return true;
    }


    @Override
    public void submitVenderOrder() {
        List<Long> ids = appointOrderService.allNewOrderIds();
        for (long id : ids) {
            try {
                AppointOrderDetailVO detail = appointOrderService.detail(id);
                if (skipOrAlarm(detail)) {
                    continue;
                }
                appointOrderService.submitVenderOrder(detail);
            } catch (Exception e) {
                logger.error("submitVenderOrder error", e);
            }
        }
    }

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "取消预约任务", classify = TasksAppointFacadeImpl.class))
    public SoaResponse<Boolean> cancelJob(SoaRequest soaRequest) {
        //查询预约中的数据
        List<AppointOrderPO> canceling = appointOrderService.findCancelingOrder();
        for (AppointOrderPO appointOrderPO : canceling) {
            appointOrderPO.setDateForm(DateForm.SYSTEM);
            appointOrderPO.setOperateUser("系统");
            appointOrderService.cancel(appointOrderPO);
        }
        return new SoaResponse<>(true);
    }

    /**
     * 初始化库存
     */
    @Override
    public void initStock() {
        stockInfoService.init();
    }

    private boolean skipOrAlarm(AppointOrderDetailVO detail) {
        long createTime = detail.getCreated().getTime();
        long now = System.currentTimeMillis();
        long seconds = (now - createTime) / 1000;
        //1分钟缓冲，1分钟之内的订单暂时不处理，因为有可能之前异步提交的订单还在提交之中
        if (seconds < 1 * 60) {
            return true;
        }
        if (seconds > 5 * 60) {
            logger.error("{}-->商家提单超时，请处理", detail.getId());
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.VENDER_ORDER_RETRY_TIME_OUT.getKey(), "商家提单超时,请处理");
        }
        return false;
    }

    /**
     * 订阅物流信息
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "订阅物流信息任务", classify = TasksAppointFacadeImpl.class))
    public SoaResponse<Boolean> routeSubscribe(SoaRequest soaRequest) {
        for(int i = 0; i < 5; i++){
            List<TaskInfoPo> taskInfoPos = tasksService.findByFunctionId(TaskConstants.INPUT_LSNS);//一次100条
            if (CollectionUtils.isEmpty(taskInfoPos)){
                return new SoaResponse<>();
            }
            taskInfoPos.stream().forEach(v -> {
                OrderShipsDto dto = JSONObject.parseObject(v.getContent(), OrderShipsDto.class);
                try {
                    boolean routeResult = expressService.routeSubscribe(dto.getAppointOrderId(), dto.getLogisticsNo(), dto.getLogisticsSiteId());
                    if(routeResult){//订阅物流成功发送短信
                        sendMessageService.sendMsg(dto.getAppointOrderId(), SmsPointEnum.APPOINT_DAZHAIXIE_FINISH);
                        tasksService.consumerTask(v.getId());
                    }else {
                        tasksService.failConsumerTask(v.getId());
                    }
                } catch (Exception e) {
                    logger.error("订阅物流信息任务异常：", e);
                    UmpAlarmUtils.alarm(AppointUmpAlarmEnum.ROUTE_SUBSCRIBE_INFO);
                    tasksService.failConsumerTask(v.getId());
                }
            });
        }
        return new SoaResponse<>();
    }
}
