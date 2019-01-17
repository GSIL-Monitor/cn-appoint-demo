package com.jd.appoint.shopapi;

import com.jd.appoint.shopapi.vo.*;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.dynamic.table.DynamicTable;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 预约单操作
 * Created by gaoxiaoqing on 2018/5/11.
 */
public interface ShopAppointOrderFacade {
    /**
     * Shop端修改预约单
     *
     * @param soaRequest
     * @return
     */
    SoaResponse editAppointOrder(SoaRequest<ShopAppointUpdateVO> soaRequest);

    /**
     * Shop预约取消接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse cancel(SoaRequest<AppointCancel> soaRequest);

    /**
     * 预约完成接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse finished(SoaRequest<AppointFinishVO> soaRequest);

    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<AppointOrderDetailVO> getAppointOrderDetail(SoaRequest<ShopAppointOrderQueryVO> soaRequest);


    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    /*@Version("2")*/
    SoaResponse<DynamicOrderDetailVO> dynamicGetAppointOrderDetail(SoaRequest<DynamicShopAppointOrderQuery> soaRequest);

    /**
     * 分页查询预约单列表
     *
     * @param pageSoaRequest
     * @return
     */
    SoaResponse<Page<AppointOrderDetailVO>> list(SoaRequest<ShopAppointOrderListRequest> pageSoaRequest);

    /**
     * 根据查询条件获取预约单统计数据
     * 日期为空获取今天的
     *
     * @param query
     */
    SoaResponse<OrderStatisticVO> statisticByDate(SoaRequest<OrderStatisticQuery> query);

    /**
     * 派单
     *
     * @param soaRequest
     * @return
     */
    SoaResponse dispatcher(SoaRequest<DispatchOrderVO> soaRequest);

    /**
     * 动态预约单列表
     *
     * @param pageSoaRequest
     * @return
     */
    SoaResponse<DynamicTable> dynamicList(SoaRequest<ShopAppointOrderListRequest> pageSoaRequest);

    /**
     * Shop端动态修改预约单
     *
     * @param soaRequest
     * @return
     */
    SoaResponse dynamicUpdateAppoint(SoaRequest<UpdateAppointVO> soaRequest);

    /**
     * 更新附属信息
     *
     * @param soaRequest
     * @return
     */
    SoaResponse updateAttachInfo(SoaRequest<UpdateAttachVO> soaRequest);

    /**
     * 预约单审核
     *
     * @param soaRequest
     * @return
     */
    SoaResponse checkAppointOrder(SoaRequest<CheckOrderVO> soaRequest);

    /**
     * Shop端 改期
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<AppointOrderResult> reschdule(SoaRequest<ReschduleVO> soaRequest);

    /**
     * 导入物流单号
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Long> inputLsns(SoaRequest<LsnInputVO> soaRequest);

    /**
     * 导出预约单列表
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<List<LinkedHashMap<String, String>>> exportAppointOrders(SoaRequest<ShopAppointOrderListRequest> soaRequest);

    public void pressure(long count);

}
