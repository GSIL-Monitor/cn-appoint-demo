package com.jd.appoint.service.order.operate.finish;

import com.jd.appoint.dao.order.AppointOrderDao;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 服装业务相关的代码操作
 * 【服装】
 *
 * @author lijizhen1@jd.com
 * @date 2018/7/9 18:11
 */
@Service(value = "finishFzAppointExcutor")
public class FinishFzAppointExcutor extends AbstractFinishAppointExcutor {
    private static final Logger logger = LoggerFactory.getLogger(FinishFzAppointExcutor.class);

    @Resource
    private AppointOrderDao appointOrderDao;

    /**
     * 执行服装的完成服务
     *
     * @param finishAppointDto
     * @return
     */
    @Override
    public OperateResultEnum excute(FinishAppointDto finishAppointDto) {
        /**
         * 1.有附件的上传附件【服装】
         * 2.修改预约单状态为预约完成【服装】
         */
        //获得需要持久到数据库的参数
        AppointOrderPO appointOrderPO = finishAppointDto.getAppointOrderPO();
        //如果完成直接返回
        if (AppointStatusEnum.APPOINT_FINISH.equals(appointOrderPO.getAppointStatus())) {
            return OperateResultEnum.SUCCESS;
        }
        //如果不是待服务状态不能执行
        if (!AppointStatusEnum.WAIT_SERVICE.equals(appointOrderPO.getAppointStatus())) {
            logger.error("执行预约完成的时候执行失败appointOrderId={}，节点前置状态不能为appointStatus={}", appointOrderPO.getId(), appointOrderPO.getAppointStatus());
            throw new RuntimeException("当前节点状态不能处理为预约完成");
        }
        //生成调用成功设置为成功
        appointOrderPO.setAppointStatus(AppointStatusEnum.APPOINT_FINISH);
        //修改数据库
        if (appointOrderDao.finishAppoint(appointOrderPO, finishAppointDto.getOverwrite(), FINISH_APPOINT_BEFORE_STATUS.get()) < 1) {
            throw new RuntimeException("完成预约失败");
        }
        //发送消息
        sendMq(appointOrderPO.getId());
        //写入埋点数据
        footPrint(appointOrderPO);
        return OperateResultEnum.SUCCESS;
    }


}
