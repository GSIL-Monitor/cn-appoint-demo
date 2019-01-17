package com.jd.appoint.service.order.operate.finish;

import com.google.common.collect.Lists;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.ProcessTypeEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.record.BuriedPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author lijizhen1@jd.com
 * @date 2018/7/9 18:50
 */
public abstract class AbstractFinishAppointExcutor extends AbstractLocalFinishAppointExcutor implements FinishAppointExcutor {
    private static final Logger logger = LoggerFactory.getLogger(AbstractFinishAppointExcutor.class);
    @Resource
    protected AppointOrderService appointOrderService;
    @Autowired
    protected PlatformTransactionManager transactionManager;
    @Resource
    protected BuriedPointService buriedPointService;
    /**
     * 预约完成前置条件状态
     */
    static final Supplier<List<Integer>> FINISH_APPOINT_BEFORE_STATUS = () -> {
        List<Integer> finishBeforeStatus = Lists.newArrayList();
        //完成预约条件
        finishBeforeStatus.add(AppointStatusEnum.WAIT_SERVICE.getIntValue());
        return finishBeforeStatus;
    };


    /**
     * 记录订单贵
     * @param appointOrderPO
     */
    void footPrint(AppointOrderPO appointOrderPO){
        buriedPointService.statusChangeBuried(appointOrderPO, ProcessTypeEnum.CHANGE_APPOINT_STATUS.getCode());
    }


    @Override
    public void close(){
        super.remove();
    }
}
