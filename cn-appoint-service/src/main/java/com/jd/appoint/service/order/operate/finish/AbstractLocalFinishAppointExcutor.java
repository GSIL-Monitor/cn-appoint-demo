package com.jd.appoint.service.order.operate.finish;

import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.service.mq.AppointJmqProducer;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.operate.OperateService;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author lijizhen1@jd.com
 * @date 2018/7/10 9:42
 */
public abstract class AbstractLocalFinishAppointExcutor {
    private static final Logger logger = LoggerFactory.getLogger(AbstractLocalFinishAppointExcutor.class);
    private static final String OPERATE_CONFIG_PREFIX = "OPERATE_";
    private static final OperateService.OperateEnum operateEnum = OperateService.OperateEnum.APPOINT_FINISH;
    @Resource
    private AppointJmqProducer appointJmqProducer;
    @Autowired
    private VenderConfigService venderConfigService;
    @Resource
    protected AppointOrderService appointOrderService;
    private final static ThreadLocal<AppointOrderDetailVO> dbAppointOrderPO = new ThreadLocal<AppointOrderDetailVO>();

    /**
     * 获得持久化的数据
     *
     * @param appointOrderId
     * @return
     */
    AppointOrderDetailVO getAppointOrderVo(Long appointOrderId) {
        AppointOrderDetailVO vo = dbAppointOrderPO.get();
        if (null != vo) {
            return vo;
        }
        AppointOrderDetailVO appointOrderPO = appointOrderService.fastDetail(appointOrderId);
        if (null == appointOrderId) {
            logger.error("没有找到对应的预约单好appointOrderId为{}的预约单", appointOrderId);
        }
        dbAppointOrderPO.set(appointOrderPO);
        return appointOrderPO;
    }

    /**
     * 获得
     *
     * @param finishAppointDto
     * @return
     */
    VenderConfigVO getVenderConfig(FinishAppointDto finishAppointDto) {
        String businessCode = finishAppointDto.getAppointOrderPO().getBusinessCode();
        Long venderId = finishAppointDto.getAppointOrderPO().getVenderId();
//        if(StringUtils.isBlank(businessCode) || null == venderId){//优先取传入的数据
//            AppointOrderDetailVO appointOrderPO = getAppointOrderVo(finishAppointDto.getAppointOrderPO().getId());
//            businessCode = appointOrderPO.getBusinessCode();
//            venderId = appointOrderPO.getVenderId();
//        }
        return venderConfigService.getConfig(businessCode, venderId, OPERATE_CONFIG_PREFIX + operateEnum);
    }

    /**
     * 发送通知消息
     *
     * @param appointOrderId
     */
    void sendMq(Long appointOrderId) {
        //发送消息通知 降级在数据库任务  FIXME 需要再接入通知消息
        //appointJmqProducer.noticeAppointInfo(new AppointNoticeMsg(appointOrderId, AppointStatusEnum.APPOINT_FINISH.getIntValue()));
    }

    void remove(){
        dbAppointOrderPO.remove();
    }
}
