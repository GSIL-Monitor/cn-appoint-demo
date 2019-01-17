package com.jd.appoint.shopapi;

import com.jd.appoint.vo.schedule.ScheduleModel;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by luqiang3 on 2018/6/15.
 * 产能日历
 */
public interface ShopScheduleFacade {

    /**
     * 产能日历接口
     * @param soaRequest
     * @return
     */
    SoaResponse<ScheduleResult> searchSchedules(SoaRequest<ScheduleVO> soaRequest);

    /**
     * 获取产能日历模式
     * @param soaRequest
     * @return
     */
    SoaResponse<ScheduleModel> searchScheduleModel(SoaRequest<String> soaRequest);
}
