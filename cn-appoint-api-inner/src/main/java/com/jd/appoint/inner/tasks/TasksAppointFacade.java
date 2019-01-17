package com.jd.appoint.inner.tasks;

import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 18:03
 */
public interface TasksAppointFacade {
    /**
     * 异步执行补充消息
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Boolean> noticeAppointInfo(SoaRequest soaRequest);

    /**
     * 处理改期中订单
     *
     * @param soaRequest
     * @return
     */
    SoaResponse reschduling(SoaRequest soaRequest);

    /**
     * 短信通知重试操作
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Boolean> renoticeSms(SoaRequest soaRequest);


    void submitVenderOrder();

    /**
     * 执行取消预约的调度任务
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Boolean> cancelJob(SoaRequest soaRequest);

    /**
     * 订阅物流信息
     * @param soaRequest
     * @return
     */
    SoaResponse<Boolean> routeSubscribe(SoaRequest soaRequest);

    /**
     * 初始化库存
     */
    void initStock();
}
