package com.jd.appoint.service.stock.impl;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.common.enums.ScheduleModelEnum;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.common.utils.RedisCache;
import com.jd.appoint.dao.stock.StockInfoDao;
import com.jd.appoint.dao.stock.StockOperateDao;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.StockOptStatusEnum;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.domain.stock.Stock;
import com.jd.appoint.domain.stock.StockInfoPO;
import com.jd.appoint.domain.stock.StockOperatePO;
import com.jd.appoint.domain.stock.query.StockInfoQuery;
import com.jd.appoint.service.order.exceptions.StockException;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.service.product.ProductService;
import com.jd.appoint.service.stock.StockInfoService;
import com.jd.appoint.service.stock.convert.StockConvert;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.common.util.StringUtils;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by luqiang3 on 2018/6/14.
 */
@Service("stockInfoService")
public class StockInfoServiceImpl implements StockInfoService{

    private static final Logger logger = LoggerFactory.getLogger(StockInfoServiceImpl.class);

    @Autowired
    private StockInfoDao stockInfoDao;
    @Autowired
    private StockOperateDao stockOperateDao;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Resource
    private RedisCache redisCache;
    @Autowired
    private ProductService productService;
    TransactionDefinition DEFAULT_TRANSACTION_DEFINITION = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    @Autowired
    private VenderConfigService venderConfigService;

    @Override
    public int insert(StockInfoPO stockInfoPO) {
        return stockInfoDao.insert(stockInfoPO);
    }

    @Override
    public List<StockInfoPO> querySelective(StockInfoQuery query) {
        return stockInfoDao.querySelective(query);
    }

    @Override
    public int updateByPrimaryKeySelective(StockInfoPO stockInfoPO) {
        return stockInfoDao.updateByPrimaryKeySelective(stockInfoPO);
    }

    /**
     * 更新总库存
     *
     * @param id
     * @param latestTotalQty
     * @return
     */
    @Override
    public int updateTotalQty(Long id, int latestTotalQty) {
        return stockInfoDao.updateTotalQty(id, latestTotalQty);
    }

    /**
     * 扣减库存
     *
     * @param stock
     * @return 扣减 【成功：true】 【失败：false】
     */
    @Override
    public OperateResultEnum decreaseStock(Stock stock) {
        logger.info("扣减库存开始，stock={}", stock.toString());
        Boolean validateResult =  validateParam(stock);
        if(!validateResult){
            logger.info("扣减库存参数校验未通过，stock={}", stock.toString());
            return OperateResultEnum.FAIL;
        }
        StockOperatePO stockOperatePO = stockOperateDao.queryByAppointOrderId(stock.getAppointOrderId());
        if(null != stockOperatePO && StockOptStatusEnum.DECREASE == stockOperatePO.getStockStatus()){
            return OperateResultEnum.SUCCESS;//该订单当前为扣减状态，直接返回成功
        }
        if(!validateProductStatus(stock)){
            logger.info("扣减库存产品状态不可用，stock={}", stock.toString());
            return OperateResultEnum.FAIL;
        }
        OperateResultEnum operateResult = this.decreaseStockAndInsertOpt(stock);
        if(OperateResultEnum.SUCCESS == operateResult){
            try {
                updateStockCache(stock, stock.getDate(), -1L);//更新缓存
            }catch (Exception e){
                logger.error("扣减库存更新缓存异常：stock={},e={}", stock.toString(), e);
                return OperateResultEnum.RETRY;
            }
        }else {
            //设置缓存库存数量为0
            clearStockCache(stock, stock.getDate());
            //扣减失败发报警短信
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.STOCK_DECREASE);
        }
        logger.info("扣减库存完成，operateResult={}，stock={}", operateResult, stock.toString());
        return operateResult;
    }

    /**
     * 回冲库存
     *
     * @param stock
     * @return 回冲 【成功：true】 【失败：false】
     */
    @Override
    public OperateResultEnum increaseStock(Stock stock) {
        logger.info("回冲库存开始，stock={}", stock.toString());
        Boolean validateResult = validateParam(stock);
        if(!validateResult){
            logger.info("回冲库存参数校验未通过，stock={}", stock.toString());
            return OperateResultEnum.FAIL;
        }
        StockOperatePO stockOperatePO = stockOperateDao.queryByAppointOrderId(stock.getAppointOrderId());
        if(null != stockOperatePO && StockOptStatusEnum.INCREASE == stockOperatePO.getStockStatus()){
            return OperateResultEnum.SUCCESS;//该订单当前为回冲状态，直接返回成功
        }
        OperateResultEnum operateResult = this.increaseStockAndUpdateOpt(stock);
        if(OperateResultEnum.SUCCESS == operateResult){
            try {
                updateStockCache(stock, stock.getDate(), 1L);//更新缓存
            }catch (Exception e){
                logger.error("回冲库存更新缓存异常：stock={},e={}", stock.toString(), e);
                return OperateResultEnum.RETRY;
            }
        }else {
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.STOCK_INCREASE);//扣减库存失败，发报警短信
        }
        logger.info("回冲库存结束，operateResult={}，stock={}", operateResult, stock.toString());
        return operateResult;
    }

    /**
     * 改期
     *
     * @param stock
     * @return
     */
    @Override
    public OperateResultEnum changeStock(Stock stock) {
        logger.info("改期开始，stock={}", stock.toString());
        Boolean validateResult =  validateParam(stock);
        if(!validateResult || null == stock.getPreDate()){
            logger.info("改期参数校验未通过，stock={}", stock.toString());
            return OperateResultEnum.FAIL;
        }
        if(AppointDateUtils.isSameDate(stock.getDate(), stock.getPreDate())){
            logger.info("改期日期与原日期一致，不做操作，stock={}", stock.toString());
            return OperateResultEnum.FAIL;
        }
        StockInfoPO decreaseStock = StockConvert.stock2StockInfoPO(stock, stock.getDate());//扣减库存数据
        StockInfoPO increaseStock = StockConvert.stock2StockInfoPO(stock, stock.getPreDate());//回冲库存数据
        StockOperatePO optStock = StockConvert.stock2StockOperatePO(stock, StockOptStatusEnum.DECREASE);//新的库存操作记录
        OperateResultEnum operateResult = changeSchedule(decreaseStock, increaseStock, optStock);
        if(OperateResultEnum.SUCCESS == operateResult){
            try {
                updateStockCache(stock, stock.getDate(), -1L);//更新扣减库存缓存
                updateStockCache(stock, stock.getPreDate(), 1L);//更新回冲库存缓存
            }catch (Exception e){
                logger.error("改期更新缓存异常：stock={},e={}", stock.toString(), e);
            }
        }
        logger.info("改期结束，changeResult={}，stock={}", operateResult, stock.toString());
        return operateResult;
    }

    /**
     * 改期
     * @param decreaseStock
     * @param increaseStock
     * @param optStock
     * @return
     */
    private OperateResultEnum changeSchedule(StockInfoPO decreaseStock, StockInfoPO increaseStock, StockOperatePO optStock){
        TransactionStatus transaction = transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            if(stockOperateDao.updateAppointDate(optStock.getAppointOrderId(), StockOptStatusEnum.DECREASE, optStock.getAppointDate()) < 1){
                throw new StockException("改期更新预约日期失败");
            }
            if(stockInfoDao.decreaseStock(decreaseStock) < 1){
                throw new StockException("改期扣减库存失败");
            }
            if(stockInfoDao.increaseStock(increaseStock) < 1){
                throw new StockException("改期回冲库存失败");
            }
            transactionManager.commit(transaction);
            return OperateResultEnum.SUCCESS;
        }catch (StockException e){
            transactionManager.rollback(transaction);
            logger.error("改期异常RE：decreaseStock={}，increaseStock={}", decreaseStock.toString(), increaseStock.toString(), e);
            return OperateResultEnum.FAIL;
        }catch (Exception e){
            transactionManager.rollback(transaction);
            logger.error("改期异常：decreaseStock={}，increaseStock={}", decreaseStock.toString(), increaseStock.toString(), e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.CHANGE_SCHEDULE);
            return OperateResultEnum.RETRY;
        }
    }

    /**
     * 扣减库存和添加扣减操作记录
     * @param stock
     * @return
     */
    public OperateResultEnum decreaseStockAndInsertOpt(Stock stock){
        TransactionStatus transaction = transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            StockInfoPO stockInfoPO = StockConvert.stock2StockInfoPO(stock, stock.getDate());
            if(stockInfoDao.decreaseStock(stockInfoPO) < 1){
                throw new StockException("扣减库存失败");
            }
            stockOperateDao.insert(StockConvert.stock2StockOperatePO(stock, StockOptStatusEnum.DECREASE));
            transactionManager.commit(transaction);
            return OperateResultEnum.SUCCESS;
        }catch (StockException e){
            transactionManager.rollback(transaction);
            logger.error("扣减库存和添加扣减记录异常RE：stock={},e={}", stock.toString(), e);
            return OperateResultEnum.FAIL;
        } catch (Exception e){
            transactionManager.rollback(transaction);
            logger.error("扣减库存和添加扣减记录异常：stock={},e={}", stock.toString(), e);
            return OperateResultEnum.RETRY;
        }
    }

    /**
     * 回冲库存和添加回冲操作记录
     * @param stock
     * @return
     */
    public OperateResultEnum increaseStockAndUpdateOpt(Stock stock){
        TransactionStatus transaction = transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            if(stockOperateDao.updateStockStatus(stock.getAppointOrderId(), StockOptStatusEnum.INCREASE, StockOptStatusEnum.DECREASE) < 1){
                throw new StockException("添加回冲记录失败");
            }
            StockInfoPO stockInfoPO = StockConvert.stock2StockInfoPO(stock, stock.getDate());
            if(stockInfoDao.increaseStock(stockInfoPO) < 1){
                throw new StockException("回冲库存失败");
            }
            transactionManager.commit(transaction);
            return OperateResultEnum.SUCCESS;
        }catch (StockException e){
            transactionManager.rollback(transaction);
            logger.error("回冲库存和添加扣减记录异常RE：stock={},e={}", stock.toString(), e);
            return OperateResultEnum.FAIL;
        }catch (Exception e){
            transactionManager.rollback(transaction);
            logger.error("回冲库存和添加扣减记录异常：stock={},e={}", stock.toString(), e);
            return OperateResultEnum.RETRY;
        }
    }

    /**
     * 更新库存信息
     * @param stock
     * @param num
     */
    private void updateStockCache(Stock stock, Date date, Long num){
        String key = CacheUtils.getScheduleStockKey(stock.getBusinessCode(), stock.getVenderId(), stock.getStoreCode(), stock.getSkuId());
        Long result = redisCache.hIncrBy(key, AppointDateUtils.getDate2Str("yyyy-MM-dd", date), num);
        if(result < 0L){
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.STOCK_CACHE_LESS_THAN_ZERO);
            logger.error("缓存中的剩余库存小于零,key={}", key);
        }
    }

    /**
     * 清空缓存
     * @param stock
     * @param date
     */
    private void clearStockCache(Stock stock, Date date){
        String key = CacheUtils.getScheduleStockKey(stock.getBusinessCode(), stock.getVenderId(), stock.getStoreCode(), stock.getSkuId());
        Map<String, String> map = new HashMap<>();
        map.put(AppointDateUtils.getDate2Str("yyyy-MM-dd", date), "0");
        logger.info("该日期无库存，清除缓存数据：key={}", key);
        redisCache.hMSet(key, map);
    }

    /**
     * 必填参数校验
     * @param stock
     * @return
     */
    private Boolean validateParam(Stock stock){
        boolean result = StringUtils.isNotBlank(stock.getBusinessCode()) &&
                null != stock.getVenderId() &&
                null != stock.getAppointOrderId() &&
                null != stock.getDate();
        ScheduleModelEnum scheduleModel = venderConfigService.getScheduleModel(stock.getBusinessCode());
        if(ScheduleModelEnum.VENDER == scheduleModel){
            stock.setSkuId(DefaultVenderConfig.SKU_ID);
        }
        if(StringUtils.isBlank(stock.getStoreCode())){
            stock.setStoreCode(DefaultVenderConfig.STORE_CODE);
        }
        return result;
    }

    /**
     * 校验产品状态是否可用
     * @param stock
     * @return
     */
    private Boolean validateProductStatus(Stock stock){
        ProductPO productPO = productService.queryByShopIdAndSkuId(stock.getVenderId(), Long.parseLong(stock.getStoreCode()), stock.getSkuId());
        if(null == productPO){
            return Boolean.TRUE;
        }
        return productPO.getStatus() == StatusEnum.ENABLE ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 保存或更新库存信息
     *
     * @return
     */
    @Override
    public List<String> saveOrUpdateStock(StockInfoVO vo) {
        if(AppointDateUtils.daysBetweenTwoDate(new Date(), vo.getEndDate()) > 180){
            vo.setEndDate(AppointDateUtils.addDays(new Date(), 180));//最多支持录入未来180天的数据
        }
        if(AppointDateUtils.daysBetweenTwoDate(vo.getStartDate(), new Date()) > 1){
            vo.setStartDate(new Date());
        }
        Map<String, StockInfoPO> map = getAlreadyStockInfo(vo);
        List<String> failData = new ArrayList<>();//用于收集更改失败的数据

        int days = AppointDateUtils.daysBetweenTwoDate(vo.getStartDate(), vo.getEndDate())+1;
        for(int i = 0; i < days; i++){
            String day = AppointDateUtils.getDate2Str("yyyy-MM-dd", AppointDateUtils.addDays(vo.getStartDate(), i));
            String stockKey = getStockKey(vo.getBusinessCode(), vo.getVenderId(), vo.getStoreCode(), vo.getSkuId(), day);
            StockInfoPO stockInfoPO = StockConvert.stockInfoVO2StockInfoPO(vo, day);
            Boolean flag;
            if(map.containsKey(stockKey)){
                StockInfoPO oldStock = map.get(stockKey);
                if(oldStock.getTotalQty() == stockInfoPO.getTotalQty()){
                    logger.info("修改前后总库存一致：stockInfoPO={}", JSON.toJSONString(stockInfoPO));
                    continue;
                }
                stockInfoPO.setId(oldStock.getId());
                flag = this.updateStockAndCache(stockInfoPO);
            }else {
                flag = this.insertStockAndCache(stockInfoPO);
            }
            if(!flag){
                failData.add(day);//收集更新或添加失败的日期，返回到页面显示
            }
        }
        return failData;
    }

    /**
     * 获得已存在的库存信息
     * @param vo
     * @return
     */
    private Map<String, StockInfoPO> getAlreadyStockInfo(StockInfoVO vo){
        StockInfoQuery query = StockConvert.stockInfoVO2StockInfoQuery(vo);
        List<StockInfoPO> stockInfoPOs = this.querySelective(query);
        Map<String, StockInfoPO> map = new HashMap<>();
        for(StockInfoPO stock : stockInfoPOs){
            String day = AppointDateUtils.getDate2Str("yyyy-MM-dd", stock.getDate());
            String stockKey = getStockKey(stock.getBusinessCode(), stock.getVenderId(), stock.getStoreCode(), stock.getSkuId(), day);
            map.put(stockKey, stock);
        }
        return map;
    }

    /**
     * key = businessCode + venderId + storeId + skuId + day
     * @return
     */
    private String getStockKey(String businessCode, Long venderId, String storeId, Long skuId, String day){
        return new StringBuilder(businessCode)
                .append(venderId)
                .append(storeId)
                .append(skuId)
                .append(day).toString();
    }

    /**
     * 更新库存和缓存
     * @param stockInfoPO
     * @return
     */
    private Boolean updateStockAndCache(StockInfoPO stockInfoPO){
        TransactionStatus transaction = transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            if(stockInfoDao.updateTotalQty(stockInfoPO.getId(), stockInfoPO.getTotalQty()) <= 0){
                throw new StockException("更新库存失败");
            }
            StockInfoPO latestStock = stockInfoDao.findById(stockInfoPO.getId());
            Map<String, String> map = new HashMap<>();
            map.put(AppointDateUtils.getDate2Str("yyyy-MM-dd", stockInfoPO.getDate()), latestStock.getRemindQty().toString());
            redisCache.hMSet(getScheduleStockKey(stockInfoPO), map);
            transactionManager.commit(transaction);
        }catch (StockException e){
            transactionManager.rollback(transaction);
            logger.error("更新库存异常SE：stockInfoPO={},e={}", stockInfoPO.toString(), e);
            return Boolean.FALSE;
        }catch (Exception e){
            transactionManager.rollback(transaction);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.STOCK_UPDATE);
            logger.error("更新库存异常：stockInfoPO={},e={}", stockInfoPO.toString(), e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 添加库存和缓存
     * @param stockInfoPO
     * @return
     */
    private Boolean insertStockAndCache(StockInfoPO stockInfoPO){
        TransactionStatus transaction = transactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        try {
            stockInfoDao.insert(stockInfoPO);
            Map<String, String> map = new HashMap<>();
            map.put(AppointDateUtils.getDate2Str("yyyy-MM-dd", stockInfoPO.getDate()), stockInfoPO.getTotalQty() + "");
            redisCache.hMSet(getScheduleStockKey(stockInfoPO), map);
            transactionManager.commit(transaction);
        }catch (Exception e){
            transactionManager.rollback(transaction);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.STOCK_INSERT);
            logger.error("添加库存异常：stockInfoPO={},e={}", stockInfoPO.toString(), e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 获得产能日历查询时缓存的库存key
     * @param stockInfoPO
     * @return
     */
    private String getScheduleStockKey(StockInfoPO stockInfoPO){
        return CacheUtils.getScheduleStockKey(stockInfoPO.getBusinessCode(),
                stockInfoPO.getVenderId(),
                stockInfoPO.getStoreCode(),
                stockInfoPO.getSkuId());
    }

    /**
     * 初始化所有库存信息
     */
    @Override
    public void init() {
        logger.info("初始化库存开始");
        for(int offset = 0; offset < 1000; offset++){
            List<StockInfoPO> stockInfoPOs = stockInfoDao.queryOnPage(offset*20, 20);
            if(CollectionUtils.isEmpty(stockInfoPOs)){
                break;
            }
            for(StockInfoPO stockInfoPO : stockInfoPOs){
                Map<String, String> map = new HashMap<>();
                map.put(AppointDateUtils.getDate2Str("yyyy-MM-dd", stockInfoPO.getDate()), stockInfoPO.getRemindQty().toString());
                String key = getScheduleStockKey(stockInfoPO);
                redisCache.hMSet(key, map);
            }
        }
        logger.info("初始化库存结束");
    }
}
