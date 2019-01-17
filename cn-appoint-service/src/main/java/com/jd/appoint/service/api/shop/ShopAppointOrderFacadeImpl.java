package com.jd.appoint.service.api.shop;

import com.google.common.base.Converter;
import com.google.common.collect.Sets;
import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.DateForm;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.OrderStatisticPO;
import com.jd.appoint.service.api.convert.ReschduleConvert;
import com.jd.appoint.service.api.shop.convert.AppointUpdateConvert;
import com.jd.appoint.service.api.shop.convert.DynamicTableConverter;
import com.jd.appoint.service.config.OrderListConfigService;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.DynamicOrderService;
import com.jd.appoint.service.order.OrderDetailConfigService;
import com.jd.appoint.service.order.PopConfigService;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.service.order.exceptions.InputLnsException;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.shopapi.vo.LsnInputVO;
import com.jd.appoint.shopapi.vo.OrderStatisticQuery;
import com.jd.appoint.shopapi.vo.ShopAppointOrderQueryVO;
import com.jd.appoint.shopapi.vo.ShopAppointUpdateVO;
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
import com.jd.xn.slog.LogSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.jd.travel.base.soa.SoaError.DEAL_EXCEPTION;


/**
 * Created by gaoxiaoqing on 2018/5/11.
 */
@Service(value = "shopAppointOrderFacade")
public class ShopAppointOrderFacadeImpl implements ShopAppointOrderFacade {
    private static Logger logger = LoggerFactory.getLogger(ShopAppointOrderFacadeImpl.class);
    @Resource
    private AppointOrderService appointOrderService;
    @Autowired
    private EsOrderService esOrderService;
    @Autowired
    private OrderListConfigService orderListConfigService;
    @Autowired
    private OrderDetailConfigService orderDetailConfigService;
    @Autowired
    private DynamicOrderService dynamicOrderService;
    @Autowired
    private PopConfigService popConfigService;

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "Shop端修改预约单信息", classify = ShopAppointOrderFacadeImpl.class))
    public SoaResponse editAppointOrder(@ValideGroup SoaRequest<ShopAppointUpdateVO> soaRequest) {
        if (null == soaRequest || null == soaRequest.getData()) {
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "预约单信息参数异常");
        }
        try {
            appointOrderService.editAppointOrder(AppointUpdateConvert.convertShopAppointUpdate(soaRequest.getData()));
        } catch (IllegalArgumentException e) {
            logger.error("Shop端预约单修改参数异常异常");
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
        return new SoaResponse();
    }

    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "SHOP预约取消接口", classify = ShopAppointOrderFacadeImpl.class))
    public SoaResponse cancel(@ValideGroup(groups = ShopAppointOrderFacade.class) SoaRequest<AppointCancel> soaRequest) {
        AppointCancel cancel = soaRequest.getData();
        //对象装换
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setVenderId(cancel.getVenderId());
        appointOrderPO.setId(cancel.getAppointOrderId());
        appointOrderPO.setOperateUser(cancel.getUserPin());
        appointOrderPO.setDateForm(DateForm.SHOP);
        //转换数据
        appointOrderService.cancel(appointOrderPO);
        return new SoaResponse();
    }

    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "SHOP预约完成接口", classify = ShopAppointOrderFacadeImpl.class))
    public SoaResponse finished(@ValideGroup SoaRequest<AppointFinishVO> soaRequest) {
        AppointFinishVO finishVO = soaRequest.getData();
        //对象装换
        appointOrderService.finishAppoint(new FinishAppointDto(finishVO), PlatformEnum.SHOP.getIntValue());
        return new SoaResponse();
    }

    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "预约单详情接口", classify = ShopAppointOrderFacadeImpl.class))
    public SoaResponse<AppointOrderDetailVO> getAppointOrderDetail(@ValideGroup SoaRequest<ShopAppointOrderQueryVO> soaRequest) {
        SoaResponse<AppointOrderDetailVO> soaResponse = new SoaResponse<>();
        ShopAppointOrderQueryVO shopAppointOrderQueryVO = soaRequest.getData();
        try {

            //获取预约单详情
            AppointOrderDetailVO appointOrderDetailVO = appointOrderService.detail(shopAppointOrderQueryVO.getAppointOrderId());
            //没有查到order详情返回null
            if (appointOrderDetailVO == null) {
                return soaResponse;
            }

            //验证入参的合法性并赋值中文状态
            validate(shopAppointOrderQueryVO, appointOrderDetailVO);
            soaResponse.setResult(appointOrderDetailVO);
            return soaResponse;
        } catch (IllegalArgumentException e) {
            logger.error("获取预约单详情入参不合法，soaRequest={}, 异常：{}", LogSecurity.toJSONString(soaRequest), e.getMessage());
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
    }


    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "动态预约单详情接口", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<DynamicOrderDetailVO> dynamicGetAppointOrderDetail(@ValideGroup SoaRequest<DynamicShopAppointOrderQuery> soaRequest) {
        SoaResponse<List<OrderDetailGroupVO>> soaResponse = new SoaResponse<>();
        DynamicShopAppointOrderQuery shopAppointOrderQuery = soaRequest.getData();
        return appointOrderService.dynamicDetail(shopAppointOrderQuery.getAppointOrderId(), shopAppointOrderQuery.getBusinessCode(), 1, shopAppointOrderQuery.getServerType(), shopAppointOrderQuery.getVenderId(), null);

    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "预约单列表", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<Page<AppointOrderDetailVO>> list(@ValideGroup SoaRequest<ShopAppointOrderListRequest> pageSoaRequest) {
        Page<AppointOrderDetailVO> page = esOrderService.list(pageSoaRequest.getData().toPage(), pageSoaRequest.getData().getBusinessCode());
        SoaResponse<Page<AppointOrderDetailVO>> result = new SoaResponse<>(page);
        if (page != null && page.getList() != null) {
            page.getList().forEach(detail -> {
                String chName = AppointStatusEnum.getFromCode(detail.getAppointStatus()).getAlias();
                detail.setChAppointStatus(chName);
            });
        }
        return result;
    }


    private void validate(ShopAppointOrderQueryVO shopAppointOrderQueryVO, AppointOrderDetailVO appointOrderDetailVO) {

        if (!shopAppointOrderQueryVO.getBusinessCode().equals(appointOrderDetailVO.getBusinessCode())) {
            logger.error("参数{}不合法，入参为：{}，数据库中为：{}", "businessCode", shopAppointOrderQueryVO.getBusinessCode(), appointOrderDetailVO.getBusinessCode());
            throw new IllegalArgumentException("入参业务类型不匹配");
        }
        //校验venderId
        if (!appointOrderDetailVO.getVenderId().equals(shopAppointOrderQueryVO.getVenderId())) {
            logger.error("参数{}不合法，入参为：{}，数据库中为：{}", "venderId", shopAppointOrderQueryVO.getVenderId(), appointOrderDetailVO.getVenderId());
            throw new IllegalArgumentException("入参venderId不匹配");
        }
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "某日订单数据统计", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<OrderStatisticVO> statisticByDate(@ValideGroup SoaRequest<OrderStatisticQuery> query) {
        //Preconditions.checkNotNull(query.getData().getVenderId());
        if (query.getData().getDate() == null) {
            query.getData().setDate(LocalDate.now());
        }
        LocalDate nextDate = query.getData().getDate().plusDays(1l);
        return new SoaResponse<>(new OrderStatisticConvert()
                .convert(appointOrderService.statisticByDate(query.getData().getDate(),
                        nextDate,
                        query.getData().getVenderId())));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "动态预约单列表", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<DynamicTable> dynamicList(@ValideGroup SoaRequest<ShopAppointOrderListRequest> pageSoaRequest) {
        ShopAppointOrderListRequest data = pageSoaRequest.getData();
        List<OrderListConfigPO> orderListConfigPOS = orderListConfigService.getListItems(data.getBusinessCode(), data.getServerType(), PlatformEnum.SHOP.getIntValue());
        Page<DynamicAppointOrder> dynamicAppointOrderPage = dynamicOrderService.dynamicList(data.toPage(), data.getBusinessCode(), orderListConfigPOS);
        DynamicTable dynamicTable = DynamicTableConverter.convert(dynamicAppointOrderPage, orderListConfigPOS);
        return new SoaResponse<>(dynamicTable);
    }

    /**
     * 动态预约单修改
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "Shop端预约单修改", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse dynamicUpdateAppoint(@ValideGroup(groups = {ShopAppointOrderFacade.class}) SoaRequest<UpdateAppointVO> soaRequest) {
        try {
            appointOrderService.dynamicUpdateAppoint(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            logger.error("Shop端预约单修改参数异常异常");
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
        return new SoaResponse();
    }

    /**
     * 更新附属信息
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "更新附属信息", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse updateAttachInfo(@ValideGroup SoaRequest<UpdateAttachVO> soaRequest) {
        try {
            appointOrderService.updateAttachInfo(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            logger.error("更新附属信息异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        return new SoaResponse();
    }


    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "审核预约单", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse checkAppointOrder(@ValideGroup(groups = ShopAppointOrderFacade.class) SoaRequest<CheckOrderVO> soaRequest) {
        List<Long> appointOrders = null;
        try {
            appointOrders = appointOrderService.checkAppointOrder(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            logger.error("审核预约单参数校验异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "审核预约单参数校验异常");
        }
        //单个审核不通过情况
        if (1 == soaRequest.getData().getAppointOrderIds().size() && !CollectionUtils.isEmpty(appointOrders)) {
            return new SoaResponse(SoaError.SERVER_EXCEPTION);
        }
        return new SoaResponse(appointOrders);
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "改期", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<AppointOrderResult> reschdule(@ValideGroup(groups = ShopAppointOrderFacade.class) SoaRequest<ReschduleVO> soaRequest) {
        AppointOrderResult appointOrderResult = null;
        try {
            appointOrderResult = appointOrderService.reschdule(ReschduleConvert.shopToReschduleDTO(soaRequest.getData()));
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

    /**
     * Shop端预约单列表导出
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "Shop端预约单列表导出", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<List<LinkedHashMap<String, String>>> exportAppointOrders(@ValideGroup SoaRequest<ShopAppointOrderListRequest> soaRequest) {
        List<LinkedHashMap<String, String>> appointOrderList = null;
        try {
            AppointOrderListQuery appointOrderListQuery = new AppointOrderListQuery();
            BeanUtils.copyProperties(soaRequest.getData(), appointOrderListQuery);
            appointOrderList = dynamicOrderService.exportDynamicList(appointOrderListQuery, PlatformEnum.SHOP.getIntValue());
        } catch (IllegalArgumentException e) {
            logger.error("Shop端预约单列表导出异常", e);
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        return new SoaResponse<>(appointOrderList);
    }


    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "shop端派单操作", classify = ShopAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse dispatcher(@ValideGroup(groups = ShopAppointOrderFacade.class) SoaRequest<DispatchOrderVO> soaRequest) {
        try {
            appointOrderService.dispatchOrder(soaRequest.getData());
        } catch (IllegalArgumentException e) {
            logger.error("shop端派单操作参数异常异常");
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        return new SoaResponse();
    }

    private static class OrderStatisticConvert extends Converter<OrderStatisticPO, OrderStatisticVO> {

        @Override
        protected OrderStatisticVO doForward(OrderStatisticPO orderStatisticPO) {
            OrderStatisticVO orderStatisticVO = new OrderStatisticVO();
            BeanUtils.copyProperties(orderStatisticPO, orderStatisticVO);
            return orderStatisticVO;
        }

        @Override
        protected OrderStatisticPO doBackward(OrderStatisticVO orderStatisticVO) {
            throw new RuntimeException("not implement yet!");
        }
    }


    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "批量导入物流单号操作", classify = ShopAppointOrderFacadeImpl.class))
    public SoaResponse<Long> inputLsns(@ValideGroup SoaRequest<LsnInputVO> soaRequest) {
        try {
            appointOrderService.inputLsns(soaRequest.getData());
            return new SoaResponse();
        } catch (InputLnsException e) {
            logger.warn("批量导入物流单号操作e={}", e);
            if (null != e.getAppointOrderId()) {
                SoaResponse soaResponse = new SoaResponse(DEAL_EXCEPTION, "预约单号", String.valueOf(e.getAppointOrderId()), "导入失败，导入任务已停止，请检查后重试！");
                soaResponse.setResult(e.getAppointOrderId());
                return soaResponse;
            }
        }
        return new SoaResponse(SoaError.SERVER_EXCEPTION);
    }
    @Override
    public void pressure(long count) {
        long start = 110000;
        long end = start + count;
        Set<String> set = Sets.newHashSet();
        while (start + 1000 < end) {
            for (long i = start; i < start + 1000; i++) {
                set.add(i + "");
            }
            esOrderService.batchIndex(set);
            set.clear();
            start += 1000;
        }
    }
}
