package com.jd.appoint.shopapi;

import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by luqiang3 on 2018/5/5.
 * 预约时间服务
 */
public interface ShopWorkTimeFacade {

    /**
     * 保存服务时间接口
     * @param soaRequest
     * @return
     */
    SoaResponse saveTime(SoaRequest<WorkTime> soaRequest);

    /**
     * 查询服务时间接口
     * @param soaRequest
     * @return
     */
    SoaResponse<WorkTime> searchTime(SoaRequest<WorkTimeQuery> soaRequest);

    /**
     * 编辑服务时间接口
     * @param soaRequest
     * @return
     */
    SoaResponse editTime(SoaRequest<WorkTime> soaRequest);

}
