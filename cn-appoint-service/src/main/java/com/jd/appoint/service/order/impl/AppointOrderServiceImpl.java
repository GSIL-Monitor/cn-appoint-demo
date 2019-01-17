package com.jd.appoint.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jd.air.common.enums.converter.EnumConverter;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.common.security.LocalSecurityClient;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.dao.order.AppointOrderDao;
import com.jd.appoint.dao.order.AppointOrderFormItemDao;
import com.jd.appoint.dao.order.AppointOrderServiceItemDao;
import com.jd.appoint.dao.shop.ShopFormControlItemDao;
import com.jd.appoint.domain.config.OrderDetailConfigPO;
import com.jd.appoint.domain.dto.ReschuleDTO;
import com.jd.appoint.domain.enums.*;
import com.jd.appoint.domain.order.AppointOrderFormItemPO;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import com.jd.appoint.domain.order.OrderStatisticPO;
import com.jd.appoint.domain.order.query.AppointUpdateQuery;
import com.jd.appoint.domain.rpc.query.MappingStatusQuery;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import com.jd.appoint.rpc.ExpressService;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.rpc.jmi.dto.IdentityDTO;
import com.jd.appoint.rpc.jmi.jicc.RpcIdentityService;
import com.jd.appoint.rpc.search.RpcAppointSearchGwService;
import com.jd.appoint.service.mq.AppointJmqProducer;
import com.jd.appoint.service.mq.msg.AppointNoticeMsg;
import com.jd.appoint.service.order.*;
import com.jd.appoint.service.order.convert.AppointOrderConvert;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.exceptions.InputLnsException;
import com.jd.appoint.service.order.key.UniqueGeneratorService;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.service.order.operate.OperateService;
import com.jd.appoint.service.order.operate.finish.FinishAppointExcutor;
import com.jd.appoint.service.order.operate.finish.FinishOperateService;
import com.jd.appoint.service.order.vo.ValueFromControlItem;
import com.jd.appoint.service.record.BuriedPointService;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.appoint.service.shop.ShopStaffService;
import com.jd.appoint.service.sms.SmsPointEnum;
import com.jd.appoint.service.sms.templets.SendMessageService;
import com.jd.appoint.service.sys.VenderConfigConstant;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.service.task.TasksService;
import com.jd.appoint.service.util.BuriedPointUtil;
import com.jd.appoint.shopapi.vo.LsnInputVO;
import com.jd.appoint.shopapi.vo.LsnVO;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.order.*;
import com.jd.pop.configcenter.client.StringUtils;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import com.jd.virtual.appoint.city.CityItem;
import com.jd.virtual.appoint.service.ServiceItem;
import com.jd.virtual.appoint.store.StoreItem;
import com.jd.xn.slog.LogSecurity;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by gaoxiaoqing on 2018/5/15.
 */
@Service(value = "appointOrderService")
public class AppointOrderServiceImpl implements AppointOrderService {

    private static final Logger logger = LoggerFactory.getLogger(AppointOrderServiceImpl.class);
    @Resource
    private AppointOrderDao appointOrderDao;
    @Resource
    private AppointJmqProducer appointJmqProducer;
    @Resource
    private AppointOrderServiceItemService appointOrderServiceItemService;
    @Resource
    private AppointOrderServiceItemDao appointOrderServiceItemDao;
    @Resource
    private AppointOrderFormItemDao appointOrderFormItemDao;
    @Resource
    private AppointOrderFormItemService formItemService;
    @Autowired
    private ShopFormControlItemDao shopFormControlItemDao;
    @Autowired
    private RpcIdentityService rpcIdentityService;
    @Resource
    private VenderConfigService venderConfigService;
    @Autowired
    private ShopFormControlItemService formControlItemService;
    @Autowired
    private RpcAppointSearchGwService rpcAppointSearchGwService;
    @Autowired
    private LocalSecurityClient localSecurityClient;
    @Resource
    private BuriedPointService buriedPointService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    TransactionDefinition DEFAULT_TRANSACTION_DEFINITION = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Resource
    private FinishOperateService finishOperateService;
    @Resource
    private SendMessageService sendMessageService;
    @Autowired
    private TasksService tasksService;
    @Autowired
    private ShopStaffService shopStaffService;
    @Autowired
    private OperateService operateService;
    @Resource
    private ExpressService expressService;
    @Autowired
    private RpcContextService rpcContextService;
    @Autowired
    private UniqueGeneratorService uniqueGeneratorService;
    @Autowired
    private OrderDetailConfigService orderDetailConfigService;
    @Autowired
    private DynamicOrderService dynamicOrderService;

    @Autowired
    private PopConfigService popConfigService;

    private static final String START_APPOINT_FORMAT = "yyyy-MM-dd HH:mm";


    @Override
    public AppointOrderDetailVO detail(long id) {
        AppointOrderDetailVO appointOrderDetailVO = new AppointOrderDetailVO();

        //查询appoint_order表
        AppointOrderPO appointOrderPO = appointOrderDao.findById(id);
        //没有查到order详情返回null
        if (appointOrderPO == null) {
            return null;
        }

        //查询appoint_order_service_item表
        AppointOrderServiceItemPO appointOrderServiceItemPO = appointOrderServiceItemDao.selectByAppointOrderId(id);

        //查询appoint_order_form_item表
        List<AppointOrderFormItemPO> appointOrderFormItemPOList = appointOrderFormItemDao.selectFormItemListByAppointOrderId(id);
        //解密动态表单项
        Map<String, String> formItems = getDecryptedFormItemMap(appointOrderFormItemPOList, appointOrderPO.getCustomerUserPin());

        //得到detailVO
        if (null != appointOrderServiceItemPO) {
            BeanUtils.copyProperties(appointOrderServiceItemPO, appointOrderDetailVO);
        }
        if (null != appointOrderPO) {
            BeanUtils.copyProperties(appointOrderPO, appointOrderDetailVO);
        }
        appointOrderDetailVO.setStoreCode(appointOrderServiceItemPO.getStoreCode());
        appointOrderDetailVO.setFormItems(formItems);

        //为没有成功copy的字段手动赋值到vo
        //重新赋值id,两次copyProperties方法id重复赋值
        appointOrderDetailVO.setId(id);
        appointOrderDetailVO.setAppointStatus(appointOrderPO.getAppointStatus().getIntValue());
        //设置系统中文状态（具体端调用还需要重新赋值）
        setChStatus(appointOrderDetailVO);
        appointOrderDetailVO.setChSource(SourceEnum.getFromCode(appointOrderDetailVO.getSource()).getPerson());
        if (null != appointOrderPO.getPreAppointStatus()) {
            appointOrderDetailVO.setPreAppointStatus(appointOrderPO.getPreAppointStatus().getIntValue());
        }
        appointOrderDetailVO.setChServerType(ServerTypeEnum.getFromCode(appointOrderDetailVO.getServerType()).getAlias());
        return appointOrderDetailVO;
    }

    /**
     * 快速获得订单详情接口
     * @param id
     * @return
     */
    public AppointOrderDetailVO fastDetail(long id) {
        AppointOrderDetailVO appointOrderDetailVO = new AppointOrderDetailVO();
        //查询appoint_order表
        AppointOrderPO appointOrderPO = appointOrderDao.findById(id);
        //没有查到order详情返回null
        if (appointOrderPO == null) {
            return null;
        }
        //查询appoint_order_service_item表
        AppointOrderServiceItemPO appointOrderServiceItemPO = appointOrderServiceItemDao.selectByAppointOrderId(id);
        //得到detailVO
        if (null != appointOrderServiceItemPO) {
            BeanUtils.copyProperties(appointOrderServiceItemPO, appointOrderDetailVO);
        }
        if (null != appointOrderPO) {
            BeanUtils.copyProperties(appointOrderPO, appointOrderDetailVO);
        }
        return appointOrderDetailVO;
    }

    private void setChStatus(AppointOrderDetailVO appointOrderDetailVO) {
        MappingStatusQuery mappingStatusQuery = new MappingStatusQuery();

        mappingStatusQuery.setBusinessCode(appointOrderDetailVO.getBusinessCode());
        Integer appointStatus = appointOrderDetailVO.getAppointStatus();
        AppointStatusEnum appointStatusEnum = EnumConverter.getEnum(String.valueOf(appointStatus), AppointStatusEnum.class);
        mappingStatusQuery.setAppointStatusEnum(appointStatusEnum);
        mappingStatusQuery.setServerTypeEnum(ServerTypeEnum.getFromCode(appointOrderDetailVO.getServerType()));
        String chStatus = popConfigService.querySystemStatus(mappingStatusQuery);
        if (!org.springframework.util.StringUtils.isEmpty(chStatus)) {
            appointOrderDetailVO.setChAppointStatus(chStatus);
        }
    }

    /**
     * 获取订单详情的接口
     *
     * @param id
     * @param businessCode
     * @param platform
     * @param serverType
     * @param venderId
     * @param storeId
     * @return
     */
    @Override
    public SoaResponse<DynamicOrderDetailVO> dynamicDetail(long id, String businessCode, Integer platform, Integer serverType, Long venderId, Long storeId) {

        //获取detail展示项  order_detail_config
        List<OrderDetailConfigPO> detailItems = orderDetailConfigService.getDetailItems(businessCode, platform, serverType);

        if (org.springframework.util.CollectionUtils.isEmpty(detailItems)) {
            logger.info("您传的参数查询不到对应的详情配置");
            return new SoaResponse<>();
        }

        //获取detail数据
        AppointOrderDetailVO detail = detail(id);
        encryptInfo(detail);
        //校验venderId和businessCode
        if (detail == null) {
            return new SoaResponse<>();
        }
        //商家查询storeId传null，只有门店端校验
        if (storeId != null && !detail.getStoreCode().equals(storeId + "")) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "storeId入参和根据id查询到的不匹配");
        }
        //门店查询venderId传null，只有商家端校验
        if (venderId != null && !detail.getVenderId().equals(venderId)) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "venderId入参和根据id查询到的不匹配");
        }
        if (!detail.getBusinessCode().equals(businessCode)) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "businessCode入参和根据id查询到的不匹配");
        }
        //分配各展示项的值
        List<OrderDetailGroupVO> detailGroupVOS = new ArrayList<>();
        OrderDetailGroupVO detailGroupVO = new OrderDetailGroupVO();
        List<OrderDetailItemVO> detailItemList = new ArrayList<>();
        TimeShowTypeEnum timeShowTypeEnum = venderConfigService.getTimeShowType(businessCode);
        for (OrderDetailConfigPO orderDetailConfigPO : detailItems) {
            String value = dynamicOrderService.invoke(orderDetailConfigPO.getAlias(), detail, timeShowTypeEnum);

            OrderDetailItemVO detailItem = new OrderDetailItemVO();
            detailItem.setAlias(orderDetailConfigPO.getAlias());
            detailItem.setLabel(orderDetailConfigPO.getLabel());
            detailItem.setValue(value);
            if (detailGroupVO.getGroupName() == null) {
                detailGroupVO.setGroupName(orderDetailConfigPO.getGroupName());
                detailItemList.add(detailItem);
            } else if (orderDetailConfigPO.getGroupName().equals(detailGroupVO.getGroupName())) {
                detailItemList.add(detailItem);
            } else {
                //新的group，将上一个Group放入List,重建GroupVO
                detailGroupVO.setOrderDetailItemVOList(detailItemList);
                detailGroupVOS.add(detailGroupVO);
                detailGroupVO = new OrderDetailGroupVO();
                detailItemList = new ArrayList<>();
                detailItemList.add(detailItem);
            }

        }

        detailGroupVO.setOrderDetailItemVOList(detailItemList);
        detailGroupVOS.add(detailGroupVO);
        DynamicOrderDetailVO dynamicOrderDetailVO = new DynamicOrderDetailVO();
        dynamicOrderDetailVO.setOrderDetailGroupVOList(detailGroupVOS);
        dynamicOrderDetailVO.setSkuId(detail.getSkuId());
        dynamicOrderDetailVO.setAppointStartTime(detail.getAppointStartTime());
        dynamicOrderDetailVO.setLogisticsNo(detail.getLogisticsNo());
        dynamicOrderDetailVO.setLogisticsSource(detail.getLogisticsSource());
        dynamicOrderDetailVO.setLogisticsSiteId(detail.getLogisticsSiteId());
        dynamicOrderDetailVO.setVenderMemo(detail.getVenderMemo());
        SoaResponse<DynamicOrderDetailVO> soaResponse = new SoaResponse<>();
        soaResponse.setResult(dynamicOrderDetailVO);

        return soaResponse;
    }

    @Override
    public List<AppointOrderDetailVO> appointDetailList(List<AppointOrderPO> appointOrderPOList) {
        //获取预约单ID
        Map<Long, AppointOrderPO> appointOrderPOMap = new HashMap<>();
        List<Long> appointOrderIds = new ArrayList<>();
        appointOrderPOList.stream().forEach(appointOrderPO -> {
            appointOrderIds.add(appointOrderPO.getId());
            appointOrderPOMap.put(appointOrderPO.getId(), appointOrderPO);
        });
        //获取服务条目信息
        List<AppointOrderServiceItemPO> appointOrderServiceItemPOS =
                appointOrderServiceItemService.getAppointServiceItems(appointOrderIds);
        //获取所有动态配置项
        List<AppointOrderFormItemPO> appointOrderFormItemPOList = formItemService.getAppointFormItems(appointOrderIds);
        //根据ID对配置项分组
        Map<Long, List<AppointOrderFormItemPO>> formItemMap = appointOrderFormItemPOList.stream()
                .collect(Collectors.groupingBy(AppointOrderFormItemPO::getAppointOrderId, Collectors.toList()));
        //组装预约单详情
        List<AppointOrderDetailVO> appointOrderDetailVOList = appointOrderServiceItemPOS.stream().map(appointOrderServiceItemPO -> {
            AppointOrderPO appointOrderPO = appointOrderPOMap.get(appointOrderServiceItemPO.getAppointOrderId());
            AppointOrderDetailVO appointOrderDetailVO = new AppointOrderDetailVO();
            BeanUtils.copyProperties(appointOrderServiceItemPO, appointOrderDetailVO);
            BeanUtils.copyProperties(appointOrderPO, appointOrderDetailVO);
            appointOrderDetailVO.setStoreCode(appointOrderServiceItemPO.getStoreCode());
            appointOrderDetailVO.setAppointStatus(appointOrderPO.getAppointStatus().getIntValue());
            if (null != appointOrderPO.getPreAppointStatus()) {
                appointOrderDetailVO.setPreAppointStatus(appointOrderPO.getPreAppointStatus().getIntValue());
            }
            appointOrderDetailVO.setServerType(ServerTypeEnum.getFromCode(appointOrderPO.getServerType()).getIntValue());
            appointOrderDetailVO.setSkuName(appointOrderPO.getSkuName());
            //设置系统中文状态（具体端调用还需要重新赋值）
            appointOrderDetailVO.setChAppointStatus(appointOrderPO.getAppointStatus().getAlias());
            //获取动态配置项
            List<AppointOrderFormItemPO> orderFormItemPOList = formItemMap.get(appointOrderPO.getId());
            if (CollectionUtils.isNotEmpty(orderFormItemPOList)) {
                appointOrderDetailVO.setFormItems(getDecryptedFormItemMaps(orderFormItemPOList, appointOrderPO.getCustomerUserPin()));
            }
            return appointOrderDetailVO;
        }).collect(Collectors.toList());
        return appointOrderDetailVOList;
    }

    private Map<String, String> getDecryptedFormItemMap(List<AppointOrderFormItemPO> appointOrderFormItemPOList, String userPin) {
        Map<String, String> formItems = new HashMap<>();
        appointOrderFormItemPOList.forEach(appointOrderFormItemPO -> {
            //查shop_form_control_item表找到每一项的加密类型
            ShopFormControlItemPO shopFormControlItemPO = shopFormControlItemDao.findById(appointOrderFormItemPO.getFormControlId());
            if (shopFormControlItemPO == null) {
                return;
            }
            EncryptTypeEnum encryptType = shopFormControlItemPO.getEncryptType();
            switch (encryptType) {
                case NO_ENCRYPT:
                    formItems.put(shopFormControlItemPO.getAlias(), appointOrderFormItemPO.getAttrValue());
                    break;
                case JMI_ENCRYPT:
                    formItems.put(shopFormControlItemPO.getAlias(), jmiDecrypt(appointOrderFormItemPO.getAttrValue(), userPin));
                    break;
                case SAFETY_ENCRYPT:
                    formItems.put(shopFormControlItemPO.getAlias(), localDecrypt(appointOrderFormItemPO.getAttrValue()));
                    break;
            }
        });
        return formItems;
    }

    /**
     * 批量获取动态配置项
     *
     * @param appointOrderFormItemPOList
     * @param userPin
     * @return
     */
    private Map<String, String> getDecryptedFormItemMaps(List<AppointOrderFormItemPO> appointOrderFormItemPOList, String userPin) {
        List<Long> formItemIds = appointOrderFormItemPOList.stream()
                .map(AppointOrderFormItemPO::getFormControlId)
                .collect(Collectors.toList());
        //批量获取表单控制项
        List<ShopFormControlItemPO> shopFormControlItemPOs = shopFormControlItemDao.queryFormControlItems(formItemIds);
        //表单控制项放入Map
        Map<Long, ShopFormControlItemPO> shopFormMap = shopFormControlItemPOs.stream()
                .collect(Collectors.toMap(ShopFormControlItemPO::getId, Function.identity()));
        Map<String, String> formItems = new HashMap<>();
        appointOrderFormItemPOList.forEach(appointOrderFormItemPO -> {
            //查shop_form_control_item表找到每一项的加密类型
            ShopFormControlItemPO shopFormControlItemPO = shopFormMap.get(appointOrderFormItemPO.getFormControlId());
            if (shopFormControlItemPO == null) {
                return;
            }
            EncryptTypeEnum encryptType = shopFormControlItemPO.getEncryptType();
            switch (encryptType) {
                case NO_ENCRYPT:
                    formItems.put(shopFormControlItemPO.getAlias(), appointOrderFormItemPO.getAttrValue());
                    break;
                case JMI_ENCRYPT:
                    formItems.put(shopFormControlItemPO.getAlias(), jmiDecrypt(appointOrderFormItemPO.getAttrValue(), userPin));
                    break;
                case SAFETY_ENCRYPT:
                    formItems.put(shopFormControlItemPO.getAlias(), localDecrypt(appointOrderFormItemPO.getAttrValue()));
                    break;
            }
        });
        return formItems;
    }

    private String localDecrypt(String value) {
        return localSecurityClient.decrypt(value);
    }

    private String jmiDecrypt(String identity, String userPin) {
        IdentityDTO userIdentityInfo = rpcIdentityService.getUserIdentityInfo(identity, userPin);
        return userIdentityInfo.getCertNum();
    }


    /**
     * @param appointOrderPo
     * @param overwrite      覆盖true，追加false，
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int submitAttach(AppointOrderPO appointOrderPo, Boolean overwrite) {
        return appointOrderDao.submitAttach(appointOrderPo, overwrite);
    }

    @Override
    @Transactional
    public void cancel(AppointOrderPO appointOrderPO) {
        //待取消预约单
        AppointOrderPO cancelAppoint = appointOrderDao.findById(appointOrderPO.getId());
        cancelAppoint.setDateForm(appointOrderPO.getDateForm());
        if (null == cancelAppoint) {
            logger.error("取消没有该预约单appointOrderId={}", String.valueOf(appointOrderPO.getId()));
            throw new RuntimeException("没有该预约单");
        }
        //防止重复发短信
        if (AppointStatusEnum.APPOINT_CANCEL.equals(cancelAppoint.getAppointStatus())) {
            return;
        }
        //校验取消
        checkCancelAppoint(appointOrderPO, cancelAppoint);
        //订单明细  增加取消预约中。
        cancelAppoint.setAppointStatus(AppointStatusEnum.CANCELING);
        //取消预约条件
        List<Integer> limitStatus = getCancelLimitStatus(cancelAppoint);
        if (!AppointStatusEnum.CANCELING.equals(cancelAppoint.getAppointStatus()) && appointOrderDao.cancel(cancelAppoint, limitStatus) < 1) {
            //注：AppointStatusEnum.CANCELING 状态不能添加到limitStatus中，因为取消中的任务会调用改方法
            throw new RuntimeException("取消预约失败");
        }
        //订单明细  增加取消预约中。
        Future<AppointStatusEnum> future = this.threadPoolTaskExecutor.submit(() -> this.cancelAppoint(cancelAppoint, limitStatus));
        try {
            AppointStatusEnum appointStatusEnum = future.get(3, TimeUnit.SECONDS);
            //取消完成
            if (AppointStatusEnum.APPOINT_CANCEL == appointStatusEnum) {
                //发送消息
                appointJmqProducer.noticeAppointInfo(new AppointNoticeMsg(cancelAppoint.getId(), AppointStatusEnum.APPOINT_CANCEL.getIntValue()));
                //埋点代码
                appointOrderPO.setServerType(cancelAppoint.getServerType());
                appointOrderPO.setBusinessCode(cancelAppoint.getBusinessCode());
                appointOrderPO.setAppointStatus(cancelAppoint.getAppointStatus());
                buriedPointService.statusChangeBuried(appointOrderPO, ProcessTypeEnum.CHANGE_APPOINT_STATUS.getCode());
                return;
            } else if (appointStatusEnum == null) {//failure
                logger.error("取消预约失败：预约单预约单appointOrderId={},rollback status.", appointOrderPO.getId());
                appointOrderDao.rollbackStatus(appointOrderPO.getId());
                throw new RuntimeException("预约单appointOrderId=" + appointOrderPO.getId() + "取消预约失败");
            }
        } catch (TimeoutException e) {
            logger.error("取消3秒之内没有返回结果，预约单Id:" + appointOrderPO.getId());
            //超时属于正常操作,future会正常执行
        } catch (Exception e) {
            logger.error("cancel appoint order error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得取消时的订单状态限制
     * @param appointOrderPO
     * @return
     */
    private List<Integer> getCancelLimitStatus(AppointOrderPO appointOrderPO){
        List<Integer> stringList = Lists.newArrayList();
        stringList.add(AppointStatusEnum.APPOINT_FINISH.getIntValue());
        stringList.add(AppointStatusEnum.APPOINT_FAILURE.getIntValue());

        String businessCode = appointOrderPO.getBusinessCode();
        switch (appointOrderPO.getDateForm()) {
            case API:
                if(businessCode.equals(BusinessCodeEnum.DAZHAXIE.getKey())){
                    stringList.add(AppointStatusEnum.WAIT_SERVICE.getIntValue());
                }
                break;
        }
        return stringList;
    }

    /**
     * 校验取消的条件
     */
    private void checkCancelAppoint(AppointOrderPO appointOrderPO, AppointOrderPO cancelAppoint) {
        //校验判断各个来源的额参数是否有问题
        switch (appointOrderPO.getDateForm()) {
            case API:
                //客户自己取消
                if (!appointOrderPO.getCustomerUserPin().equals(cancelAppoint.getCustomerUserPin())) {
                    logger.error("客户的userPin不是下单的pin不一致 customerUserPin={}", appointOrderPO.getCustomerUserPin());
                    throw new RuntimeException("取消预约失败");
                }
                break;
            case SHOP:
                //如果有商家ID并且商家ID不为系统中的商家ID不能取消
                if (!appointOrderPO.getVenderId().equals(cancelAppoint.getVenderId())) {
                    logger.error("商家的venderId不匹配venderId={}", String.valueOf(appointOrderPO.getVenderId()));
                    throw new RuntimeException("取消预约失败");
                }
                break;
            case STAFF:
                //量体师端的取消
                if (null != appointOrderPO.getStaffPin()) {
                    AppointOrderServiceItemPO appointOrderServiceItemPO =
                            appointOrderServiceItemService.getServiceItemByAppointOrderId(appointOrderPO.getId());
                    if (null == appointOrderServiceItemPO ||
                            !appointOrderPO.getStaffPin().equals(appointOrderServiceItemPO.getStaffUserPin())) {
                        logger.error("量体师的pin和下单时候派单的量体师不一致staffPin={}", appointOrderPO.getStaffPin());
                        throw new RuntimeException("取消预约失败");
                    }
                }
                break;
            case STORE:
                AppointOrderServiceItemPO appointOrderServiceItemPO =
                        appointOrderServiceItemService.getServiceItemByAppointOrderId(appointOrderPO.getId());
                //商家ID和门店ID
                if (null == appointOrderServiceItemPO
                        || !appointOrderPO.getStoreCode().equals(appointOrderServiceItemPO.getStoreCode())) {
                    logger.error("没有找到预约单appointOrderId={}，或商家的storeCode不匹配,storeCode={}", appointOrderPO.getId(), String.valueOf(appointOrderPO.getVenderId()));
                    throw new RuntimeException("取消预约失败");
                }
                break;
            case SYSTEM:
                logger.info("该订单由系统取消，appointOrderPO={}", JSON.toJSONString(appointOrderPO));
                break;
            default:
                logger.error("取消预约失败：没有找到调用取消服务的来源入口");
                throw new RuntimeException("取消预约失败");
        }
    }


    /**
     * 取消预约所有的链上执行完成
     *
     * @param cancelAppoint
     * @return
     */
    private AppointStatusEnum cancelAppoint(AppointOrderPO cancelAppoint, List<Integer> stringList) {
        AppointOrderDetailVO appointOrderDetailVO = this.detail(cancelAppoint.getId());
        OperateResultEnum resultEnum = operateService.execute(appointOrderDetailVO,
                OperateService.OperateEnum.CANCLE_OPRDER);
        if (resultEnum == OperateResultEnum.RETRY) {//如果执行失败，则返回前端预约中，等待重试
            cancelAppoint.setAppointStatus(AppointStatusEnum.CANCELING);
        } else if (OperateResultEnum.SUCCESS.equals(resultEnum)) {//如果执行成功，则返回前端成功状态
            cancelAppoint.setAppointStatus(AppointStatusEnum.APPOINT_CANCEL);
        } else {//如果执行失败，则返回前端预约失败
            logger.error("取消预约失败");
            return null;
        }
        //取消预约
        appointOrderDao.cancel(cancelAppoint, stringList);
        return cancelAppoint.getAppointStatus();
    }


    @Override
    public void finishAppoint(FinishAppointDto finishAppointDto, int platform) {
        Assert.notNull(finishAppointDto.getAppointOrderPO().getId(), "完成预约传递的预约单号为空");
        AppointFinishVO appointFinishVO = finishAppointDto.getAppointFinishVO();
        AppointOrderPO appointOrderPO = this.findOne(appointFinishVO.getAppointOrderId());
        if (AppointStatusEnum.WAIT_SERVICE.equals(appointOrderPO.getAppointStatus())
                    || AppointStatusEnum.APPOINT_FINISH.equals(appointOrderPO.getAppointStatus())) {

        }else {
            throw new RuntimeException("完成预约前置状态不对");
        }
        appointOrderPO.setAppointFinishTime(new Date());
        appointOrderPO.setOperateUser(appointFinishVO.getOperateUser());
        appointOrderPO.setEndOrderId(appointFinishVO.getEndOrderId());
        if(null != appointFinishVO.getAttach()){
            appointOrderPO.setAttrUrls(appointFinishVO.getAttach().getUrls());
        }
        finishAppointDto.setAppointOrderPO(appointOrderPO);
        //获得需要持久到数据库的参数
        FinishAppointExcutor fae = null;
        try {
            fae = finishOperateService.getOperate(finishAppointDto);
            if (PlatformEnum.STAFF.getIntValue() == platform) {
                //获得待完成的预约单
                AppointOrderServiceItemPO appointOrderServiceItemPO =
                        appointOrderServiceItemService.getServiceItemByAppointOrderId(finishAppointDto.getAppointOrderPO().getId());
                if (null != appointOrderServiceItemPO && null != finishAppointDto.getAppointOrderPO()) {
                    finishAppointDto.getAppointOrderPO().setOperateUser(appointOrderServiceItemPO.getStaffUserPin());
                }
            }
            //执行完成预约操作
            OperateResultEnum operateResultEnum = fae.excute(finishAppointDto);
            //如果执行失败抛出异常 不考虑重试
            if (OperateResultEnum.FAIL.equals(operateResultEnum)) {
                logger.error("执行完成预约失败:请求参数finishAppointDto={}", LogSecurity.toJSONString(finishAppointDto));
                throw new RuntimeException("执行完成预约失败");
            }
        } finally {
            fae.close();
        }
    }


    @Override
    public void dispatchOrder(AppointOrderPO appointOrderPo) {
        appointOrderDao.dispatchOrder(appointOrderPo);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void dynamicUpdateAppoint(UpdateAppointVO updateAppointVO) {
        //获取预约单信息
        AppointOrderDetailVO appointOrderDetailVO = detail(updateAppointVO.getAppointOrderId());
        //验证更新预约单
        validateUpdateAppoint(appointOrderDetailVO, updateAppointVO);
        //更新预约单条目表
        appointOrderServiceItemService.updateServiceItem(AppointOrderConvert.convertServiceItem(updateAppointVO));
        //更新动态条目项
       /* if (null != updateAppointVO.getFormItems() && updateAppointVO.getFormItems().size() > 0) {
            updateFormItems(appointOrderPO.getId(), updateAppointVO.getFormItems());
        }*/
    }

    /**
     * 验证更新预约单
     *
     * @param appointOrderDetailVO
     * @return
     */
    public void validateUpdateAppoint(AppointOrderDetailVO appointOrderDetailVO, UpdateAppointVO updateAppointVO) {
        //校验预约单信息
        if (null == appointOrderDetailVO) {
            throw new IllegalArgumentException("不存在该预约单,预约单号");
        }
        //供应商信息不一致
        if (null != updateAppointVO.getVenderId() && !appointOrderDetailVO.getVenderId().equals(updateAppointVO.getVenderId())) {
            throw new IllegalArgumentException("商家信息不一致,预约单号=" + appointOrderDetailVO.getId());
        }
        //判断门店ID是否一致
        if (null != updateAppointVO.getStoreCode() &&
                !appointOrderDetailVO.getStoreCode().equals(updateAppointVO.getStoreCode())) {
            throw new IllegalArgumentException("门店ID不一致,预约单号=" + appointOrderDetailVO.getId());
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void editAppointOrder(AppointUpdateQuery appointUpdateQuery) {
        //获取预约单信息
        AppointOrderPO appointOrderPO = appointOrderDao.findById(appointUpdateQuery.getAppointOrderId());
        if (appointUpdateQuery.getAppointStartTime().after(appointUpdateQuery.getAppointEndTime())) {
            logger.error("开始时间不可大于结束时间");
            throw new IllegalArgumentException("开始时间不可大于结束时间");
        }
        //校验预约单信息
        if (!validateAppointOrder(appointUpdateQuery.getAppointOrderId(), appointOrderPO)) {
            throw new IllegalArgumentException("修改预约单信息异常");
        }
        //更新动态条目项
        if (null != appointUpdateQuery.getFormItems() && appointUpdateQuery.getFormItems().size() > 0) {
            updateFormItems(appointOrderPO.getId(), appointUpdateQuery.getFormItems());
        }
        //更新预约单信息
        appointOrderDao.updateAppointOrder(AppointOrderConvert.convertAppointOrder(appointUpdateQuery));
        //获取预约单服务条目
        AppointOrderServiceItemPO appointOrderServiceItemPO =
                appointOrderServiceItemService.getServiceItemByAppointOrderId(appointUpdateQuery.getAppointOrderId());
        if (null == appointOrderServiceItemPO) {
            throw new IllegalArgumentException("修改预约单服务信息异常");
        }
        //判断是否来自shop端
        boolean isShop = venderConfigService.isShop(appointOrderPO.getBusinessCode(), appointOrderPO.getVenderId());
        //网关接口(非shop端)-这里只是更新一个中间状态，直接用worker调用商家接口
        if (!isShop) {
            return;
        }
        //判断所改预约单供应商是否一致（仅shop端配置）
        if (!appointOrderPO.getVenderId().equals(appointUpdateQuery.getVenderId())) {
            throw new IllegalArgumentException("修改预约单信息商家ID异常,预约单号=" + appointOrderPO.getId());
        }
        //更新服务员信息
        if (null == appointOrderServiceItemPO.getStaffCode() || !appointUpdateQuery.getStaffId().toString().equals(appointOrderServiceItemPO.getStaffCode())) {
            appointOrderServiceItemService.dispatchOrder(
                    AppointOrderConvert.convertAppointDispatcher(appointUpdateQuery, appointOrderPO.getAppointStatus().getIntValue()));
            //状态变更
            if (AppointStatusEnum.WAIT_ORDER == appointOrderPO.getAppointStatus()) {
                appointOrderPO.setOperateUser(appointUpdateQuery.getOperateUserPin());
                buriedPointService.statusChangeBuried(appointOrderPO, ProcessTypeEnum.CHANGE_APPOINT_STATUS.getCode());
            }
        }
        //预约单更新埋点
        burryUpdateAppointInfo(appointOrderPO, appointUpdateQuery);
    }

    /**
     * 修改预约信息埋点
     *
     * @param appointOrderPO
     * @param appointUpdateQuery
     */
    private void burryUpdateAppointInfo(AppointOrderPO appointOrderPO, AppointUpdateQuery appointUpdateQuery) {
        Map<String, String> mapParam = new HashMap<>();
        //修改预约时间
        if (isTimeChange(appointUpdateQuery, appointOrderPO)) {
            mapParam.put("appointTime", BuriedPointUtil.getAppointTime(appointUpdateQuery.getAppointStartTime(), appointUpdateQuery.getAppointEndTime()));
            mapParam.put("operateUser", appointUpdateQuery.getOperateUserPin());
            buriedPointService.processBuried(appointOrderPO.getId().toString(), ProcessTypeEnum.CHANGE_SERVICE_TIME.getCode(), mapParam);
        }
    }

    /**
     * 验证时间是否变更
     *
     * @param appointUpdateQuery
     * @param appointOrderPO
     * @return
     */
    private Boolean isTimeChange(AppointUpdateQuery appointUpdateQuery, AppointOrderPO appointOrderPO) {
        //时间无变更
        if (AppointDateUtils.isSameDate(appointUpdateQuery.getAppointStartTime(), appointOrderPO.getAppointStartTime())
                && AppointDateUtils.isSameDate(appointUpdateQuery.getAppointEndTime(), appointOrderPO.getAppointEndTime())) {
            return false;
        }
        //时间变更
        return true;
    }

    @Override
    public AppointOrderPO getAppointOrder(Long appointOrderId) {
        return appointOrderDao.findById(appointOrderId);
    }

    @Override
    public AppointOrderResult submitAppoint(AppointOrderDetailVO detail) {
        //预约结束>=开始
        if (detail.getAppointEndTime() != null
                && detail.getAppointEndTime().getTime() < detail.getAppointStartTime().getTime()) {
            throw new IllegalArgumentException("预约结束时间需要>=开始");
        }
        AppointOrderResult result = new AppointOrderResult();
        //表单项目加密，验证，如果不通过就快速失败，抛出异常
        Map<String, ValueFromControlItem> fromControlItemMap = formControlItemService.createValueMap(detail);
        //提交本地订单
        AppointOrderPO appointOrderPO = this.submitLocalAppoint(detail, fromControlItemMap);
        result.setOrderId(appointOrderPO.getId());
        result.setAppointStatus(appointOrderPO.getAppointStatus().getIntValue());
        detail.setId(appointOrderPO.getId());
        detail.setAppointStatus(appointOrderPO.getAppointStatus().getIntValue());
        Future<AppointStatusEnum> future = this.threadPoolTaskExecutor.submit(() -> this.submitVenderOrder(detail));
        try {
            AppointStatusEnum appointStatusEnum = future.get(3, TimeUnit.SECONDS);
            if (appointStatusEnum != null) {
                result.setAppointStatus(appointStatusEnum.getIntValue());
            }
        } catch (TimeoutException e) {
            logger.error("供应商3秒之内没有返回结果，预约单Id:" + appointOrderPO.getId());
            //超时属于正常操作,future会正常执行
        } catch (Exception e) {
            logger.error("submitGwAndAddTask error", e);
        }
        return result;
    }

    public AppointStatusEnum submitVenderOrder(AppointOrderDetailVO detail) {
        //只有新订单需要重新创建,其他状态的可能是客户端多次操作导致
        if (!detail.getAppointStatus().equals(AppointStatusEnum.NEW_ORDER.getIntValue())) {
            return AppointStatusEnum.getFromCode(detail.getAppointStatus());
        }
        OperateResultEnum resultEnum = operateService.execute(detail, OperateService.OperateEnum.SUBMIT_ORDER);
        if (resultEnum == OperateResultEnum.RETRY) {//如果执行失败，则返回前端预约中，等待重试
            return null;
        }
        AppointStatusEnum status;
        if (resultEnum == OperateResultEnum.SUCCESS) {//如果执行成功，则返回前端成功状态
            status = getSuccessInitStatus(detail.getBusinessCode(), detail.getServerType());
        } else {//如果执行失败，则返回前端预约失败
            status = AppointStatusEnum.APPOINT_FAILURE;
        }
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setId(detail.getId());
        appointOrderPO.setAppointStatus(status);
        //新订单才可以更新状态
        TransactionStatus transaction = transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            appointOrderDao.updateAppointStatus(appointOrderPO, AppointStatusEnum.NEW_ORDER.getIntValue());
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw e;
        }
        //提交订单埋点
        appointOrderPO.setOperateUser(detail.getCustomerUserPin());
        appointOrderPO.setServerType(detail.getServerType());
        appointOrderPO.setBusinessCode(detail.getBusinessCode());
        buriedPointService.statusChangeBuried(appointOrderPO, ProcessTypeEnum.NEW_APPOINT_ORDER.getCode());
        return status;
    }

    /**
     * 获取下单成功的订单初始状态
     * 服务类型：上门(1),到店(2)
     *
     * @param businessCode
     * @param serverType
     * @return
     */
    private AppointStatusEnum getSuccessInitStatus(String businessCode, int serverType) {
        VenderConfigVO config = venderConfigService.getConfig(businessCode, -1, VenderConfigConstant.INITIAL_SUCCESS_APPOINT_STATUS);
        //默认用 上门待派单，到店待服务
        if (config == null || Strings.isNullOrEmpty(config.getValue())) {
            if (serverType == 1) {
                return AppointStatusEnum.WAIT_ORDER;
            }
            return AppointStatusEnum.WAIT_SERVICE;
        } else {
            //有配置
            Map<Integer, Integer> serverTypeStatusMap = JSON.parseObject(config.getValue(), Map.class);
            if (!serverTypeStatusMap.containsKey(serverType)) {
                throw new IllegalStateException("businessCode " + VenderConfigConstant.INITIAL_SUCCESS_APPOINT_STATUS + "config not format");
            }
            return AppointStatusEnum.getFromCode(serverTypeStatusMap.get(serverType));
        }
    }

    private AppointOrderPO submitLocalAppoint(AppointOrderDetailVO data, Map<String, ValueFromControlItem> fromControlItemMap) {
        AppointOrderPO appointOrderPO = AppointOrderConvert.convertToAppointOrder(data);
        //商家订单默认状态下单失败，商家方订单下单成功时修改为正常的初始状态
        AppointStatusEnum appointStatus = AppointStatusEnum.NEW_ORDER;
        appointOrderPO.setAppointStatus(appointStatus);
        String uniqueKey = uniqueGeneratorService.createUniqueKey(data);
        TransactionStatus transaction = transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            if (uniqueKey != null) {//需要幂等性
                AppointOrderPO byUniqueKey = this.appointOrderDao.findByUniqueKey(uniqueKey);
                if (byUniqueKey != null) {
                    return byUniqueKey;
                }
                //没找到，保存幂等KEY
                appointOrderPO.setUniqueKey(uniqueKey);
            }
            //先插入订单
            this.appointOrderDao.insert(appointOrderPO);
            //插入表单项目
            fromControlItemMap.values().stream()
                    .map(item -> AppointOrderConvert.convertToOrderFormItem(item, appointOrderPO.getId()))
                    .forEach(appointOrderFormItemPO -> formItemService.insert(appointOrderFormItemPO));
            AppointOrderServiceItemPO appointOrderServiceItemPO = AppointOrderConvert.convertToOrderServiceItem(data, appointOrderPO.getId());
            appointOrderServiceItemService.insert(appointOrderServiceItemPO);
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw e;
        }
        return appointOrderPO;
    }


    /**
     * 校验预约单信息
     *
     * @return
     */
    private Boolean validateAppointOrder(Long appointOrderId, AppointOrderPO appointOrderPO) {
        //预约单是否存在
        if (null == appointOrderPO) {
            logger.error("并不存在该订单，请核实！ appointOrderId ={}", appointOrderId);
            return false;
        }
        //预约完成、预约失败、预约取消不可修改
        if (!AppointStatusEnum.isOperate(appointOrderPO.getAppointStatus().getIntValue())) {
            logger.error("该状态下不可修改预约单信息！appointOrderId ={},状态={}", appointOrderId, appointOrderPO.getAppointStatus().getAlias());
            return false;
        }
        return true;
    }

    /**
     * 验证时间是否变更
     *
     * @return
     */
    private Boolean isTimeChange(ReschuleDTO reschuleDTO, AppointOrderDetailVO appointOrderDetailVO) {
        //开始日期
        if (null == reschuleDTO.getAppointEndTime() &&
                AppointDateUtils.isSameDate(reschuleDTO.getAppointStartTime(), appointOrderDetailVO.getAppointStartTime())) {
            return false;
        }
        //时间槽类型
        if (AppointDateUtils.isSameDate(reschuleDTO.getAppointStartTime(), appointOrderDetailVO.getAppointStartTime())
                && AppointDateUtils.isSameDate(reschuleDTO.getAppointEndTime(), appointOrderDetailVO.getAppointEndTime())) {
            return false;
        }
        //时间变更
        return true;
    }

    @Override
    public OrderStatisticPO statisticByDate(LocalDate startDate, LocalDate endDate, Long venderId) {
        return appointOrderDao.statisticByDate(startDate, endDate, venderId);
    }

    /**
     * 更新动态服务项信息
     *
     * @param appointOrderId
     * @param params
     */
    private void updateFormItems(Long appointOrderId, Map<String, String> params) {
        //获取需要修改的别名
        List<String> aliasList = new ArrayList<>();
        params.forEach((k, v) -> aliasList.add(k));
        //获取订单的动态服务项
        List<AppointOrderFormItemPO> appointOrderFormItemPOList = appointOrderFormItemDao.queryItemsByOrderAndAlias(appointOrderId, aliasList);
        if (CollectionUtils.isEmpty(appointOrderFormItemPOList)) {
            return;
        }
        Map<Long, AppointOrderFormItemPO> formControlMap = new HashMap<>();
        List<Long> formControlIdList = new ArrayList<>();
        appointOrderFormItemPOList.forEach(appointOrderFormItemPO -> {
            appointOrderFormItemPO.setAttrValue(params.get(appointOrderFormItemPO.getAttrNameAlias())); //更新
            formControlMap.put(appointOrderFormItemPO.getFormControlId(), appointOrderFormItemPO); //订单对应的动态项
            formControlIdList.add(appointOrderFormItemPO.getFormControlId());
        });
        //根据动态控制信息
        List<ShopFormControlItemPO> shopFormControlItemPOList = shopFormControlItemDao.queryFormControlItems(formControlIdList);
        if (CollectionUtils.isEmpty(shopFormControlItemPOList)) {
            return;
        }
        //判断是否加密
        shopFormControlItemPOList.forEach(shopFormControlItemPO -> {
            AppointOrderFormItemPO appointOrderFormItemPO = formControlMap.get(shopFormControlItemPO.getId());
            //判断是否必填
            if (shopFormControlItemPO.getNeedInput() && StringUtils.isEmpty(appointOrderFormItemPO.getAttrValue())) {
                throw new RuntimeException("动态项参数为必填项，请核查！alias = " + appointOrderFormItemPO.getAttrNameAlias());
            }
            if (EncryptTypeEnum.SAFETY_ENCRYPT.getIntValue() == shopFormControlItemPO.getEncryptType().getIntValue()) {
                appointOrderFormItemPO.setAttrValue(localSecurityClient.encrypt(params.get(shopFormControlItemPO.getAlias())));
            }
        });
        //更新条目信息
        appointOrderFormItemDao.batchUpdatedByAppointIdAndAlias(appointOrderFormItemPOList);
    }

    @Override
    public AppointOrderDetailVO fillDetailByCode(String contextId, AppointOrderDetailVO detailVO) {
        fillCityName(detailVO.getCityId(), detailVO);
        fillPackageName(detailVO.getPackageCode(), detailVO);
        fillStoreInfo(contextId, detailVO.getStoreCode(), detailVO);
        return detailVO;
    }

    @Override
    public List<Long> allNewOrderIds() {
        return appointOrderDao.allNewOrderIds();
    }

    @Override
    public AppointOrderPO findOne(long id) {
        return appointOrderDao.findById(id);
    }

    /**
     * 改期
     *
     * @param reschuleDTO
     */
    @Override
    public AppointOrderResult reschdule(ReschuleDTO reschuleDTO) {
        //预约单信息
        AppointOrderDetailVO appointOrderDetailVO = detail(reschuleDTO.getAppointOrderId());
        //改期参数校验
        validReschdule(reschuleDTO, appointOrderDetailVO);
        AppointOrderResult appointOrderResult = new AppointOrderResult();
        //时间无改动则不处理
        if (!isTimeChange(reschuleDTO, appointOrderDetailVO)) {
            appointOrderResult.setOrderId(appointOrderDetailVO.getId());
            appointOrderResult.setAppointStatus(appointOrderDetailVO.getAppointStatus());
            return appointOrderResult;
        }
        appointOrderDetailVO.setAppointStartTime(reschuleDTO.getAppointStartTime());
        appointOrderDetailVO.setAppointEndTime(reschuleDTO.getAppointEndTime());
        appointOrderDetailVO.setPreAppointStatus(appointOrderDetailVO.getAppointStatus());
        //更改前置状态，并插入任务信息
        Long taskId = beginReschdule(appointOrderDetailVO, reschuleDTO);
        OperateResultEnum operateResultEnum = null;
        appointOrderResult.setAppointStatus(AppointStatusEnum.RESCHEDULING.getIntValue());
        try {
            //改期其他操作
            Future<OperateResultEnum> future = threadPoolTaskExecutor.submit(() -> this.venderReschdule(appointOrderDetailVO, taskId));
            operateResultEnum = future.get(3, TimeUnit.SECONDS);
            //埋点
            if (OperateResultEnum.SUCCESS.equals(operateResultEnum)) {
                if(PlatformEnum.TO_C.getIntValue() == reschuleDTO.getPlatformEnum().getIntValue()){
                    reschuleDTO.setUserPin(appointOrderDetailVO.getCustomerUserPin());
                }
                appointOrderResult.setAppointStatus(appointOrderDetailVO.getPreAppointStatus());
                burryTimeChange(reschuleDTO);
                return appointOrderResult;
            }
            //预约失败
            appointOrderResult.setAppointStatus(AppointStatusEnum.APPOINT_FAILURE.getIntValue());
        } catch (TimeoutException e) {
            logger.error("改期请求超时,预约单ID = {}", reschuleDTO.getAppointOrderId(), e);
        } catch (Exception e) {
            logger.error("改期异常，请核查！预约单ID = {}", reschuleDTO.getAppointOrderId(), e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_ORDER_RESCHULE);
            throw new RuntimeException();
        }
        return appointOrderResult;
    }

    /**
     * 校验改期信息
     *
     * @param reschuleDTO
     * @param appointOrderDetailVO
     */
    private void validReschdule(ReschuleDTO reschuleDTO, AppointOrderDetailVO appointOrderDetailVO) {
        if (null == appointOrderDetailVO) {
            throw new IllegalArgumentException("不存在该预约单");
        }
        //预约开始必须大于或当前时间（时间槽模式）
        if (null == reschuleDTO.getAppointEndTime()) {
            Date slotDate = AppointDateUtils.getDate2Date("yyyy-MM-dd", new Date());
            if (reschuleDTO.getAppointStartTime().before(slotDate)) {
                throw new IllegalArgumentException("日期格式，开始日期必须大于等于当前日期");
            }
        }
        //预约开始必须大于或当前时间（时间段模式）
        if (null != reschuleDTO.getAppointEndTime()) {
            Date periodDate = AppointDateUtils.getDate2Date("yyyy-MM-dd hh:mm:ss", new Date());
            if (reschuleDTO.getAppointStartTime().before(periodDate)) {
                throw new IllegalArgumentException("时间段格式，开始时间必须大于当前时间");
            }
        }
        //开始时间不可大于结束时间
        if (null != reschuleDTO.getAppointEndTime() &&
                reschuleDTO.getAppointStartTime().after(reschuleDTO.getAppointEndTime())) {
            throw new IllegalArgumentException("开始时间不可大于结束时间");
        }
        //商家是否一致
        if (PlatformEnum.SHOP.equals(reschuleDTO.getPlatformEnum()) &&
                !reschuleDTO.getVenderId().equals(appointOrderDetailVO.getVenderId())) {
            throw new IllegalArgumentException("商家信息不一致");
        }
        //门店是否一致
        if (PlatformEnum.STORE.equals(reschuleDTO.getPlatformEnum()) &&
                !reschuleDTO.getStoreCode().equals(appointOrderDetailVO.getStoreCode())) {
            throw new IllegalArgumentException("门店信息不一致");
        }
        //C端操作用户验证
        if (PlatformEnum.TO_C.equals(reschuleDTO.getPlatformEnum())) {
            if (!reschuleDTO.getUserPin().equals(appointOrderDetailVO.getCustomerUserPin())) {
                throw new IllegalArgumentException("不可操作其他用户订单");
            }
        }
        if (!AppointStatusEnum.isOperateByPlatform(appointOrderDetailVO.getAppointStatus(),
                reschuleDTO.getPlatformEnum().getIntValue())) {
            throw new IllegalArgumentException("该状态不可改期");
        }
    }

    @Override
    public OperateResultEnum venderReschdule(AppointOrderDetailVO appointOrderDetailVO, Long taskId) {
        //改期其他流程
        OperateResultEnum operateResultEnum = operateService.execute(appointOrderDetailVO, OperateService.OperateEnum.CHANGE_SCHEDULE);
        if (OperateResultEnum.RETRY.equals(operateResultEnum)) {
            return operateResultEnum;
        }
        finishReschdule(appointOrderDetailVO, taskId, operateResultEnum);
        return operateResultEnum;
    }

    /**
     * 更新改期前信息
     */
    public Long beginReschdule(AppointOrderDetailVO appointOrderDetailVO, ReschuleDTO reschuleDTO) {
        TransactionStatus transactionStatus =
                transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        TaskInfoPo taskInfoPo = new TaskInfoPo();
        try {
            //插入任务信息
            taskInfoPo.setTaskStatus(TasksStatusEnum.DEAL_DOING);
            taskInfoPo.setContent(JSON.toJSONString(reschuleDTO));
            taskInfoPo.setFunctionId(TaskTypeEnum.RESCHDULE.toString());
            taskInfoPo.setMaxRetry(3);
            tasksService.insertAndGetId(taskInfoPo);
            //更新预约单状态和前置状态
            AppointOrderPO appointOrderPO = new AppointOrderPO();
            appointOrderPO.setPreAppointStatus(AppointStatusEnum.getFromCode(appointOrderDetailVO.getAppointStatus()));
            appointOrderPO.setAppointStatus(AppointStatusEnum.RESCHEDULING);
            appointOrderPO.setId(appointOrderDetailVO.getId());
            appointOrderDao.updateSelective(appointOrderPO);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            logger.error("更新预约单前置状态异常", e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_ORDER_RESCHULE);
        }
        return taskInfoPo.getId();
    }

    /**
     * 完成改期
     *
     * @param appointOrderDetailVO
     */
    public void finishReschdule(AppointOrderDetailVO appointOrderDetailVO, Long taskId, OperateResultEnum operateResultEnum) {
        TransactionStatus transactionStatus =
                transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            //更新预约单状态
            AppointOrderPO appointOrderPO = new AppointOrderPO();
            appointOrderPO.setId(appointOrderDetailVO.getId());
            if (OperateResultEnum.SUCCESS.equals(operateResultEnum)) {
                appointOrderPO.setAppointStartTime(appointOrderDetailVO.getAppointStartTime());
                appointOrderPO.setAppointEndTime(appointOrderDetailVO.getAppointEndTime());
            }
            appointOrderPO.setAppointStatus(AppointStatusEnum.getFromCode(appointOrderDetailVO.getPreAppointStatus()));
            appointOrderDao.updateSelective(appointOrderPO);
            //更新任务信息
            tasksService.consumerTask(taskId);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            logger.error("更新预约单异常", e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_ORDER_RESCHULE);
        }
    }


    /**
     * 简单导入不做设计了
     *
     * @param lsnInputVO
     */
    @Override
    public void inputLsns(LsnInputVO lsnInputVO) throws InputLnsException {
        List<LsnVO> lsnInputVOS = lsnInputVO.getLsnVos();
        //执行批量导入数据
        for (LsnVO v : lsnInputVOS) {
            this.inputLsns(v, lsnInputVO.getOperateUser());
        }
    }


    @Override
    public List<AppointOrderPO> findCancelingOrder() {
        return appointOrderDao.findCancelingOrder();
    }

    /**
     * 导入物流单
     *
     * @param v
     */
    private void inputLsns(LsnVO v, String operateUser) throws InputLnsException {
        try {
            AppointFinishVO appointFinishVO = new AppointFinishVO();
            appointFinishVO.setAppointOrderId(v.getAppointOrderId());
            appointFinishVO.setOperateUser(operateUser);
            MailInformation mailInformation = new MailInformation();
            mailInformation.setLogisticsNo(v.getLogisticsNo());
            mailInformation.setLogisticsSiteId(v.getLogisticsSiteId());
            mailInformation.setLogisticsSource(v.getLogisticsSource());
            appointFinishVO.setMailInformation(mailInformation);
            FinishAppointDto finishAppointDto = new FinishAppointDto(appointFinishVO);
            //完成预约单
            this.finishAppoint(finishAppointDto, PlatformEnum.SHOP.getIntValue());
        } catch (Exception e) {
            logger.error("批量导入物流单在appointOrderId={}的预约单发生错误。e={}", v.getAppointOrderId(), e);
            throw new InputLnsException(v.getAppointOrderId(), "导入失败，导入任务已停止，请检查后重试！");
        }
    }

    /**
     * 时间改期埋点
     *
     * @param
     */
    private void burryTimeChange(ReschuleDTO reschuleDTO) {
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put("operateUser", reschuleDTO.getUserPin());
        if (null != reschuleDTO.getAppointEndTime()) {
            mapParam.put("appointTime", BuriedPointUtil.getAppointTime(reschuleDTO.getAppointStartTime(), reschuleDTO.getAppointEndTime()));
        } else {
            mapParam.put("appointTime", AppointDateUtils.getDate2Str(START_APPOINT_FORMAT,reschuleDTO.getAppointStartTime()));
        }
        buriedPointService.processBuried(reschuleDTO.getAppointOrderId().toString(), ProcessTypeEnum.CHANGE_SERVICE_TIME.getCode(), mapParam);
    }

    @Transactional
    @Override
    public void dispatchOrder(DispatchOrderVO dispatchOrderVO) {
        //获取服务条目信息
        AppointOrderServiceItemPO appointOrderServiceItemPO = appointOrderServiceItemService.getServiceItemByAppointOrderId(dispatchOrderVO.getAppointOrderId());
        //获取预约单详情
        AppointOrderDetailVO appointOrderDetailVO = detail(dispatchOrderVO.getAppointOrderId());
        //服务人员信息
        ShopStaffPO shopStaffPO = shopStaffService.getStaffById(Long.valueOf(dispatchOrderVO.getStaffCode()));
        //校验派单信息
        validDispatcher(dispatchOrderVO, appointOrderDetailVO, shopStaffPO);
        //执行派单操作
        appointOrderServiceItemPO.setStaffName(shopStaffPO.getServerName());
        appointOrderServiceItemPO.setAppointOrderId(dispatchOrderVO.getAppointOrderId());
        appointOrderServiceItemPO.setStaffUserPin(shopStaffPO.getUserPin());
        appointOrderServiceItemPO.setStaffCode(shopStaffPO.getId().toString());
        appointOrderServiceItemPO.setStaffPhone(shopStaffPO.getServerPhone());
        appointOrderServiceItemDao.update(appointOrderServiceItemPO);
        //修改主订单的状态
        AppointOrderPO appointOrderPo = new AppointOrderPO();
        appointOrderPo.setAppointStatus(AppointStatusEnum.WAIT_SERVICE);
        appointOrderPo.setId(dispatchOrderVO.getAppointOrderId());
        dispatchOrder(appointOrderPo);
        //派单埋点
        appointOrderPo.setBusinessCode(appointOrderDetailVO.getBusinessCode());
        appointOrderPo.setServerType(appointOrderDetailVO.getServerType());
        burryDispatchStaff(dispatchOrderVO, shopStaffPO, appointOrderPo);
    }

    /**
     * 校验派单
     *
     * @param dispatchOrderVO
     * @param appointOrderDetailVO
     * @param shopStaffPO
     * @return
     */
    private void validDispatcher(DispatchOrderVO dispatchOrderVO, AppointOrderDetailVO appointOrderDetailVO, ShopStaffPO shopStaffPO) {
        //服务人员并没有更改
        if (dispatchOrderVO.getStaffCode().toString().equals(appointOrderDetailVO.getStaffCode())) {
            return;
        }
        //是否存在该服务人员
        if (null == shopStaffPO) {
            throw new IllegalArgumentException("不存在该员工，请核查！");
        }
        //是否存在预约单信息
        if (null == appointOrderDetailVO) {
            throw new IllegalArgumentException("派单操作异常，不存在该预约单");
        }
        //商家校验
        if (dispatchOrderVO.getVenderId().equals(appointOrderDetailVO.getVenderId())) {
            throw new IllegalArgumentException("不可派其他商家订单");
        }
        //状态校验
        if (!AppointStatusEnum.WAIT_ORDER.equals(appointOrderDetailVO.getAppointStatus())) {
            throw new IllegalArgumentException("只有待处理状态订单才可以派单");
        }
    }

    /**
     * 更新附属信息
     *
     * @param updateAttachVO
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateAttachInfo(UpdateAttachVO updateAttachVO) {
        //获取预约单信息
        AppointOrderPO appointOrderPO =
                appointOrderDao.findOrderByCondition(updateAttachVO.getAppointOrderId(), updateAttachVO.getBusinessCode(), updateAttachVO.getVenderId());
        //校验附属信息
        validateAttachInfo(updateAttachVO, appointOrderPO);
        //更新附件信息
        if (null != updateAttachVO.getAttachVO()) {
            appointOrderDao.submitAttach(
                    AppointOrderConvert.convertAttachInfo(updateAttachVO), updateAttachVO.getAttachVO().getOverwrite());
        }
        if (null == updateAttachVO.getLogisticVO()) {
            return;
        }
        //根据站点ID获取物流公司名称
        String expressCompany = expressService.getExpressCompanyName(updateAttachVO.getLogisticVO().getLogisticsSiteId());
        AppointOrderDetailVO mailInfo = new AppointOrderDetailVO();
        mailInfo.setId(updateAttachVO.getAppointOrderId());
        mailInfo.setLogisticsNo(updateAttachVO.getLogisticVO().getLogisticsNo());
        mailInfo.setLogisticsSiteId(updateAttachVO.getLogisticVO().getLogisticsSiteId());
        mailInfo.setLogisticsSource(expressCompany);
        if(appointOrderServiceItemService.inputLsns(mailInfo) <= 0){
            return;//未更新物流信息，则不添加变更记录
        }
        //物流变更记录
        Map<String, String> param = new HashMap<>();
        param.put("operateUser", updateAttachVO.getLoginUserPin());
        param.put("logisticsCompany", expressCompany);
        param.put("logisticsNo", updateAttachVO.getLogisticVO().getLogisticsNo());
        buriedPointService.processBuried(appointOrderPO.getId().toString(), ProcessTypeEnum.CHANGE_LOGISTICS.getCode(), param);
    }

    /**
     * 校验附属信息
     *
     * @param updateAttachVO
     */
    public void validateAttachInfo(UpdateAttachVO updateAttachVO, AppointOrderPO appointOrderPO) {
        //校验预约单信息
        if (null == appointOrderPO) {
            throw new IllegalArgumentException("不存在该预约单");
        }
        if (null != updateAttachVO.getAttachVO() && StringUtils.isEmpty(updateAttachVO.getAttachVO().getAttrUrls())) {
            throw new IllegalArgumentException("附件入参不正确");
        }
        if (null == updateAttachVO.getLogisticVO()) {
            return;
        }
        if (StringUtils.isEmpty(updateAttachVO.getLogisticVO().getLogisticsNo()) ||
                null == updateAttachVO.getLogisticVO().getLogisticsSiteId()) {
            throw new IllegalArgumentException("物流信息入参不正确");
        }
        if (!AppointStatusEnum.APPOINT_FINISH.equals(appointOrderPO.getAppointStatus())) {
            throw new IllegalArgumentException("物流信息修改状态必须为已完成");
        }
    }

    @Override
    public List<Long> checkAppointOrder(CheckOrderVO checkOrderVO) {
        if (CollectionUtils.isEmpty(checkOrderVO.getAppointOrderIds())) {
            throw new IllegalArgumentException("审核预约单传入预约单号为空！");
        }
        //批量获取预约单信息
        List<AppointOrderPO> appointOrderPOS = appointOrderDao.getAppointOrders(checkOrderVO.getAppointOrderIds());
        //获取预约单详情
        List<AppointOrderDetailVO> appointOrderDetailVOList = appointDetailList(appointOrderPOS);
        if (CollectionUtils.isEmpty(appointOrderPOS)) {
            throw new IllegalArgumentException("不存在该预约单！");
        }
        List<Long> appointOrders = new ArrayList<>();
        appointOrderDetailVOList.forEach(appointOrderDetailVO -> {
            //校验订单合法性
            if (!validateCheckOrder(checkOrderVO, appointOrderDetailVO)) {
                appointOrders.add(appointOrderDetailVO.getId()); //审核失败
                return;
            }
            //审核预约单(前置状态：待派单)
            AppointOrderPO appointOrderPO = new AppointOrderPO();
            appointOrderPO.setId(appointOrderDetailVO.getId());
            appointOrderPO.setAppointStatus(AppointStatusEnum.getFromCode(AppointStatusEnum.WAIT_SERVICE.getIntValue()));
            if (1 != appointOrderDao.updateAppointStatus(appointOrderPO, AppointStatusEnum.WAIT_ORDER.getIntValue())) {
                appointOrders.add(appointOrderPO.getId()); //审核失败
                return;
            }
            //审核成功发短信
            SmsPointEnum smsPointEnum =
                    appointOrderDetailVO.getServerType() == ServerTypeEnum.DAODIAN.getIntValue() ? SmsPointEnum.DAODIAN_SHENHE : SmsPointEnum.DAOJIA_SHENHE;
            sendMessageService.sendMsg(appointOrderDetailVO.getId(), smsPointEnum);
            //审核记录
            appointOrderPO.setOperateUser(checkOrderVO.getLoginUserPin());
            appointOrderPO.setServerType(appointOrderDetailVO.getServerType());
            appointOrderPO.setBusinessCode(appointOrderDetailVO.getBusinessCode());
            buriedPointService.statusChangeBuried(appointOrderPO, ProcessTypeEnum.CHANGE_APPOINT_STATUS.getCode());
        });
        return appointOrders;
    }

    /**
     * 审核验证
     *
     * @param appointOrderDetailVO
     * @return
     */
    public boolean validateCheckOrder(CheckOrderVO checkOrderVO, AppointOrderDetailVO appointOrderDetailVO) {
        if (null == appointOrderDetailVO) {
            logger.error("预约单详情异常");
            return false;
        }
        //状态不是待处理
        if (AppointStatusEnum.WAIT_ORDER.getIntValue() != appointOrderDetailVO.getAppointStatus()) {
            logger.error("状态不是待处理");
            return false;
        }

        //校验门店Code是否一致
        if (null != checkOrderVO.getStoreCode() &&
                !checkOrderVO.getStoreCode().equals(appointOrderDetailVO.getStoreCode())) {
            logger.error("校验门店Code是否一致");
            return false;
        }
        //校验商家是否一致
        if (null != checkOrderVO.getVenderId() &&
                !checkOrderVO.getVenderId().equals(appointOrderDetailVO.getVenderId())) {
            logger.error("校验商家是否一致");
            return false;
        }
        return true;
    }

    @Override
    public List<AppointOrderPO> getAppointByStatus(Integer appointStatus) {
        return appointOrderDao.getAppointByStatus(appointStatus);
    }


    @Override
    public int updateAppointStatus(AppointOrderPO appointOrderPO, Integer appointStatus) {
        return appointOrderDao.updateAppointStatus(appointOrderPO, appointStatus);
    }

    @Override
    public List<AppointOrderPO> getAppointOrderByUserPin(String userPin, String businessCode) {
        return appointOrderDao.getAppointByUserPin(userPin, businessCode);
    }


    /**
     * 服务人员变更埋点
     *
     * @param shopStaffPO
     */
    private void burryDispatchStaff(DispatchOrderVO dispatchOrderVO, ShopStaffPO shopStaffPO, AppointOrderPO appointOrderPO) {
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put("staffName", shopStaffPO.getServerName());
        mapParam.put("operateUser", dispatchOrderVO.getLoginUserPin());
        buriedPointService.processBuried(dispatchOrderVO.getAppointOrderId().toString(), ProcessTypeEnum.CHANGE_SERVICE_STAFF.getCode(), mapParam);
        //状态变更
        if (AppointStatusEnum.WAIT_ORDER == appointOrderPO.getAppointStatus()) {
            appointOrderPO.setOperateUser(dispatchOrderVO.getLoginUserPin());
            buriedPointService.statusChangeBuried(appointOrderPO, ProcessTypeEnum.CHANGE_APPOINT_STATUS.getCode());
        }
    }


    private void fillStoreInfo(String contextId, String storeCode, AppointOrderDetailVO appointOrderDetailVO) {
        if (Strings.isNullOrEmpty(storeCode)) {
            return;
        }
        StoreItem store = rpcAppointSearchGwService.getStoreItemByStoreCode(storeCode, contextId);
        if (store != null) {
            appointOrderDetailVO.setStoreName(store.getStoreName());
            appointOrderDetailVO.setStoreAddress(store.getStoreAddress());
            appointOrderDetailVO.setStorePhone(store.getStorePhone());
        } else {
            throw new IllegalStateException("storeCode已传递，但是不能获packageName");
        }
    }

    /**
     * @param packageCode
     * @param appointOrderDetailVO
     */
    private void fillPackageName(String packageCode, AppointOrderDetailVO appointOrderDetailVO) {
        if (Strings.isNullOrEmpty(packageCode))
            return;
        ServiceItem serviceItem = rpcAppointSearchGwService.getServiceItemByPackageCode(packageCode);
        if (serviceItem != null && !Strings.isNullOrEmpty(serviceItem.getPackageName())) {
            appointOrderDetailVO.setPackageName(serviceItem.getPackageName());
        } else {
            throw new IllegalStateException("packageCode已传递，但是不能获packageName");
        }
    }

    /**
     * 填充城市
     *
     * @param cityId
     * @param appointOrderDetailVO
     */
    private void fillCityName(String cityId, AppointOrderDetailVO appointOrderDetailVO) {
        if (Strings.isNullOrEmpty(cityId))
            return;
        CityItem city = rpcAppointSearchGwService.getCity(cityId);
        if (city != null && !Strings.isNullOrEmpty(city.getCityName())) {
            appointOrderDetailVO.setCityName(city.getCityName());
        } else {
            throw new IllegalArgumentException("城市Id已传递，但是不能获取城市名称");
        }
    }

    /**
     * 加密敏感信息
     * @param appointOrderDetailVO
     */
    private void encryptInfo(AppointOrderDetailVO appointOrderDetailVO){
        if(com.jd.common.util.StringUtils.isNotBlank(appointOrderDetailVO.getCardNo())){
            String cardNo = appointOrderDetailVO.getCardNo();
            StringBuilder builder = new StringBuilder();
            builder.append(cardNo.substring(0, 1));
            builder.append("******");
            builder.append(cardNo.substring(cardNo.length() - 1, cardNo.length()));
            appointOrderDetailVO.setCardNo(builder.toString());
        }
    }
}
