package com.jd.appoint.service.order.operate.finish;

import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.service.order.operate.OperateService;

/**
 * @author lijizhen1@jd.com
 * @date 2018/7/9 18:09
 */
public interface FinishAppointExcutor {


    /**
     * 执行完成节点的代码
     *
     * @param finishAppointDto
     * @return
     */
    OperateResultEnum excute(FinishAppointDto finishAppointDto);


    /**
     * 关闭执行
     */
    void close();
}
