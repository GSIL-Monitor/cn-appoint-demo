package com.jd.appoint.service.order.operate.impl.gw;

import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import com.jd.virtual.appoint.AppointmentGwService;
import com.jd.virtual.appoint.appointment.AppointmentRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/6/14.
 */
@Service("gwChangeSchedule")
public class GWChangeSchedule implements BaseOperate {

    private static final Logger logger = LoggerFactory.getLogger(GWChangeSchedule.class);

    private static final String SUCCESS_CODE = "000";

    //@Resource
    private AppointmentGwService appointmentGwService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        //获取
        AppointmentRequest appointmentRequest = appointUpdateGwConvert(detailVO);
        //更新商家信息
        CommonResponse commonResponse = appointmentGwService.update(appointmentRequest);
        if(!SUCCESS_CODE.equals(commonResponse.getCode())){
            logger.error("更新预约单信息异常！ appointOrderId ={}",detailVO.getId());
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_ORDER_RESCHULE);
            return OperateResultEnum.RETRY;
        }
        return OperateResultEnum.SUCCESS;
    }

    /**
     * 网关更新预约单信息
     *
     * @param appointOrderDetailVO
     * @return
     */
    private static AppointmentRequest appointUpdateGwConvert(AppointOrderDetailVO appointOrderDetailVO) {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setStartTime(appointOrderDetailVO.getAppointStartTime());
        appointmentRequest.setEndTime(appointOrderDetailVO.getAppointEndTime());
        appointmentRequest.setCode(appointOrderDetailVO.getCardNo());
        appointmentRequest.setPassword(appointOrderDetailVO.getCardPassword());
        return appointmentRequest;
    }
}
