package com.jd.appoint.api;

import com.jd.appoint.api.vo.ApiReschuleVO;
import com.jd.appoint.api.vo.OrderDetailWithOperateVO;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by luqiang3 on 2018/5/2.
 * 提供订单相关服务
 */
public interface AppointOrderFacade {

    /**
     * 提交预约单接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<AppointOrderResult> submitAppoint(SoaRequest<SubmitAppointVO> soaRequest);

    /**
     * 从context中
     * @param soaRequest
     * @return
     */
    SoaResponse<AppointOrderResult> contextSubmitAppoint(SoaRequest<ContextSubmitAppointVO> soaRequest);

    /**
     * 预约检查接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse checkAppoint(SoaRequest<SubmitAppointVO> soaRequest);

    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<AppointOrderDetailVO> getAppointOrderDetail(SoaRequest<AppointOrderQueryVO> soaRequest);

    /**
     * 预约完成接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse finished(SoaRequest<AppointFinishVO> soaRequest);

    /**
     * 预约取消接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse cancel(SoaRequest<AppointCancel> soaRequest);


    /**
     * 分页查询预约单列表
     *
     * @param pageSoaRequest
     * @return
     */
    SoaResponse<Page<OrderDetailWithOperateVO>> list(SoaRequest<AppointOrderListRequest> pageSoaRequest);

    /**
     * Api端 改期
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<AppointOrderResult> reschdule(SoaRequest<ApiReschuleVO> soaRequest);


    /**
     * 动态预约单列表（C端）
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Page<ApiAppointOrderDetailVO>> dynamicAppointList(SoaRequest<AppointOrderListRequest> soaRequest);

    /**
     * 预约单列表（C端）
     * @param soaRequest
     * @return
     */
    SoaResponse<Page<OrderDetailWithOperateVO>> appointList(SoaRequest<AppointOrderListRequest> soaRequest);

}
