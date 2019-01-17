package com.jd.appoint.service.api;

import com.google.common.collect.Maps;
import com.jd.appoint.api.AppointOrderFacade;
import com.jd.appoint.api.vo.ApiReschuleVO;
import com.jd.appoint.api.vo.OrderDetailWithOperateVO;
import com.jd.appoint.domain.config.OrderOperateConfigPO;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.DateForm;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.service.api.convert.PageConvert;
import com.jd.appoint.service.api.convert.ReschduleConvert;
import com.jd.appoint.service.api.shop.convert.OrderOperateConfigConvert;
import com.jd.appoint.service.config.OrderOperateConfigService;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.DynamicOrderService;
import com.jd.appoint.service.order.PopConfigService;
import com.jd.appoint.service.order.convert.AppointOrderConvert;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.service.product.ProductService;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.appoint.service.sys.ContextService;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.dynamic.OperateItemVo;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.xn.slog.LogSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/14 14:00
 */
@Service(value = "appointOrderFacade")
public class AppointOrderFacadeImpl implements AppointOrderFacade {

    private static Logger logger = LoggerFactory.getLogger(AppointOrderFacadeImpl.class);
    @Resource(name = "appointOrderService")
    private AppointOrderService appointOrderService;
    @Autowired
    private PopConfigService popConifgService;
    @Autowired
    private EsOrderService esOrderService;
    @Autowired
    private ShopFormControlItemService shopFormControlItemService;

    @Autowired
    private ProductService productService;
    @Resource
    private OrderOperateConfigService orderOperateConfigService;
    @Resource
    private DynamicOrderService dynamicOrderService;
    @Autowired
    private RpcContextService rpcContextService;
    @Autowired
    private ContextService contextService;

    @UmpMonitor(logCollector =
    @LogCollector(description = "提交预约单", classify = AppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<AppointOrderResult> submitAppoint(@ValideGroup SoaRequest<SubmitAppointVO> soaRequest) {
        try {
            AppointOrderResult appointOrderResult = appointOrderService.submitAppoint(AppointOrderDetailVO.convert(soaRequest.getData()));
            return new SoaResponse<>(appointOrderResult);
        } catch (IllegalArgumentException e) {
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
    }

    @UmpMonitor(logCollector =
    @LogCollector(description = "context提交预约单", classify = AppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<AppointOrderResult> contextSubmitAppoint(@ValideGroup SoaRequest<ContextSubmitAppointVO> soaRequest) {
        try {
            ContextSubmitAppointVO data = soaRequest.getData();
            Map<String, String> context = rpcContextService.getContext(data.getContextId());
            if (context == null) {
                context = Maps.newHashMap();
            }
            if (data.getMap() == null) {
                data.setMap(Maps.newHashMap());
            }
            context.putAll(data.getMap());
            Map<String, String> staticFieldMap = contextService.validateContext(context);
            AppointOrderDetailVO detailVO = contextService.getBean(staticFieldMap, AppointOrderDetailVO.class);
            Map<String, String> finalContext = context;
            staticFieldMap.keySet().stream().forEach(fieldName -> finalContext.remove(fieldName));
            detailVO.setFormItems(context);
            appointOrderService.fillDetailByCode(data.getContextId(), detailVO);
            AppointOrderResult appointOrderResult = appointOrderService.submitAppoint(detailVO);
            rpcContextService.dropContext(data.getContextId());
            return new SoaResponse<>(appointOrderResult);
        } catch (IllegalArgumentException e) {
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
    }

    @UmpMonitor(logCollector =
    @LogCollector(description = "预约单可预约检查", classify = AppointOrderFacadeImpl.class))
    @Override
    public SoaResponse checkAppoint(@ValideGroup SoaRequest<SubmitAppointVO> soaRequest) {
        try {
            SubmitAppointVO submitAppointVO = soaRequest.getData();
            //预约结束>=开始
            if (submitAppointVO.getAppointEndTime() != null
                    && submitAppointVO.getAppointEndTime().getTime() < submitAppointVO.getAppointStartTime().getTime()) {
                throw new IllegalArgumentException("预约结束时间需要>=开始");
            }
            AppointOrderDetailVO detailVO = AppointOrderDetailVO.convert(soaRequest.getData());
            shopFormControlItemService.createValueMap(detailVO);
            return new SoaResponse<>();
        } catch (IllegalArgumentException e) {
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
    }

    /**
     * 预约单详情
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "获取预约单详情", classify = AppointOrderFacadeImpl.class))
    public SoaResponse<AppointOrderDetailVO> getAppointOrderDetail(@ValideGroup SoaRequest<AppointOrderQueryVO> soaRequest) {
        SoaResponse<AppointOrderDetailVO> soaResponse = new SoaResponse<>();
        AppointOrderQueryVO appointOrderQueryVO = soaRequest.getData();
        try {

            //获取预约单详情
            AppointOrderDetailVO appointOrderDetailVO = appointOrderService.detail(appointOrderQueryVO.getAppointOrderId());
            //没有查到order详情返回null
            if (appointOrderDetailVO == null) {
                return soaResponse;
            }

            //验证入参的合法性并赋值中文状态
            validate(appointOrderQueryVO, appointOrderDetailVO);

            soaResponse.setResult(appointOrderDetailVO);
            return soaResponse;
        } catch (IllegalArgumentException e) {
            logger.error("获取预约单详情入参不合法，soaRequest={}, 异常：{}", LogSecurity.toJSONString(soaRequest), e.getMessage());
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
    }

    private void validate(AppointOrderQueryVO appointOrderQueryVO, AppointOrderDetailVO appointOrderDetailVO) {
        //校验customerUserPin
        if (!appointOrderDetailVO.getCustomerUserPin().equals(appointOrderQueryVO.getCustomerUserPin())) {
            logger.error("参数{}不合法，入参为：{}，数据库中为：{}", "customerUserPin", appointOrderQueryVO.getCustomerUserPin(), appointOrderDetailVO.getCustomerUserPin());
            throw new IllegalArgumentException("入参customerUserPin不匹配");
        }
    }


    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "预约完成接口", classify = AppointOrderFacadeImpl.class))
    public SoaResponse finished(@ValideGroup SoaRequest<AppointFinishVO> soaRequest) {
        AppointFinishVO appointFinishVO = soaRequest.getData();
        appointOrderService.finishAppoint(new FinishAppointDto(appointFinishVO),PlatformEnum.STAFF.getIntValue());
        return new SoaResponse();
    }

    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "预约取消接口", classify = AppointOrderFacadeImpl.class))
    public SoaResponse cancel(@ValideGroup(groups = AppointOrderFacade.class) SoaRequest<AppointCancel> soaRequest) {
        AppointCancel cancel = soaRequest.getData();
        //对象装换
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setId(cancel.getAppointOrderId());
        appointOrderPO.setCustomerUserPin(cancel.getUserPin());
        appointOrderPO.setOperateUser(cancel.getUserPin());
        appointOrderPO.setDateForm(DateForm.API);
        //转换数据
        appointOrderService.cancel(appointOrderPO);
        return new SoaResponse();
    }


    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "预约单列表", classify = AppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<Page<OrderDetailWithOperateVO>> list(@ValideGroup SoaRequest<AppointOrderListRequest> pageSoaRequest) {
        AppointOrderListRequest request = pageSoaRequest.getData();
        Page<AppointOrderDetailVO> page = esOrderService.list(request.toPage(), request.getBusinessCode());
        List<SystemStatusInfo> systemStatusInfos = popConifgService.queryStatusMappingList(request.getBusinessCode());
        List<OrderOperateConfigPO> orderOperateConfigPOS = orderOperateConfigService.normalOperateList(request.getBusinessCode(), null, PlatformEnum.TO_C.getIntValue());
        Map<Integer, List<OperateItemVo>> collect = orderOperateConfigPOS.stream()
                .collect(
                        Collectors.groupingBy(config -> config.getAppointStatus().getIntValue(),
                                Collectors.mapping(config -> OrderOperateConfigConvert.convertToOperateItemVo(config),
                                        Collectors.toList()))
                );
        AppointOrderConvert.mapChStatus(page.getList(), systemStatusInfos);
        Page<OrderDetailWithOperateVO> result = PageConvert.copyPage(page);
        if (page.getList() != null) {
            List<OrderDetailWithOperateVO> list = page.getList().stream().map(detailVO -> bindOperate(detailVO, collect)).collect(Collectors.toList());
            result.setList(list);
        }
        return new SoaResponse<>(result);
    }

    private OrderDetailWithOperateVO bindOperate(AppointOrderDetailVO detailVO, Map<Integer, List<OperateItemVo>> collect) {
        OrderDetailWithOperateVO detailWithOperateVO = new OrderDetailWithOperateVO();
        detailWithOperateVO.setAppointOrderDetailVO(detailVO);
        List<OperateItemVo> operateItemVos = collect.get(detailVO.getAppointStatus());
        if (operateItemVos != null) {
            List<OperateItemVo> list = operateItemVos.stream()
                    //筛选通用或者等于此服务模式的操作
                    .filter(item -> item.getServerType() == null || item.getServerType() == item.getServerType())
                    .collect(Collectors.toList());
            detailWithOperateVO.setOperateItemList(list);
        }
        return detailWithOperateVO;
    }


    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "Api端改期", classify = AppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<AppointOrderResult> reschdule(@ValideGroup SoaRequest<ApiReschuleVO> soaRequest) {

        AppointOrderResult appointOrderResult = null;
        try {
            appointOrderResult = appointOrderService.reschdule(ReschduleConvert.apiToReschduleDTO(soaRequest.getData()));
        } catch (IllegalArgumentException e) {
            logger.error("Api端改期参数异常", e);
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
    @LogCollector(description = "动态C端预约单列表", classify = AppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<Page<ApiAppointOrderDetailVO>> dynamicAppointList(@ValideGroup SoaRequest<AppointOrderListRequest> soaRequest) {
        Page<ApiAppointOrderDetailVO> apiAppointOrderDetailVOPage = null;
        try {
            apiAppointOrderDetailVOPage = dynamicOrderService.cDynamicAppointList(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
        return new SoaResponse<>(apiAppointOrderDetailVOPage);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "C端预约单列表", classify = AppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<Page<OrderDetailWithOperateVO>> appointList(SoaRequest<AppointOrderListRequest> soaRequest) {
        Page<OrderDetailWithOperateVO> orderDetailWithOperateVOPage = null;
        try {
            orderDetailWithOperateVOPage = dynamicOrderService.appointList(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            logger.error("C端预约单列表异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        return new SoaResponse<>(orderDetailWithOperateVOPage);
    }

}
