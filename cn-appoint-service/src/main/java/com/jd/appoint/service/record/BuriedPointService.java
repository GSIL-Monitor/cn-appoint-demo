package com.jd.appoint.service.record;

import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.rpc.BuriedAppoint;
import com.jd.appoint.vo.order.AppointOrderDetailVO;

import java.util.Map;

/**
 * Created by gaoxiaoqing on 2018/5/29.
 */
public interface BuriedPointService {

    /**
     * 足迹埋点
     * @param traceNO
     * @param processType
     * @param param
     */
    void processBuried(String traceNO, int processType,Map<String,String> param);

    /**
     * 状态变更
     * @param appointOrderPO
     */
    void statusChangeBuried(AppointOrderPO appointOrderPO, int processType);
}
