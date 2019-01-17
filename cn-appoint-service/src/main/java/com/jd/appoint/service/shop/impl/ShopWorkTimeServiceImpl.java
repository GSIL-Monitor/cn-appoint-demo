package com.jd.appoint.service.shop.impl;

import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.common.enums.SoaCodeEnum;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.dao.shop.ShopWorkTimeDao;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.ShopWorkTimePO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeItemQuery;
import com.jd.appoint.domain.shop.query.ShopWorkTimeQuery;
import com.jd.appoint.service.api.convert.WorkTimeConvert;
import com.jd.appoint.service.api.convert.WorkTimeItemConvert;
import com.jd.appoint.service.shop.ShopWorkTimeItemService;
import com.jd.appoint.service.shop.ShopWorkTimeService;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeItem;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.jim.cli.Cluster;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.xn.slog.LogSecurity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/5.
 */
@Service("shopWorkTimeService")
public class ShopWorkTimeServiceImpl implements ShopWorkTimeService {

    private static final Logger logger = LoggerFactory.getLogger(ShopWorkTimeServiceImpl.class);

    @Autowired
    private ShopWorkTimeDao shopWorkTimeDao;
    @Autowired
    private ShopWorkTimeItemService shopWorkTimeItemService;
    @Resource(name = "jimClient")
    private Cluster jimClient;
    @Autowired
    private VenderConfigService venderConfigService;

    /**
     * 添加店铺服务时间
     *
     * @param shopWorkTimePO
     */
    @Override
    public int insertShopWorkTime(ShopWorkTimePO shopWorkTimePO) {
        return shopWorkTimeDao.insert(shopWorkTimePO);
    }

    /**
     * 添加店铺服务时间及明细
     *
     * @param shopWorkTimePO
     * @param shopWorkTimeItemPOs
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean insertShopWorkTimeAndItem(ShopWorkTimePO shopWorkTimePO, List<ShopWorkTimeItemPO> shopWorkTimeItemPOs) {
        shopWorkTimeDao.insert(shopWorkTimePO);
        if(CollectionUtils.isNotEmpty(shopWorkTimeItemPOs)){
            for(ShopWorkTimeItemPO item : shopWorkTimeItemPOs){
                item.setShopWorkTimeId(shopWorkTimePO.getId());
                shopWorkTimeItemService.insertShopWorkTimeItem(item);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 服务时间查询
     *
     * @param shopWorkTimeQuery
     * @return
     */
    @Override
    public ShopWorkTimePO queryShopWorkTime(ShopWorkTimeQuery shopWorkTimeQuery) {
        return shopWorkTimeDao.queryShopWorkTime(shopWorkTimeQuery);
    }

    /**
     * 更新服务时间
     *
     * @param shopWorkTimePO
     * @return
     */
    @Override
    public int updateShopWorkTime(ShopWorkTimePO shopWorkTimePO) {
        return shopWorkTimeDao.updateShopWorkTime(shopWorkTimePO);
    }

    /**
     * 通过主键分别更新服务时间和服务时间项表
     * 更新成功后，删除缓存信息
     * @param shopWorkTimePO
     * @param shopWorkTimeItemPOs
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateShopWorkTimeAndItemById(ShopWorkTimePO shopWorkTimePO, List<ShopWorkTimeItemPO> shopWorkTimeItemPOs) {
        shopWorkTimeDao.updateByIdAndVenderId(shopWorkTimePO);
        if(CollectionUtils.isNotEmpty(shopWorkTimeItemPOs)){
            for(ShopWorkTimeItemPO item : shopWorkTimeItemPOs){
                shopWorkTimeItemService.updateById(item);
            }
        }
        //更新成功，删除缓存信息
        String businessCode = shopWorkTimePO.getBusinessCode();
        Long venderId = shopWorkTimePO.getVenderId();
        String storeCode = shopWorkTimePO.getStoreCode();
        Long skuId = shopWorkTimePO.getSkuId();
        String key1 = CacheUtils.getScheduleWorkTimeKey(businessCode, venderId, storeCode, skuId);
        String key2 = CacheUtils.getShopWorkTimeKey(businessCode, venderId, storeCode, skuId);
        jimClient.del(key1);
        jimClient.del(key2);
        logger.info("SHOP端编辑服务时间删除缓存信息，key1={},key2={}", key1, key2);
        return Boolean.TRUE;
    }

    /**
     * 保存服务时间
     *
     * @param workTime
     * @return
     */
    @Override
    public SoaResponse saveTime(WorkTime workTime) {
        if(TimeShowTypeEnum.DAY.getIntValue() != workTime.getTimeShowType() && !validateT0Advance(workTime)){
            logger.error("保存服务时间当天提前预约时间为空，workTime={}", LogSecurity.toJSONString(workTime));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "t0Advance is null");
        }
        //校验工作开始时间小于等于结束时间
        if(TimeShowTypeEnum.DAY.getIntValue() != workTime.getTimeShowType() && !validateWorkStartAndEnd(workTime.getWorkTimeItems())){
            logger.error("保存服务时间工作开始时间大于结束时间，workTime={}", LogSecurity.toJSONString(workTime));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "workEnd lt workStart");
        }
        //查询是否配置过服务时间
        ShopWorkTimeQuery query = new ShopWorkTimeQuery();
        query.setBusinessCode(workTime.getBusinessCode());
        query.setVenderId(workTime.getVenderId());
        query.setStoreCode(workTime.getStoreCode());
        query.setSkuId(null == workTime.getSkuId() ? DefaultVenderConfig.SKU_ID : workTime.getSkuId());
        ShopWorkTimePO result = this.queryShopWorkTime(query);
        if (null != result) {
            logger.info("保存服务时间重复添加，workTime={}", LogSecurity.toJSONString(workTime));
            return new SoaResponse(SoaCodeEnum.DATA_DUPLICATED);
        }
        TimeShowTypeEnum timeShowType = venderConfigService.getTimeShowType(workTime.getBusinessCode());
        ShopWorkTimePO shopWorkTimePO = WorkTimeConvert.toShopWorkTimePO(workTime);
        List<ShopWorkTimeItemPO> shopWorkTimeItemPOs = WorkTimeItemConvert.toShopWorkTimeItemPOs(workTime.getWorkTimeItems(), timeShowType);
        //保存到数据库，新增数据不刷新到缓存，由第一次用户查询触发添加到缓存
        this.insertShopWorkTimeAndItem(shopWorkTimePO, shopWorkTimeItemPOs);
        return new SoaResponse();
    }

    /**
     * 校验当天提前预约时间
     * 正确返回true 错误返回false
     * @param workTime
     * @return
     */
    private boolean validateT0Advance(WorkTime workTime){
        if(workTime.getStartDay() == 0){
            return null != workTime.getT0Advance() && workTime.getT0Advance() >= 0;
        }
        return Boolean.TRUE;
    }

    /**
     * 校验工作开始时间是否小于等于结束时间
     * 开始时间 <= 结束时间 返回true 否则返回 false
     * @param workTimeItems
     * @return
     */
    private boolean validateWorkStartAndEnd(List<WorkTimeItem> workTimeItems){
        for(WorkTimeItem workTimeItem : workTimeItems){
            if(!validateWorkStartAndEnd(workTimeItem)){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 校验工作开始时间是否小于等于结束时间
     * 开始时间 <= 结束时间 返回true 否则返回 false
     * @param workTimeItem
     * @return
     */
    private boolean validateWorkStartAndEnd(WorkTimeItem workTimeItem){
        int start = AppointDateUtils.hour2Minute(workTimeItem.getWorkStart());
        int end = AppointDateUtils.hour2Minute(workTimeItem.getWorkEnd());
        if(start > end){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 查询服务时间
     *
     * @param workTimeQuery
     * @return
     */
    @Override
    public SoaResponse<WorkTime> searchTime(WorkTimeQuery workTimeQuery) {
        //查询服务时间信息
        ShopWorkTimeQuery query = WorkTimeConvert.workTimeQuery2ShopWorkTimeQuery(workTimeQuery);
        ShopWorkTimePO shopWorkTimePO = this.queryShopWorkTime(query);
        if (null == shopWorkTimePO) {
            //查询结果不存在，直接返回
            return new SoaResponse<>();
        }
        WorkTime workTime = WorkTimeConvert.toWorkTime(shopWorkTimePO);
        //查询服务时间项信息
        ShopWorkTimeItemQuery itemQuery = new ShopWorkTimeItemQuery();
        itemQuery.setShopWorkTimeId(shopWorkTimePO.getId());
        List<ShopWorkTimeItemPO> shopWorkTimeItemPOs = shopWorkTimeItemService.queryShopWorkTimeItems(itemQuery);
        List<WorkTimeItem> workTimeItems = WorkTimeItemConvert.toWorkTimeItems(shopWorkTimeItemPOs);
        workTime.setWorkTimeItems(workTimeItems);
        return new SoaResponse<>(workTime);
    }

    /**
     * 编辑服务时间
     *
     * @param workTime
     * @return
     */
    @Override
    public SoaResponse editTime(WorkTime workTime) {
        if(TimeShowTypeEnum.DAY.getIntValue() != workTime.getTimeShowType() && !validateT0Advance(workTime)){
            logger.error("保存服务时间当天提前预约时间为空，workTime={}", LogSecurity.toJSONString(workTime));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "t0Advance is null");
        }
        if (null == workTime.getId()) {
            logger.error("编辑服务时间workTime.id is null，workTime={}", LogSecurity.toJSONString(workTime));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "workTime.id is null");
        }
        if(CollectionUtils.isNotEmpty(workTime.getWorkTimeItems())){
            for (WorkTimeItem workTimeItem : workTime.getWorkTimeItems()) {
                if (null == workTimeItem.getId()) {
                    logger.error("编辑服务时间workTimeItem.id is null，workTime={}", LogSecurity.toJSONString(workTime));
                    return new SoaResponse(SoaError.PARAMS_EXCEPTION, "workTimeItem.id is null");
                }
                //校验工作开始时间小于等于结束时间
                if(TimeShowTypeEnum.DAY.getIntValue() != workTime.getTimeShowType() && !validateWorkStartAndEnd(workTimeItem)){
                    logger.error("编辑服务时间工作开始时间小于结束时间，workTime={}", LogSecurity.toJSONString(workTime));
                    return new SoaResponse(SoaError.PARAMS_EXCEPTION, "workEnd lt workStart");
                }
            }
        }
        TimeShowTypeEnum timeShowType = venderConfigService.getTimeShowType(workTime.getBusinessCode());
        ShopWorkTimePO shopWorkTimePO = WorkTimeConvert.toShopWorkTimePO(workTime);
        List<ShopWorkTimeItemPO> shopWorkTimeItemPOs = WorkTimeItemConvert.toShopWorkTimeItemPOs(workTime.getWorkTimeItems(), timeShowType);
        //更新数据库&删除对应缓存信息
        Boolean result = this.updateShopWorkTimeAndItemById(shopWorkTimePO, shopWorkTimeItemPOs);
        if (!result) {
            logger.info("编辑服务时间失败，workTime={}", LogSecurity.toJSONString(workTime));
            return new SoaResponse(SoaError.SERVER_EXCEPTION);
        }
        return new SoaResponse();
    }
}
