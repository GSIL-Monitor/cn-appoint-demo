package com.jd.appoint.service.api.store;

import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.domain.config.OrderOperateConfigPO;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.service.config.OrderListConfigService;
import com.jd.appoint.service.config.OrderOperateConfigService;
import com.jd.appoint.service.api.shop.convert.OrderListConfigConverter;
import com.jd.appoint.service.api.shop.convert.OrderOperateConfigConvert;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.storeapi.StoreDynamicConfigFacade;
import com.jd.appoint.vo.dynamic.FilterItemVO;
import com.jd.appoint.vo.dynamic.OperateItemVo;
import com.jd.appoint.vo.dynamic.ServerTypeRequest;
import com.jd.appoint.vo.dynamic.ServerTypeVO;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/7/3.
 */
@Service("storeDynamicConfigFacade")
public class StoreDynamicConfigFacadeImpl implements StoreDynamicConfigFacade {
    @Autowired
    private OrderListConfigService orderListConfigService;
    @Autowired
    private OrderOperateConfigService orderOperateConfigService;
    @Autowired
    private AppointOrderService appointOrderService;

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "shop获取服务类型列表", classify = StoreDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<ServerTypeVO>> serverTypeList(SoaRequest<String> businessCode) {
        String data = businessCode.getData();
        if (data == null) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "businessCode must not null");
        }
        List<ServerTypeVO> result = orderListConfigService.getServerTypes(data, PlatformEnum.STORE.getIntValue());
        return new SoaResponse<>(result);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "stroe获取筛选项", classify = StoreDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<FilterItemVO>> filterItemList(SoaRequest<ServerTypeRequest> serverTypeRequest) {
        ServerTypeRequest data = serverTypeRequest.getData();
        List<OrderListConfigPO> listItems = orderListConfigService.getFilterItems(data.getBusinessCode(), data.getServerType(), PlatformEnum.STORE.getIntValue());
        List<FilterItemVO> result = listItems.stream()
                .map(config -> OrderListConfigConverter.convertToFilterItemVO(config))
                .collect(Collectors.toList());
        return new SoaResponse<>(result);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "store获取批量操作", classify = StoreDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<OperateItemVo>> batchOperateList(SoaRequest<ServerTypeRequest> serverTypeRequest) {
        ServerTypeRequest data = serverTypeRequest.getData();
        List<OrderOperateConfigPO> orderOperateConfigPOS = orderOperateConfigService.batchOperateList(data.getBusinessCode(), data.getServerType(), PlatformEnum.STORE.getIntValue());
        List<OperateItemVo> result = orderOperateConfigPOS.stream().map(config -> OrderOperateConfigConvert.convertToOperateItemVo(config)).collect(Collectors.toList());
        return new SoaResponse<>(result);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "store获取详情操作", classify = StoreDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<OperateItemVo>> detailOperateList(SoaRequest<Long> serverTypeRequest) {
        Long appointOrderId = serverTypeRequest.getData();
        if (appointOrderId == null) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "appointOrderId must not null");
        }
        AppointOrderPO orderPO = appointOrderService.findOne(appointOrderId);
        List<OrderOperateConfigPO> orderOperateConfigPOS = orderOperateConfigService.normalOperateList(orderPO.getBusinessCode(), orderPO.getServerType(), PlatformEnum.STORE.getIntValue());
        List<OperateItemVo> collect = orderOperateConfigPOS.stream()
                .filter(config -> config.getAppointStatus() == orderPO.getAppointStatus())
                .map(config -> OrderOperateConfigConvert.convertToOperateItemVo(config))
                .collect(Collectors.toList());
        return new SoaResponse<>(collect);
    }
}
