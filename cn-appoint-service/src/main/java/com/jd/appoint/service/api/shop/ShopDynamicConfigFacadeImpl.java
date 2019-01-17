package com.jd.appoint.service.api.shop;

import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.domain.config.OrderOperateConfigPO;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.service.config.OrderListConfigService;
import com.jd.appoint.service.config.OrderOperateConfigService;
import com.jd.appoint.service.api.shop.convert.OrderListConfigConverter;
import com.jd.appoint.service.api.shop.convert.OrderOperateConfigConvert;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.shopapi.ShopDynamicConfigFacade;
import com.jd.appoint.vo.dynamic.*;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/6/25.
 */
@Service("shopDynamicConfigFacade")
public class ShopDynamicConfigFacadeImpl implements ShopDynamicConfigFacade {
    @Autowired
    private OrderListConfigService orderListConfigService;
    @Autowired
    private OrderOperateConfigService orderOperateConfigService;
    @Autowired
    private AppointOrderService appointOrderService;

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "shop获取服务类型列表", classify = ShopDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<ServerTypeVO>> serverTypeList(SoaRequest<String> businessCode) {
        String data = businessCode.getData();
        if (data == null) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "businessCode must not null");
        }
        List<ServerTypeVO> result = orderListConfigService.getServerTypes(data, PlatformEnum.SHOP.getIntValue());
        return new SoaResponse<>(result);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "获取筛选项", classify = ShopDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<FilterItemVO>> filterItemList(@ValideGroup SoaRequest<ServerTypeRequest> serverTypeRequest) {
        ServerTypeRequest data = serverTypeRequest.getData();
        List<OrderListConfigPO> listItems = orderListConfigService.getFilterItems(data.getBusinessCode(), data.getServerType(), PlatformEnum.SHOP.getIntValue());
        List<FilterItemVO> result = listItems.stream()
                .map(config -> OrderListConfigConverter.convertToFilterItemVO(config))
                .collect(Collectors.toList());
        return new SoaResponse<>(result);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "获取批量操作", classify = ShopDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<OperateItemVo>> batchOperateList(@ValideGroup SoaRequest<ServerTypeRequest> serverTypeRequest) {
        ServerTypeRequest data = serverTypeRequest.getData();
        List<OrderOperateConfigPO> orderOperateConfigPOS = orderOperateConfigService.batchOperateList(data.getBusinessCode(), data.getServerType(), PlatformEnum.SHOP.getIntValue());
        List<OperateItemVo> result = orderOperateConfigPOS.stream().map(config -> OrderOperateConfigConvert.convertToOperateItemVo(config)).collect(Collectors.toList());
        return new SoaResponse<>(result);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "获取详情操作", classify = ShopDynamicConfigFacadeImpl.class))
    @Override
    public SoaResponse<List<OperateItemVo>> detailOperateList(SoaRequest<Long> serverTypeRequest) {
        Long appointOrderId = serverTypeRequest.getData();
        if (appointOrderId == null) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "appointOrderId must not null");
        }
        AppointOrderPO orderPO = appointOrderService.findOne(appointOrderId);
        List<OrderOperateConfigPO> orderOperateConfigPOS = orderOperateConfigService.normalOperateList(orderPO.getBusinessCode(), orderPO.getServerType(), PlatformEnum.SHOP.getIntValue());
        List<OperateItemVo> collect = orderOperateConfigPOS.stream()
                .filter(config -> config.getAppointStatus() == orderPO.getAppointStatus())
                .map(config -> OrderOperateConfigConvert.convertToOperateItemVo(config))
                .collect(Collectors.toList());
        return new SoaResponse<>(collect);
    }
}
