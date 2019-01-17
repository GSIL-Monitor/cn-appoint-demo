package com.jd.appoint.service.api.outer.schedule;

import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.common.enums.ScheduleModelEnum;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.common.utils.RedisCache;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.enums.TimeUnitEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.ShopWorkTimePO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeItemQuery;
import com.jd.appoint.domain.shop.query.ShopWorkTimeQuery;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.rpc.context.dto.ScheduleContextDTO;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.AppointOrderServiceItemService;
import com.jd.appoint.service.product.ProductService;
import com.jd.appoint.service.shop.ShopWorkTimeItemService;
import com.jd.appoint.service.shop.ShopWorkTimeService;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.common.util.StringUtils;
import com.jd.fastjson.JSON;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.virtual.appoint.ScheduleGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.enums.StatusCode;
import com.jd.virtual.appoint.schedule.DateItem;
import com.jd.virtual.appoint.schedule.Schedule;
import com.jd.virtual.appoint.schedule.TimeItem;
import com.jd.virtual.appoint.schedule.UpdateScheduleRequest;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by luqiang3 on 2018/5/14.
 */
@Service("scheduleGwService")
public class ScheduleGwServiceImpl implements ScheduleGwService{

    private static Logger logger = LoggerFactory.getLogger(ScheduleGwServiceImpl.class);

    @Autowired
    private ShopWorkTimeService shopWorkTimeService;
    @Autowired
    private ShopWorkTimeItemService shopWorkTimeItemService;
    @Resource
    private RedisCache redisCache;
    @Autowired
    private RpcContextService rpcContextService;
    @Autowired
    private VenderConfigService venderConfigService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AppointOrderService appointOrderService;
    @Autowired
    private AppointOrderServiceItemService appointOrderServiceItemService;

    /**
     * 默认库存
     */
    private static Integer DEFAULT_STOCK = 9999;

    @Override
    @UmpMonitor(serverType = ServerEnum.NONE, logCollector =
    @LogCollector(description = "查询产能日历", classify = ScheduleGwServiceImpl.class))
    public CommonResponse<Schedule> getScheduleList(CommonRequest request) {
        ScheduleContextDTO context = rpcContextService.getScheduleContextDTO(request.getContextId());
        return getScheduleList(context);
    }

    @Override
    @UmpMonitor(serverType = ServerEnum.NONE, logCollector =
    @LogCollector(description = "改期查询产能日历", classify = ScheduleGwServiceImpl.class))
    public CommonResponse<Schedule> getScheduleListByOrder(UpdateScheduleRequest request) {
        if(!checkParam(request)){
            return new CommonResponse<>(StatusCode.PARAM_ERROR, Boolean.FALSE);
        }
        AppointOrderPO appointOrder = appointOrderService.getAppointOrder(request.getOrderId());
        AppointOrderServiceItemPO appointService = appointOrderServiceItemService.getServiceItemByAppointOrderId(request.getOrderId());
        appointOrder.setStoreCode(appointService.getStoreCode());
        if(null == appointOrder){
            logger.info("改期查询产能日历-未找到订单，request={}", JSON.toJSONString(request));
            return new CommonResponse<>(StatusCode.PARAM_ERROR, Boolean.FALSE);
        }
        if(StringUtils.isNotBlank(request.getUserPin()) &&
                !request.getUserPin().equals(appointOrder.getCustomerUserPin())){
            logger.info("改期查询产能日历-用户非法，request={}", JSON.toJSONString(request));
            return new CommonResponse<>(StatusCode.PARAM_ERROR, Boolean.FALSE);
        }
        ScheduleContextDTO context = this.convert(appointOrder, request);
        return getScheduleList(context);
    }

    /**
     * 获取产能日历通用方法
     * @param context
     * @return
     */
    private CommonResponse<Schedule> getScheduleList(ScheduleContextDTO context){
        if(!volidateParams(context)){
            logger.error("产能日历-参数错误：context={}", context.toString());
            return new CommonResponse<>(StatusCode.PARAM_ERROR, Boolean.FALSE);
        }
        if(!validateProductStatus(context)){
            logger.info("产能日历-产品状态不可用：context={}", context.toString());
            return new CommonResponse<>(StatusCode.PRODUCT_CAN_NOT_APPOINT, Boolean.FALSE);
        }
        ShopWorkTimePO shopWorkTimePO = getShopWorkTime(context);
        if(null == shopWorkTimePO){
            logger.info("产能日历-未配置服务时间：context={}", context.toString());
            StatusCode statusCode = StatusCode.STORE_ALREADY_FULL;
            if(context.getStoreCode().equals(DefaultVenderConfig.STORE_CODE)){
                //默认门店编号表示为商家
                statusCode = StatusCode.VENDER_ALREADY_FULL;
            }
            return new CommonResponse<>(statusCode, Boolean.FALSE);
        }
        context.initStartAndEndDate(shopWorkTimePO);
        context.initDateList();
        List<ScheduleWorkTime> scheduleWorkTimes = getScheduleWorkTimeFromCaches(context);//获得服务时间
        Map<String, Integer> stockMap = getStock(context);//获得库存信息

        //服务时间&库存绑定
        Schedule schedule = new Schedule();
        List<DateItem> dateItems = new ArrayList<>();
        for(ScheduleWorkTime workTime : scheduleWorkTimes){
            DateItem dateItem = new DateItem();
            dateItem.setDate(workTime.getDate());
            if(workTime.getDateStatus()){//该日期有效
                dateItem.setRemindAppointNum(stockMap.get(workTime.getDate()));
            }
            dateItems.add(dateItem);
        }
        schedule.setDateItems(dateItems);

        Date currentDate = AppointDateUtils.getDate2Date("yyyy-MM-dd", new Date());
        //可选日期范围
        List<String> dateRange = new ArrayList<>();
        dateRange.add(AppointDateUtils.getDate2Str("yyyy-MM-dd", AppointDateUtils.addDays(currentDate, shopWorkTimePO.getStartDay())));
        dateRange.add(AppointDateUtils.getDate2Str("yyyy-MM-dd", AppointDateUtils.addDays(currentDate, shopWorkTimePO.getEndDay())));
        schedule.setDateRange(dateRange);

        //时间槽模式
        if((TimeShowTypeEnum.RANGE == shopWorkTimePO.getTimeShowType()
                || TimeShowTypeEnum.POINT == shopWorkTimePO.getTimeShowType())){
            List<TimeRange> timeRanges = scheduleWorkTimes.get(0).getTimeRanges();
            if(AppointDateUtils.isSameDate(currentDate, context.getStartDate())){//查询当天，需过滤
                filterTimeRange(timeRanges, shopWorkTimePO);
            }
            List<TimeItem> timeItems = getTimeItems(timeRanges, shopWorkTimePO);
            Map<String, List<TimeItem>> timeItemMap = new HashMap<>();
            timeItemMap.put(scheduleWorkTimes.get(0).getDate(), timeItems);
            schedule.setTimeItems(timeItemMap);
            //获取休息日期
            schedule.setExcludeDates(getExcludeDates(dateRange, shopWorkTimePO.getId()));
        }

        CommonResponse commonResponse = new CommonResponse<>();
        commonResponse.setResult(schedule);
        return commonResponse;
    }

    /**
     * 获得服务时间
     * @param context
     * @return
     */
    private ShopWorkTimePO getShopWorkTime(ScheduleContextDTO context){
        String key = CacheUtils.getShopWorkTimeKey(context.getBusinessCode(), context.getVenderId(), context.getStoreCode(), context.getSkuId());
        return CacheUtils.getObjectFromCacheOrDB(ShopWorkTimePO.class, () -> {
            return redisCache.get(key);
        }, () -> {
            ShopWorkTimeQuery query = new ShopWorkTimeQuery();
            query.setBusinessCode(context.getBusinessCode());
            query.setVenderId(context.getVenderId());
            query.setStoreCode(context.getStoreCode());
            query.setSkuId(context.getSkuId());
            ShopWorkTimePO shopWorkTimePO = shopWorkTimeService.queryShopWorkTime(query);
            if (null != shopWorkTimePO) {
                return shopWorkTimePO;
            }
            logger.info("产能日历-从数据库未获得服务时间，context={}", context.toString());
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.QUERY_SHOP_WORK_TIME_FROM_DB);
            return null;
        }, (v) -> {
            redisCache.set(key, v);
        });
    }

    /**
     * 获得服务时间项
     * @param shopWorkTimeId
     * @return
     */
    private List<ShopWorkTimeItemPO> getShopWorkTimeItems(Long shopWorkTimeId){
        String key = CacheUtils.getShopWorkTimeItemKey(shopWorkTimeId);
        List<ShopWorkTimeItemPO> itemPOs = redisCache.get(key, List.class);
        if(CollectionUtils.isNotEmpty(itemPOs)){
            return itemPOs;
        }
        ShopWorkTimeItemQuery query = new ShopWorkTimeItemQuery();
        query.setShopWorkTimeId(shopWorkTimeId);
        List<ShopWorkTimeItemPO> dbItemPOs = shopWorkTimeItemService.queryShopWorkTimeItems(query);
        if(CollectionUtils.isEmpty(dbItemPOs)){
            logger.error("从数据库未获得服务时间项，shopWorkTimeId={}", shopWorkTimeId);
            return null;
        }
        redisCache.setObject(key, dbItemPOs);
        return dbItemPOs;
    }

    /**
     * 校验产品的状态
     * @param context
     * @return true【可用】false【不可用】
     */
    private Boolean validateProductStatus(ScheduleContextDTO context){
        List<ProductPO> productPOs = productService.queryByVenderStoreSkuId(context.getVenderId(), Long.parseLong(context.getStoreCode()), context.getSkuId());
        if(CollectionUtils.isEmpty(productPOs)){
            return Boolean.TRUE;
        }
        Boolean flag = false;
        for(ProductPO productPO : productPOs){
            if(productPO.getStatus() == StatusEnum.ENABLE){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 从缓存中获得产能日历服务时间
     * @param context
     * @return
     */
    private List<ScheduleWorkTime> getScheduleWorkTimeFromCaches(ScheduleContextDTO context){
        String key = CacheUtils.getScheduleWorkTimeKey(context.getBusinessCode(), context.getVenderId(), context.getStoreCode(), context.getSkuId());
        List<String> workTimeStrs = redisCache.hMGet(key, context.getDateList());
        List<ScheduleWorkTime> scheduleWorkTimes = new ArrayList<>();
        for(String workTimeStr : workTimeStrs){
            if(StringUtils.isNotBlank(workTimeStr)){
                scheduleWorkTimes.add(JSON.parseObject(workTimeStr, ScheduleWorkTime.class));
            }
        }
        if(scheduleWorkTimes.size() != 0){
            return scheduleWorkTimes;
        }
        //从数据库加载
        scheduleWorkTimes = getScheduleWorkTimeFromDbs(context);
        if(CollectionUtils.isEmpty(scheduleWorkTimes)){
            return null;
        }
        List<ScheduleWorkTime> result = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for(ScheduleWorkTime scheduleWorkTime : scheduleWorkTimes){
            map.put(scheduleWorkTime.getDate(), JSON.toJSONString(scheduleWorkTime));
            Date date = AppointDateUtils.getStrToDate("yyyy-MM-dd", scheduleWorkTime.getDate());
            if(AppointDateUtils.daysBetweenTwoDate(context.getStartDate(), date) >= 0
                    && AppointDateUtils.daysBetweenTwoDate(context.getEndDate(), date) <= 0){
                result.add(scheduleWorkTime);
            }
        }
        redisCache.hMSet(key, CacheUtils.timeOutIntraday(new Date()), map);
        return result;
    }

    /**
     * 从数据库获得产能日历服务时间
     * @param context
     * @return
     */
    private List<ScheduleWorkTime> getScheduleWorkTimeFromDbs(ScheduleContextDTO context){
        ShopWorkTimePO shopWorkTimePO = getShopWorkTime(context);
        if(null == shopWorkTimePO){
            logger.info("从数据库中未查询到服务时间信息，context={}", context.toString());
            return null;
        }
        ShopWorkTimeItemQuery shopWorkTimeItemQuery = new ShopWorkTimeItemQuery();
        shopWorkTimeItemQuery.setShopWorkTimeId(shopWorkTimePO.getId());
        List<ShopWorkTimeItemPO> shopWorkTimeItemPOs = shopWorkTimeItemService.queryShopWorkTimeItems(shopWorkTimeItemQuery);
        return toScheduleWorkTimes(shopWorkTimePO, shopWorkTimeItemPOs);
    }

    /**
     * 获得库存
     * @param context
     * @return
     */
    private Map<String, Integer> getStock(ScheduleContextDTO context){
        Map<String, Integer> map = new HashMap<>();
        Boolean stockConfig;
        try {
            stockConfig = venderConfigService.getStockConfig(context.getBusinessCode());
        }catch (Exception e){
            logger.error("产能日历查询库存配置异常：", e);
            return map;//直接返回，没有库存页面显示不可预约
        }
        String key = CacheUtils.getScheduleStockKey(context.getBusinessCode(), context.getVenderId(), context.getStoreCode(), context.getSkuId());
        List<String> remindStocks = redisCache.hMGet(key, context.getDateList());//获得剩余库存数
        for(int i = 0; i < context.getDateList().length; i++){
            Integer remindStock = null;
            if(!stockConfig){//如果该业务线不设置库存，则使用默认库存
                remindStock = DEFAULT_STOCK;
            } else if(null != remindStocks.get(i)){
                remindStock = Integer.parseInt(remindStocks.get(i));
            }else {
                //业务线需要设置库存，但是没有设置，页面显示不可预约
            }
            map.put(context.getDateList()[i], remindStock);
        }
        return map;
    }

    /**
     * 参数校验
     * 校验通过返回true 不通过返回false
     * @param context
     * @return
     */
    private boolean volidateParams(ScheduleContextDTO context){
        if(StringUtils.isBlank(context.getBusinessCode())
                || null == context.getVenderId()){
            return Boolean.FALSE;
        }
        ScheduleModelEnum scheduleModel = venderConfigService.getScheduleModel(context.getBusinessCode());
        if(ScheduleModelEnum.VENDER == scheduleModel){
            context.setSkuId(DefaultVenderConfig.SKU_ID);
        }else if (ScheduleModelEnum.SKU == scheduleModel && null == context.getSkuId()){
            logger.error("获取产能日历，未传skuId，context={}", JSON.toJSONString(context));
            return Boolean.FALSE;
        }
        if(StringUtils.isBlank(context.getStoreCode())){
            context.setStoreCode(DefaultVenderConfig.STORE_CODE);
        }
        return Boolean.TRUE;
    }

    /**
     * 隐藏时间项
     * eg：
     * a.小于当前时间的时间项。当前时间为10:00，则09:00-10:00需要隐藏；
     * b.小于当天T0提前时间的时间项。当前时间为10:00，T0提前时间为120min，则12:00之前的时间项需隐藏
     * 隐藏的逻辑，是把时间项的可预约人数置为0，即不可预约
     */
    private void filterTimeRange(List<TimeRange> timeRanges, ShopWorkTimePO shopWorkTimePO){
        if(CollectionUtils.isEmpty(timeRanges)){
            return;
        }
        Integer currentTime = AppointDateUtils.hour2Minute(AppointDateUtils.getDate2Str("HH:mm", new Date()));//当前时间，单位分
        Integer t0Advance = getMinuteByUnit(shopWorkTimePO.getT0Advance(), shopWorkTimePO.getT0AdvanceUnit());//当天提前预约时间，单位分
        Integer minTime = currentTime + t0Advance;//最小可选时间
        Iterator iterator = timeRanges.iterator();
        while (iterator.hasNext()){
            TimeRange timeRange = (TimeRange)iterator.next();
            //endTime小于等于minTime的时间项，需要去掉
            if(timeRange.getEnd() <= minTime){
                iterator.remove();
            }else {
                break;
            }
        }
    }

    /**
     * 获得时间槽
     * @param timeRanges
     * @param shopWorkTimePO
     * @return
     */
    private List<TimeItem> getTimeItems(List<TimeRange> timeRanges, ShopWorkTimePO shopWorkTimePO){
        if(CollectionUtils.isEmpty(timeRanges)){
            return null;
        }
        List<TimeItem> timeItems = new ArrayList<>();
        for(TimeRange timeRange : timeRanges){
            //根据时间项范围、时间间隔、显示方式等，将配置的时间解析为前端需要的时间格式
            TimeItem timeItem = new TimeItem();
            timeItem.setStartTime(AppointDateUtils.minute2Hour(timeRange.getStart()));
            if(TimeShowTypeEnum.RANGE == shopWorkTimePO.getTimeShowType()){
                timeItem.setEndTime(AppointDateUtils.minute2Hour(timeRange.getEnd()));
            }
            timeItem.setRemindAppointNum(DEFAULT_STOCK);
            timeItems.add(timeItem);
        }
        return timeItems;
    }

    /**
     * 服务时间处理，将数据库存储的结构转换为前端需要的数据结构
     * @param shopWorkTimePO
     * @param shopWorkTimeItemPOs
     * @return
     */
    public List<ScheduleWorkTime> toScheduleWorkTimes(ShopWorkTimePO shopWorkTimePO, List<ShopWorkTimeItemPO> shopWorkTimeItemPOs){
        //将时间项放到itemMap中，key为周几，便于后面StartDay-EndDay循环时，通过天对应周几，直接取出当天配置的工作、休息开始结束时间
        Map<Integer, ShopWorkTimeItemPO> itemMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(shopWorkTimeItemPOs)){
            for(ShopWorkTimeItemPO shopWorkTimeItemPO : shopWorkTimeItemPOs){
                itemMap.put(shopWorkTimeItemPO.getWeekday(), shopWorkTimeItemPO);
            }
        }
        List<ScheduleWorkTime> scheduleWorkTimes = new ArrayList<>();
        Date currentDate = new Date();//当前日期，基于该日期+TN得到具体日期
        for(int i = shopWorkTimePO.getStartDay(); i <= shopWorkTimePO.getEndDay(); i++){
            ScheduleWorkTime scheduleWorkTime = new ScheduleWorkTime();
            String date = AppointDateUtils.getDate2Str("yyyy-MM-dd", AppointDateUtils.addDays(currentDate, i));
            scheduleWorkTime.setDate(date);
            ShopWorkTimeItemPO shopWorkTimeItemPO = itemMap.get(AppointDateUtils.day2Week(date));
            if(null == shopWorkTimeItemPO || StatusEnum.ENABLE == shopWorkTimeItemPO.getStatus()){
                scheduleWorkTime.setDateStatus(Boolean.TRUE);
                List<TimeRange> timeRanges = timeSplitByInterval(timeSplitByWorkAndRest(shopWorkTimeItemPO), shopWorkTimePO);
                scheduleWorkTime.setTimeRanges(timeRanges);
            }else {
                scheduleWorkTime.setDateStatus(Boolean.FALSE);//该日期不可预约
            }
            scheduleWorkTimes.add(scheduleWorkTime);
        }
        return scheduleWorkTimes;
    }

    /**
     * 按工作时间和休息时间，分隔时间区间
     * 目前只支持一个休息时间，如果以后有多个休息时间，则重写该方法逻辑
     * @param item
     * @return
     */
    private List<TimeRange> timeSplitByWorkAndRest(ShopWorkTimeItemPO item){
        if(StringUtils.isBlank(item.getWorkStart()) || StringUtils.isBlank(item.getWorkEnd())){
            return null;
        }
        //将时间由字符转换为分钟 start  eg: "02:30" -> 150 min
        Integer workStart = AppointDateUtils.hour2Minute(item.getWorkStart());
        Integer workEnd = AppointDateUtils.hour2Minute(item.getWorkEnd());
        Integer restStart =AppointDateUtils.hour2Minute(item.getRestStart());
        Integer restEnd = AppointDateUtils.hour2Minute(item.getRestEnd());
        //将时间由字符转换为分钟 end
        TimeRange oneRange = new TimeRange();
        TimeRange twoRange = new TimeRange();
        List<TimeRange> items = new ArrayList<>();
        if(null != restStart && null != restEnd){
            //如果有休息时间，则时间区间切分为：workStart - restStart;restEnd - workEnd
            oneRange.setStart(workStart);
            oneRange.setEnd(restStart);
            twoRange.setStart(restEnd);
            twoRange.setEnd(workEnd);
            items.add(oneRange);
            items.add(twoRange);
        }else {
            //如果没有休息时间，则时间区间切分为：workStart - workEnd
            oneRange.setStart(workStart);
            oneRange.setEnd(workEnd);
            items.add(oneRange);
        }
        return items;
    }

    /**
     * 通过间隔时间切分时间槽
     * @param timeRanges
     */
    private List<TimeRange> timeSplitByInterval(List<TimeRange> timeRanges, ShopWorkTimePO shopWorkTimePO){
        if(CollectionUtils.isEmpty(timeRanges)){
            return null;
        }
        List<TimeRange> timeRangeResult = new ArrayList<>();
        int interval = getMinuteByUnit(shopWorkTimePO.getTimeInterval(), shopWorkTimePO.getTimeIntervalUnit());
        for(TimeRange timeRange : timeRanges){
            for(int i = timeRange.getStart(); i <= timeRange.getEnd(); ){
                TimeRange timeItem = new TimeRange();
                timeItem.setStart(i);
                i += interval;//开始时间加上间隔时间得到结束时间
                timeItem.setEnd(i);
                if(i > timeRange.getEnd()){
                    //切的时间段小于间隔时间时，前端不显示
                    break;
                }
                timeRangeResult.add(timeItem);
            }
        }
        return timeRangeResult;
    }

    /**
     * 时间+时间单位 获得 对应的分钟数
     * 目前只支持分钟和小时
     * @param time
     * @param timeUnit
     * @return
     */
    public int getMinuteByUnit(Integer time, TimeUnitEnum timeUnit){
        if(TimeUnitEnum.HOUR == timeUnit){
            return time * 60;
        }
        return time;
    }

    /**
     * 改期查询产能日历参数转换
     * @param appointOrderPO
     * @return
     */
    private ScheduleContextDTO convert(AppointOrderPO appointOrderPO, UpdateScheduleRequest request){
        ScheduleContextDTO dto = new ScheduleContextDTO();
        dto.setBusinessCode(appointOrderPO.getBusinessCode());
        dto.setVenderId(appointOrderPO.getVenderId());
        if(StringUtils.isNotBlank(request.getStartDate())){
            dto.setStartDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", request.getStartDate()));
        }
        if(StringUtils.isNotBlank(request.getEndDate())){
            dto.setEndDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", request.getEndDate()));
        }
        dto.setSkuId(appointOrderPO.getSkuId());
        dto.setStoreCode(appointOrderPO.getStoreCode());
        return dto;
    }

    /**
     * 改期查询产能日历参数校验
     * @param request
     * @return
     */
    private Boolean checkParam(UpdateScheduleRequest request){
        if(null == request.getOrderId()){
            logger.info("改期查询产能日历orderId is null，requert={}", JSON.toJSONString(request));
            return Boolean.FALSE;
        }
        if(null == request.getVenderId() && StringUtils.isBlank(request.getUserPin())){
            logger.info("改期查询产能日历venderId or userPin is null，requert={}", JSON.toJSONString(request));
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 获取不可选日期
     * @param dateRange
     * @param shopWorkTimeId
     * @return
     */
    private List<String> getExcludeDates(List<String> dateRange, Long shopWorkTimeId){
        List<String> excludeDates = new ArrayList<>();
        List<ShopWorkTimeItemPO> shopWorkTimeItems = getShopWorkTimeItems(shopWorkTimeId);
        if(CollectionUtils.isEmpty(shopWorkTimeItems)){
            return excludeDates;
        }
        Set<Integer> weekdays = new HashSet<>();
        for(ShopWorkTimeItemPO item : shopWorkTimeItems){
            if(StatusEnum.DISABLE == item.getStatus()){
                weekdays.add(item.getWeekday());
            }
        }
        if(weekdays.size() == 0){
            return excludeDates;
        }
        Date start = AppointDateUtils.getStrToDate("yyyy-MM-dd", dateRange.get(0));
        Date end = AppointDateUtils.getStrToDate("yyyy-MM-dd", dateRange.get(1));
        int days =  AppointDateUtils.daysBetweenTwoDate(start, end);
        for(int i = 0; i < days; i++){
            Date day = AppointDateUtils.addDays(start, i);
            int week = AppointDateUtils.day2Week(day);
            if(weekdays.contains(week)){
                excludeDates.add(AppointDateUtils.getDate2Str("yyyy-MM-dd", day));
            }
        }
        return excludeDates;
    }
}
