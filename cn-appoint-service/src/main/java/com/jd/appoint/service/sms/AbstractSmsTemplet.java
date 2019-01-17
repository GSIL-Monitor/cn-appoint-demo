package com.jd.appoint.service.sms;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.common.security.LocalSecurityClient;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.TasksStatusEnum;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import com.jd.appoint.service.sms.dto.AbstractMsgDto;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.service.task.TasksService;
import com.jd.appoint.service.task.constants.TaskConstants;
import com.jd.mobilePhoneMsg.sender.client.request.SmsTemplateMessage;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 抽象的短信模本配置
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/20 12:43
 */
public abstract class AbstractSmsTemplet {
    private static final Logger logger = LoggerFactory.getLogger(AbstractSmsTemplet.class);
    @Resource
    protected VenderConfigService venderConfigService;
    @Resource
    private TasksService tasksService;
    @Autowired
    private LocalSecurityClient localSecurityClient;
    /**
     * 获得模板信息
     *
     * @param msgDto
     * @return
     */
    protected SmsTemplet getTempletId(AbstractMsgDto msgDto) {
        //获得对应的短信配置信息
        VenderConfigVO venderConfigVO = venderConfigService.getConfig(msgDto.getBusinessCode(), msgDto.getVenderId(), msgDto.getKey());
        if (null == venderConfigVO.getValue()) {
            logger.warn("通过businessCode={},VenderId={},key={}没有找到对应的配置信息", msgDto.getBusinessCode(), msgDto.getVenderId(), msgDto.getKey());
            return null;
        }
        //转化短信的配置信息返回
        return new SmsTemplet(venderConfigVO.getValue());
    }


    /**
     * 绑定发送短信的参数
     *
     * @param msgDto
     * @return
     */
    protected SmsTemplateMessage bindSmsTemplateMessage(AbstractMsgDto msgDto) {
        //获得模板的ID
        SmsTemplet templet = getTempletId(msgDto);
        //如果配置模板信息为空，可能是没有配置不需要发送短信
        if (null == templet) {
            return null;
        }
        SmsTemplateMessage templateMessage = new SmsTemplateMessage();
        templateMessage.setMobileNum(msgDto.getMobileNum());
        templateMessage.setTemplateId(templet.getTempletId());
        //模板的配置
        String[] templateParams = templet.getTemplateParams();
        //循环解析的参数
        String[] params = new String[templateParams.length];
        Map<String, String> maps = msgDto.getMsgInfoDto();
        for (int i = 0; i < params.length; i++) {
            String param = maps.get(templateParams[i]);
            if (StringUtils.isEmpty(param)) {
                logger.error("短信配置模板中配置了参数，检查是否传递参数param={}", templateParams[i]);
                throw new RuntimeException("短信配置模板中配置了参数，但是传递参数时候没有传递");
            }
            params[i] = maps.get(templateParams[i]);
        }
        //需要动态解析
        templateMessage.setTemplateParam(params);
        return templateMessage;
    }

    /**
     * 发送短信
     * 代码评审后让全部落地，不做异常后落地
     *
     * @param msgDto
     */
    protected void sendMsg(AbstractMsgDto msgDto) {
        //绑定发送的消息
        SmsTemplateMessage templateMessage = bindSmsTemplateMessage(msgDto);
        if (null == templateMessage) {
            //如果生成的模板信息为空，不发送短信
            logger.error("没有配置对应业务的短信模板");
            return;
        }
        TaskInfoPo taskInfoPo = new TaskInfoPo();
        taskInfoPo.setMaxRetry(4);
        //全部数据进行加密
        taskInfoPo.setContent(localSecurityClient.encrypt(JSONArray.toJSONString(templateMessage)));
        taskInfoPo.setFunctionId(TaskConstants.RENOTICE_SMS_NOTICE);
        taskInfoPo.setTaskStatus(TasksStatusEnum.DEAL_DOING);
        try {
            tasksService.addTask(taskInfoPo);
        } catch (Exception e) {
            logger.error("短信发送任务异常e={}", e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.SMS_ADD_TASK_ERROR);
        }
    }
}
