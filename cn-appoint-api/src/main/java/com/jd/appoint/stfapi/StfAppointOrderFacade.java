package com.jd.appoint.stfapi;

import com.jd.appoint.stfapi.vo.StaffAppointOrderListRequest;
import com.jd.appoint.stfapi.vo.StfAppointOrderQueryVO;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.order.Attach;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by luqiang3 on 2018/5/2.
 * 提供订单相关服务
 */
public interface StfAppointOrderFacade {

    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<AppointOrderDetailVO> getAppointOrderDetail(SoaRequest<StfAppointOrderQueryVO> soaRequest);

    /**
     * 上传附件接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse submitAttach(SoaRequest<Attach> soaRequest);

    /**
     * 量体师预约取消接口
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
    SoaResponse<Page<AppointOrderDetailVO>> list(SoaRequest<StaffAppointOrderListRequest> pageSoaRequest);
}
