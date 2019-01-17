package com.jd.appoint.service.order.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.appoint.api.vo.OrderDetailWithOperateVO;
import com.jd.appoint.domain.config.BusinessVenderMapPO;
import com.jd.appoint.domain.config.OrderDetailConfigPO;
import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.domain.dto.DynamicOrderListDTO;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.order.AppointOrderFormItemPO;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.appoint.service.config.BusinessVenderMapService;
import com.jd.appoint.service.config.OrderListConfigService;
import com.jd.appoint.service.order.*;
import com.jd.appoint.service.order.convert.AppointOrderConvert;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.dynamic.DynamicAppointOrder;
import com.jd.appoint.vo.dynamic.OperateItemVo;
import com.jd.appoint.vo.order.ApiAppointOrderDetailVO;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.dynamic.FieldVo;
import com.jd.appoint.vo.page.Page;
import com.jd.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/6/21.
 */
@Service
public class DynamicOrderServiceImpl implements DynamicOrderService {
    private Logger logger = LoggerFactory.getLogger(DynamicOrderServiceImpl.class);
    private static final String EMPTY_STRING = "";
    @Autowired
    private EsOrderService esOrderService;
    @Resource
    private PopConfigService popConifgService;
    @Resource
    private ShopFormControlItemService shopFormControlItemService;
    @Resource
    private AppointOrderFormItemService appointOrderFormItemService;
    @Resource
    private AppointOrderServiceItemService appointOrderServiceItemService;
    @Resource
    private OrderListConfigService orderListConfigService;
    @Resource
    private AppointOrderService appointOrderService;
    @Resource
    private OrderDetailConfigService orderDetailConfigService;
    @Resource
    private AppointOperateService appointOperateService;
    @Resource
    private BusinessVenderMapService businessVenderMapService;
    @Autowired
    private VenderConfigService venderConfigService;
    private static final String TIME = "appointStartTime,appointEndTime";

    /**
     * 预约单所有get方法缓存，如<id,getId()>
     */
    private final Map<String, Method> APPOINT_ORDER_READ_METHOD_MAP = Maps.newHashMap();

    @PostConstruct
    public void init() {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(AppointOrderDetailVO.class);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getReadMethod() != null) {
                APPOINT_ORDER_READ_METHOD_MAP.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod());
            }
        }
    }

    @Override
    public Page<DynamicAppointOrder> dynamicList(Page page, String businessCode, List<OrderListConfigPO> listItems) {
        Page<AppointOrderDetailVO> detailPage = esOrderService.list(page, businessCode);
        Page<DynamicAppointOrder> result = initPage(detailPage);
        List<DynamicAppointOrder> list = Lists.newArrayList();
        result.setList(list);
        //每个document就是一个预约单
        if (detailPage.getList() != null) {
            TimeShowTypeEnum timeShowTypeEnum = venderConfigService.getTimeShowType(businessCode);
            for (AppointOrderDetailVO orderDetailVO : detailPage.getList()) {
                long id = orderDetailVO.getId();
                DynamicAppointOrder dynamicAppointOrder = new DynamicAppointOrder();
                dynamicAppointOrder.setId(id);
                List<FieldVo> fieldVos = Lists.newArrayList();
                dynamicAppointOrder.setFieldVos(fieldVos);
                //这是前端列表项需要展示的内容
                for (OrderListConfigPO configPO : listItems) {
                    FieldVo fieldVo = new FieldVo();
                    fieldVo.setAlias(configPO.getAlias());
                    fieldVo.setLabel(configPO.getLabel());
                    //反射执行，获取内容，值不存在返回""
                    String value = invoke(configPO.getAlias(), orderDetailVO, timeShowTypeEnum);
                    fieldVo.setValue(value);
                    fieldVos.add(fieldVo);
                }
                list.add(dynamicAppointOrder);
            }
        }
        return result;
    }

    private Page<DynamicAppointOrder> initPage(Page<AppointOrderDetailVO> detailPage) {
        Page<DynamicAppointOrder> result = new Page<>();
        result.setTotalCount(detailPage.getTotalCount());
        result.setPageSize(detailPage.getPageSize());
        result.setPageNumber(detailPage.getPageNumber());
        result.setSorts(detailPage.getSorts());
        return result;
    }

    @Override
    public Page<ApiAppointOrderDetailVO> cDynamicAppointList(AppointOrderListRequest appointOrderListRequest) {
        //获取列表配置项
        List<OrderListConfigPO> orderListConfigPOList =
                orderListConfigService.getListItems(appointOrderListRequest.getBusinessCode(), null, PlatformEnum.TO_C.getIntValue());
        if (CollectionUtils.isEmpty(orderListConfigPOList)) {
            throw new IllegalArgumentException("C端预约单列表未配置");
        }
        //获取预约单详细信息
        PageInfo<AppointOrderPO> appointOrderPOPage = PageHelper.startPage(appointOrderListRequest.getPageNumber(), appointOrderListRequest.getPageSize(), true).
                doSelectPageInfo(() -> {
                    appointOrderService.getAppointOrderByUserPin(appointOrderListRequest.getCustomerUserPin(), appointOrderListRequest.getBusinessCode());
                });
        if (CollectionUtils.isEmpty(appointOrderPOPage.getList())) {
            return null;
        }
        //获取预约单详细信息
        List<AppointOrderDetailVO> appointOrderDetailVOList = appointOrderService.appointDetailList(appointOrderPOPage.getList());
        //获取操作项
        Map<Integer, List<OperateItemVo>> operateMap = appointOperateService.operateList(appointOrderListRequest.getBusinessCode(), PlatformEnum.TO_C.getIntValue());
        if (null == operateMap) {
            throw new IllegalArgumentException("未配置列表操作项");
        }
        //组装信息
        List<ApiAppointOrderDetailVO> apiAppointOrderDetailVOList =
                combineDynamicAppoint(appointOrderDetailVOList, orderListConfigPOList, operateMap);
        return convertDynamicListPage(appointOrderPOPage, apiAppointOrderDetailVOList);
    }

    @Override
    public Page<OrderDetailWithOperateVO> appointList(AppointOrderListRequest appointOrderListRequest) {
        List<OrderDetailWithOperateVO> orderDetailWithOperateVOList = null;
        PageInfo<AppointOrderPO> appointOrderPOPage = PageHelper.startPage(appointOrderListRequest.getPageNumber(), appointOrderListRequest.getPageSize(), true).
                doSelectPageInfo(() -> {
                    appointOrderService.getAppointOrderByUserPin(appointOrderListRequest.getCustomerUserPin(), appointOrderListRequest.getBusinessCode());
                });
        if (CollectionUtils.isEmpty(appointOrderPOPage.getList()) ||
                appointOrderListRequest.getPageNumber().intValue() > appointOrderPOPage.getNavigateLastPage() ) {
            appointOrderPOPage.setPageNum(appointOrderListRequest.getPageNumber());
            appointOrderPOPage.setPages(appointOrderListRequest.getPageSize());
            return convertAppointPage(appointOrderPOPage,null);
        }
        //获取预约单详细信息
        List<AppointOrderDetailVO> appointOrderDetailVOList = appointOrderService.appointDetailList(appointOrderPOPage.getList());
        //状态映射
        List<SystemStatusInfo> systemStatusInfos = popConifgService.queryStatusMappingList(appointOrderListRequest.getBusinessCode());
        AppointOrderConvert.mapChStatus(appointOrderDetailVOList, systemStatusInfos);

        //获取操作项
        Map<Integer, List<OperateItemVo>> operateMap = appointOperateService.operateList(appointOrderListRequest.getBusinessCode(), PlatformEnum.TO_C.getIntValue());
        if (null == operateMap) {
            orderDetailWithOperateVOList = appointOrderDetailVOList.stream().map(appointOrderDetailVO -> {
                OrderDetailWithOperateVO orderDetailWithOperateVO = new OrderDetailWithOperateVO();
                orderDetailWithOperateVO.setAppointOrderDetailVO(appointOrderDetailVO);
                return orderDetailWithOperateVO;
            }).collect(Collectors.toList());
            return convertAppointPage(appointOrderPOPage,orderDetailWithOperateVOList);
        }
        //组装信息(预约单详情+操作项)
        orderDetailWithOperateVOList = combineAppoint(appointOrderDetailVOList,operateMap);
        return convertAppointPage(appointOrderPOPage,orderDetailWithOperateVOList);
    }

    /**
     * 组装C端列表展示项
     * @param appointOrderDetailVOList
     * @param operateMap
     * @return
     */
    private List<OrderDetailWithOperateVO> combineAppoint(List<AppointOrderDetailVO> appointOrderDetailVOList,Map<Integer,List<OperateItemVo>> operateMap){
        List<OrderDetailWithOperateVO> orderDetailWithOperateVOList = appointOrderDetailVOList.stream().map(appointOrderDetailVO -> {
            OrderDetailWithOperateVO orderDetailWithOperateVO = new OrderDetailWithOperateVO();
            orderDetailWithOperateVO.setAppointOrderDetailVO(appointOrderDetailVO);
            //过滤相应服务模型
            if(CollectionUtils.isNotEmpty(operateMap.get(appointOrderDetailVO.getAppointStatus()))){
                List<OperateItemVo> operateItemVoList = operateMap.get(appointOrderDetailVO.getAppointStatus()).stream()
                        .filter(operateItemVo ->null == operateItemVo.getServerType() || appointOrderDetailVO.getServerType() == operateItemVo.getServerType())
                        .collect(Collectors.toList());
                orderDetailWithOperateVO.setOperateItemList(operateItemVoList);
            }
            return orderDetailWithOperateVO;
        }).collect(Collectors.toList());
        return orderDetailWithOperateVOList;
    }

    @Override
    public String invoke(String alias, AppointOrderDetailVO orderDetailVO, TimeShowTypeEnum timeShowTypeEnum) {
        try {
            Object result;
            //如果是基本属性，就直接get返回
            if (APPOINT_ORDER_READ_METHOD_MAP.containsKey(alias)) {
                result = APPOINT_ORDER_READ_METHOD_MAP.get(alias).invoke(orderDetailVO);
            }
            //也可能是formItems里面的属性
            else {
                //formItems 肯定不为NULL
                result = orderDetailVO.getFormItems().get(alias);
            }
            if (result == null) {
                //空统一返回双引号
                return EMPTY_STRING;
            }
            if (result instanceof Date) {
                SimpleDateFormat simpleDateFormat;
                if(TIME.indexOf(alias) > -1 && TimeShowTypeEnum.DAY == timeShowTypeEnum){
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                }else if(TIME.indexOf(alias) > -1) {
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                }else {
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
                return simpleDateFormat.format((Date) result);
            }
            return String.valueOf(result);
        } catch (Exception e) {
            logger.error("反射执行出错,alias:" + alias, e);
        }
        return EMPTY_STRING;
    }

    @Override
    public List<LinkedHashMap<String, String>> exportDynamicList(AppointOrderListQuery appointOrderListQuery, Integer platform) {
        //获取预约单信息
        Page<AppointOrderDetailVO> detailPage = esOrderService.list(appointOrderListQuery.toPage(), appointOrderListQuery.getBusinessCode());
        //将detail中的oriCardNo赋值给cardNo，因为导出不需要cardNo加密
        showCardNo(detailPage);
        logger.info("调用esOrderService.list得到结果：{}"+ JSON.toJSONString(detailPage));
        if (CollectionUtils.isEmpty(detailPage.getList())) {
            return null;
        }
        //获取预约单详情配置字段
        List<OrderDetailConfigPO> orderDetailConfigPOList =
                orderDetailConfigService.getDetailItems(appointOrderListQuery.getBusinessCode(), platform, appointOrderListQuery.getServerType());
        //详情配置
        List<LinkedHashMap<String, String>> orderDetailList = new ArrayList<>();
        TimeShowTypeEnum timeShowTypeEnum = venderConfigService.getTimeShowType(appointOrderListQuery.getBusinessCode());
        if (CollectionUtils.isNotEmpty(orderDetailConfigPOList)) {
            for (AppointOrderDetailVO appointOrderDetailVO : detailPage.getList()) {
                LinkedHashMap<String, String> orderDetailMap = new LinkedHashMap<>();
                for (OrderDetailConfigPO orderDetailConfigPO : orderDetailConfigPOList) {
                    String value = invoke(orderDetailConfigPO.getAlias(), appointOrderDetailVO, timeShowTypeEnum);
                    orderDetailMap.put(orderDetailConfigPO.getLabel(), value);
                }
                orderDetailList.add(orderDetailMap);
            }
            return orderDetailList;
        }
        //获取预约单列表配置字段
        List<OrderListConfigPO> orderListConfigPOList =
                orderListConfigService.getListItems(appointOrderListQuery.getBusinessCode(),appointOrderListQuery.getServerType(),platform);
        if(CollectionUtils.isNotEmpty(orderListConfigPOList)){
            for (AppointOrderDetailVO appointOrderDetailVO : detailPage.getList()) {
                LinkedHashMap<String, String> orderDetailMap = new LinkedHashMap<>();
                for (OrderListConfigPO orderListConfigPO : orderListConfigPOList) {
                    String value = invoke(orderListConfigPO.getAlias(), appointOrderDetailVO, timeShowTypeEnum);
                    orderDetailMap.put(orderListConfigPO.getLabel(), value);
                }
                orderDetailList.add(orderDetailMap);
            }
            return orderDetailList;
        }
        //读取配置
        List<OrderFieldVO> orderFieldVOS = popConifgService.getExportOrderSet(appointOrderListQuery.getBusinessCode());
        if(CollectionUtils.isNotEmpty(orderFieldVOS)){
            for (AppointOrderDetailVO appointOrderDetailVO : detailPage.getList()) {
                LinkedHashMap<String, String> orderDetailMap = new LinkedHashMap<>();
                for (OrderFieldVO orderFieldVO : orderFieldVOS) {
                    String value = invoke(orderFieldVO.getAlias(), appointOrderDetailVO, timeShowTypeEnum);
                    orderDetailMap.put(orderFieldVO.getLabel(), value);
                }
                orderDetailList.add(orderDetailMap);
            }
        }
        return orderDetailList;
    }

    private void showCardNo(Page<AppointOrderDetailVO> detailPage) {
        detailPage.getList().forEach(appointOrderDetailVO -> {appointOrderDetailVO.setCardNo(appointOrderDetailVO.getOriCardNo());});
    }

    /**
     * 组合信息
     *
     * @return
     */
    public List<ApiAppointOrderDetailVO> combineDynamicAppoint(List<AppointOrderDetailVO> appointOrderDetailVOList,
                                                          List<OrderListConfigPO> orderListConfigPOList, Map<Integer, List<OperateItemVo>> operateMap) {
        //获取商家名称
        Map<Long, String> venderNameMap = getVenderName(appointOrderDetailVOList);
        List<ApiAppointOrderDetailVO> apiAppointOrderDetailVOList = appointOrderDetailVOList.stream().map(appointOrderDetailVO -> {
            Map<String, String> formMaps = appointOrderDetailVO.getFormItems();
            List<FieldVo> fieldVoList = new ArrayList<>();
            orderListConfigPOList.forEach(orderListConfigPO -> {
                FieldVo fieldVo = new FieldVo();
                fieldVo.setAlias(orderListConfigPO.getAlias());
                fieldVo.setLabel(orderListConfigPO.getLabel());
                fieldVo.setValue(formMaps.get(orderListConfigPO.getAlias()));
                fieldVoList.add(fieldVo);
            });
            ApiAppointOrderDetailVO apiAppointOrderDetailVO = new ApiAppointOrderDetailVO();
            apiAppointOrderDetailVO.setAppointOrderId(appointOrderDetailVO.getId());
            apiAppointOrderDetailVO.setTitleName(venderNameMap.get(appointOrderDetailVO.getVenderId()));
            apiAppointOrderDetailVO.setFieldVos(fieldVoList);
            apiAppointOrderDetailVO.setAppointStatusName(appointOrderDetailVO.getChAppointStatus());
            apiAppointOrderDetailVO.setOperateItemVos(operateMap.get(appointOrderDetailVO.getAppointStatus()));
            return apiAppointOrderDetailVO;
        }).collect(Collectors.toList());
        return apiAppointOrderDetailVOList;
    }

    /**
     * 获取商家名称
     *
     * @param appointOrderDetailVOList
     * @return
     */
    private Map<Long, String> getVenderName(List<AppointOrderDetailVO> appointOrderDetailVOList) {
        List<Long> venderIds = appointOrderDetailVOList.stream().map(appointOrderDetailVO -> appointOrderDetailVO.getVenderId()).collect(Collectors.toList());
        //根据商家ID获取商家信息
        List<BusinessVenderMapPO> businessVenderMapPOList = businessVenderMapService.listVender(venderIds);
        return businessVenderMapPOList.stream().collect(Collectors.toMap(BusinessVenderMapPO::getVenderId, BusinessVenderMapPO::getVenderName));
    }

    /**
     * 合并预约单信息
     *
     * @return
     */
    private List<AppointOrderDetailVO> getOrderDetailInfo(List<AppointOrderPO> appointOrderPOList) {
        Map<Long, AppointOrderPO> appointOrderPOMap = new HashMap<>();
        List<Long> appointOrderIds = new ArrayList<>();
        appointOrderPOList.stream().forEach(appointOrderPO -> {
            appointOrderIds.add(appointOrderPO.getId());
            appointOrderPOMap.put(appointOrderPO.getId(), appointOrderPO);
        });
        List<AppointOrderServiceItemPO> appointOrderServiceItemPOS =
                appointOrderServiceItemService.getAppointServiceItems(appointOrderIds);
        List<AppointOrderDetailVO> appointOrderDetailVOList = appointOrderServiceItemPOS.stream().map(appointOrderServiceItemPO -> {
            AppointOrderPO appointOrderPO = appointOrderPOMap.get(appointOrderServiceItemPO.getAppointOrderId());
            AppointOrderDetailVO appointOrderDetailVO = new AppointOrderDetailVO();
            BeanUtils.copyProperties(appointOrderServiceItemPO, appointOrderDetailVO);
            BeanUtils.copyProperties(appointOrderPO, appointOrderDetailVO);
            appointOrderDetailVO.setAppointStatus(AppointStatusEnum.getFromCode(appointOrderPO.getAppointStatus().getIntValue()).getIntValue());
            appointOrderDetailVO.setServerType(ServerTypeEnum.getFromCode(appointOrderPO.getServerType()).getIntValue());
            appointOrderDetailVO.setSkuName(appointOrderPO.getSkuName());
            return appointOrderDetailVO;
        }).collect(Collectors.toList());
        return appointOrderDetailVOList;
    }

    /**
     * 动态列表分页转换
     * @param pageInfo
     * @param apiAppointOrderDetailVOS
     * @return
     */
    private Page<ApiAppointOrderDetailVO> convertDynamicListPage(PageInfo pageInfo, List<ApiAppointOrderDetailVO> apiAppointOrderDetailVOS) {
        Page<ApiAppointOrderDetailVO> orderDetailVOPage = new Page<>();
        BeanUtils.copyProperties(pageInfo, orderDetailVOPage);
        orderDetailVOPage.setTotalCount(pageInfo.getTotal());
        orderDetailVOPage.setPageNumber(pageInfo.getPageNum());
        orderDetailVOPage.setList(apiAppointOrderDetailVOS);
        return orderDetailVOPage;
    }

    /**
     * 列表分页转换
     * @param pageInfo
     * @param orderDetailWithOperateVOList
     * @return
     */
    private Page<OrderDetailWithOperateVO> convertAppointPage(PageInfo pageInfo,List<OrderDetailWithOperateVO> orderDetailWithOperateVOList){
        Page<OrderDetailWithOperateVO> detailWithOperateVOPage = new Page<>();
        BeanUtils.copyProperties(pageInfo,detailWithOperateVOPage);
        detailWithOperateVOPage.setTotalCount(pageInfo.getTotal());
        detailWithOperateVOPage.setPageNumber(pageInfo.getPageNum());
        detailWithOperateVOPage.setList(orderDetailWithOperateVOList);
        return detailWithOperateVOPage;
    }

    /**
     * 获取动态信息
     *
     * @param dynamicOrderListDTOList
     * @return
     */
    private List<DynamicOrderListDTO> getDynamicOrderInfo(List<DynamicOrderListDTO> dynamicOrderListDTOList) {
        dynamicOrderListDTOList.forEach(dynamicOrderListDTO -> {
            List<AppointOrderFormItemPO> appointOrderFormItemPOList =
                    appointOrderFormItemService.getFormItemListByAppointOrderId(dynamicOrderListDTO.getAppointOrderId());
            appointOrderFormItemPOList.forEach(appointOrderFormItemPO -> {
                dynamicOrderListDTO.getFormMaps().put(appointOrderFormItemPO.getAttrNameAlias(), appointOrderFormItemPO.getAttrValue());
            });
        });
        return dynamicOrderListDTOList;
    }
}
