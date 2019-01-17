package com.jd.appoint.storeapi;

import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.dynamic.DynamicAppointOrder;
import com.jd.appoint.vo.dynamic.table.DynamicTable;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public interface StoreAppointOrderFacade {

    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    /*@Version("2")*/
    SoaResponse<DynamicOrderDetailVO> dynamicGetAppointOrderDetail(SoaRequest<DynamicStoreAppointOrderQuery> soaRequest);

    /**
     * 动态预约单列表
     *
     * @param pageSoaRequest
     * @return
     */
    SoaResponse<DynamicTable> dynamicList(SoaRequest<StoreAppointOrderListRequest> pageSoaRequest);

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
     * 导出预约单列表
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<List<LinkedHashMap<String, String>>> exportAppointOrders(SoaRequest<StoreAppointOrderListRequest> soaRequest);

    /**
     * Shop端动态修改预约单
     *
     * @param soaRequest
     * @return
     */
    SoaResponse dynamicUpdateAppoint(SoaRequest<UpdateAppointVO> soaRequest);

    /**
     * 门店预约取消接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse cancel(SoaRequest<AppointCancel> soaRequest);

    /**
     * 门店预约完成接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse finished(SoaRequest<AppointFinishVO> soaRequest);

}
