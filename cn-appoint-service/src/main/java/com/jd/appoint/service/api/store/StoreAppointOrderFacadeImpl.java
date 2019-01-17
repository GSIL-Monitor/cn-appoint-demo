package com.jd.appoint.service.api.store;

import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.DateForm;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.service.api.convert.ReschduleConvert;
import com.jd.appoint.service.api.shop.convert.DynamicTableConverter;
import com.jd.appoint.service.config.OrderListConfigService;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.DynamicOrderService;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.storeapi.StoreAppointOrderFacade;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.dynamic.DynamicAppointOrder;
import com.jd.appoint.vo.dynamic.table.DynamicTable;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by gaoxiaoqing on 2018/7/9.
 */
@Service("storeAppointOrderFacade")
public class StoreAppointOrderFacadeImpl implements StoreAppointOrderFacade {

    private static final Logger logger = LoggerFactory.getLogger(StoreAppointOrderFacade.class);

    @Resource
    private AppointOrderService appointOrderService;
    @Resource
    private DynamicOrderService dynamicOrderService;
    @Autowired
    private OrderListConfigService orderListConfigService;


    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端预约单详情接口", classify = StoreAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<DynamicOrderDetailVO> dynamicGetAppointOrderDetail(@ValideGroup SoaRequest<DynamicStoreAppointOrderQuery> soaRequest) {
        SoaResponse<List<OrderDetailGroupVO>> soaResponse = new SoaResponse<>();
        DynamicStoreAppointOrderQuery storeAppointOrderQuery = soaRequest.getData();
        return appointOrderService.dynamicDetail(storeAppointOrderQuery.getAppointOrderId(), storeAppointOrderQuery.getBusinessCode(), PlatformEnum.STORE.getIntValue(), storeAppointOrderQuery.getServerType(), null, storeAppointOrderQuery.getStoreId());

    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端预约单列表", classify = StoreAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<DynamicTable> dynamicList(SoaRequest<StoreAppointOrderListRequest> pageSoaRequest) {
        StoreAppointOrderListRequest data = pageSoaRequest.getData();
        List<OrderListConfigPO> orderListConfigPOS = orderListConfigService.getListItems(data.getBusinessCode(), data.getServerType(), PlatformEnum.STORE.getIntValue());
        Page<DynamicAppointOrder> dynamicAppointOrderPage = dynamicOrderService.dynamicList(data.toPage(), data.getBusinessCode(), orderListConfigPOS);
        DynamicTable dynamicTable = DynamicTableConverter.convert(dynamicAppointOrderPage, orderListConfigPOS);
        return new SoaResponse<>(dynamicTable);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端审核预约单", classify = StoreAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse checkAppointOrder(@ValideGroup(groups = StoreAppointOrderFacade.class) SoaRequest<CheckOrderVO> soaRequest) {
        List<Long> appointIds = null;
        try {
            appointIds = appointOrderService.checkAppointOrder(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            logger.error("门店端审核预约单,参数异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        //单个审核不通过情况
        if (1 == soaRequest.getData().getAppointOrderIds().size() && !CollectionUtils.isEmpty(appointIds)) {
            return new SoaResponse(SoaError.SERVER_EXCEPTION);
        }
        return new SoaResponse(appointIds);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端改期", classify = StoreAppointOrderFacade.class))
    @Override
    public SoaResponse<AppointOrderResult> reschdule(@ValideGroup(groups = StoreAppointOrderFacade.class) SoaRequest<ReschduleVO> soaRequest) {
        AppointOrderResult appointOrderResult = null;
        try {
            appointOrderResult = appointOrderService.reschdule(ReschduleConvert.storeToReschduleDTO(soaRequest.getData()));
        } catch (IllegalArgumentException e) {
            logger.error("改期参数异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
        //改期失败
        if (null != appointOrderResult &&
                AppointStatusEnum.APPOINT_FAILURE.getIntValue() == appointOrderResult.getAppointStatus()) {
            return new SoaResponse(SoaError.SERVER_EXCEPTION);
        }
        return new SoaResponse(appointOrderResult);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端导出预约单列表", classify = StoreAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<List<LinkedHashMap<String, String>>> exportAppointOrders(SoaRequest<StoreAppointOrderListRequest> soaRequest) {
        AppointOrderListQuery appointOrderListQuery = new AppointOrderListQuery();
        List<LinkedHashMap<String, String>> appointOrderList = null;
        try {
            BeanUtils.copyProperties(soaRequest.getData(), appointOrderListQuery);
            appointOrderList = dynamicOrderService.exportDynamicList(appointOrderListQuery, PlatformEnum.STORE.getIntValue());
        } catch (IllegalArgumentException e) {
            logger.error("门店端导出预约单列表异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        return new SoaResponse<>(appointOrderList);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端修改预约单", classify = StoreAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse dynamicUpdateAppoint(@ValideGroup(groups = StoreAppointOrderFacade.class) SoaRequest<UpdateAppointVO> soaRequest) {
        try {
            appointOrderService.dynamicUpdateAppoint(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            logger.error("门店端修改预约单异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        return new SoaResponse();
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端取消预约单", classify = StoreAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse cancel(@ValideGroup(groups = StoreAppointOrderFacade.class) SoaRequest<AppointCancel> soaRequest) {
        AppointCancel cancel = soaRequest.getData();
        //对象装换
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setVenderId(cancel.getVenderId());
        appointOrderPO.setId(cancel.getAppointOrderId());
        appointOrderPO.setStoreCode(cancel.getStoreCode());
        appointOrderPO.setOperateUser(cancel.getUserPin());
        appointOrderPO.setDateForm(DateForm.STORE);
        //转换数据
        appointOrderService.cancel(appointOrderPO);
        return new SoaResponse();
    }


    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "门店端完成预约单", classify = StoreAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse finished(@ValideGroup SoaRequest<AppointFinishVO> soaRequest) {
        AppointFinishVO finishVO = soaRequest.getData();
        //对象装换
        appointOrderService.finishAppoint(new FinishAppointDto(finishVO),PlatformEnum.STORE.getIntValue());
        return new SoaResponse();
    }


}
