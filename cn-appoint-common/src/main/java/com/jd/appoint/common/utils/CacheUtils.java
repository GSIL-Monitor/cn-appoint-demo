package com.jd.appoint.common.utils;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.constants.CacheConstant;
import com.jd.appoint.common.interfaces.CacheOperation;
import com.jd.appoint.common.interfaces.CacheSetOperation;
import com.jd.appoint.common.interfaces.DbListFunction;
import com.jd.appoint.common.interfaces.DbObjectFunction;
import com.jd.jim.cli.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/10.
 * 缓存工具，主要功能把生成缓存key统一到这一个地方处理
 * 避免程序中对同一个key有多处构建实现
 */
public class CacheUtils {

    private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    private static String SPLIT = "_";

    /**
     * 生成缓存key通用方法，该方法内部会对传入的key追加上缓存版本号
     * eg: params = A,B 返回结果为：A_B_V1
     *
     * @param params
     * @return
     */
    private static String generateKey(String... params) {
        if (null == params || params.length <= 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append(param).append(SPLIT);
        }
        builder.append(CacheConstant.VERSION);
        return builder.toString();
    }

    /**
     * 生成缓存key通用方法，该方法适用于不能篡改版本号的缓存
     * eg: params = A,B 返回结果为：A_B
     *
     * @param params
     * @return
     */
    private static String generateNoVersionKey(String... params) {
        if (null == params || params.length <= 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append(param).append(SPLIT);
        }
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    /**
     * 缓存当天失效时间
     * 计算方法：一天24小时共计86400秒，减去当前时间的秒数
     *
     * @param date
     * @return 返回值单位为秒
     */
    public static int timeOutIntraday(Date date) {
        return 86400 - AppointDateUtils.getSeconds(date);
    }

    public static String getFormControlKey(String businessCode){
        return generateKey(CacheConstant.FORM_CONTROLS_KEY, businessCode);
    }

    public static String getProductKey(Long venderId, Long storeId, Long skuId){
        return generateKey(CacheConstant.PRODUCT_SKU,
                String.valueOf(venderId),
                String.valueOf(storeId),
                String.valueOf(skuId));
    }

    public static String getProductIgnoreStatusKey(Long venderId, Long storeId, Long skuId){
        return generateKey(CacheConstant.PRODUCT_IGNORE_STATUS,
                String.valueOf(venderId),
                String.valueOf(storeId),
                String.valueOf(skuId));
    }

    /**
     * 获得服务时间key
     * @param businessCode
     * @param venderId
     * @param storeCode
     * @param skuId
     * @return
     */
    public static String getShopWorkTimeKey(String businessCode, Long venderId, String storeCode, Long skuId){
        return generateKey(CacheConstant.SHOP_WORK_TIME, businessCode, venderId.toString(), storeCode, skuId.toString());
    }

    /**
     * 获得服务时间项key
     * @return
     */
    public static String getShopWorkTimeItemKey(Long shopWorkTimeId){
        return generateKey(CacheConstant.SHOP_WORK_TIME_ITEM, shopWorkTimeId.toString());
    }

    /**
     * 获得产能日历-服务时间缓存key
     * @param businessCode
     * @param venderId
     * @param storeCode
     * @param skuId
     * @return
     */
    public static String getScheduleWorkTimeKey(String businessCode, Long venderId, String storeCode, Long skuId) {
        return generateKey(CacheConstant.SCHEDULE_WORK_TIME, businessCode, venderId.toString(), storeCode, skuId.toString());
    }

    /**
     * 获得产能日历-库存缓存key
     * @param businessCode
     * @param venderId
     * @param storeCode
     * @param skuId
     * @return
     */
    public static String getScheduleStockKey(String businessCode, Long venderId, String storeCode, Long skuId){
        return generateNoVersionKey(CacheConstant.SCHEDULE_STOCK, businessCode, venderId.toString(), storeCode, skuId.toString());
    }

    /**
     * 获得产品缓存key
     * @param businessCode
     * @param venderId
     * @param storeCode
     * @param skuId
     * @return
     */
    public static String getProductKey(String businessCode, Long venderId, String storeCode, Long skuId){
        return generateKey(CacheConstant.PRODUCT, businessCode, venderId.toString(), storeCode, skuId.toString());
    }

    /**
     * 商家配置缓存key
     * eg:VENDER_CONFIG_KEY_route_FUZHUANG_1_V1
     * @param cfgKey
     * @param businessCode
     * @param venderId
     * @return
     */
    public static String getVenderConfigTripleField(String cfgKey, String businessCode, Long venderId) {
        return generateKey(cfgKey, businessCode, venderId.toString());
    }

    /**
     * 封装缓存set方法，避免缓存操作影响程序正常逻辑
     *
     * @param jimClient
     * @param key
     * @param value     可以为String，PlainObject，List等任意类型
     * @param msg
     */
    public static void safeSet(Cluster jimClient, String key, Object value, String msg) {
        //缓存操作必须捕捉异常，不能影响程序正常逻辑执行
        try {
            //放入缓存
            String jsonStr = JSON.toJSONString(value);
            jimClient.set(key, jsonStr);
            logger.info("将【{}】放入缓存：key={}，value={}", msg, key, jsonStr);
        } catch (Exception e) {
            logger.error("将【{}】放入缓存出现异常：key={}，e={}", msg, key, e);
        }
    }

    /**
     * 封装缓存get方法，避免缓存操作影响程序正常逻辑
     *
     * @param jimClient
     * @param key
     * @param msg
     * @param clazz
     * @param <T>
     * @return 返回clazz类型的变量
     */
    public static <T> T safeGetObject(Cluster jimClient, String key, String msg, Class<T> clazz) {
        T t = null;
        try {
            String value = jimClient.get(key);
            if(value != null) {
                t = JSON.parseObject(value, clazz);
                logger.info("缓存命中，获取【{}】成功：key={}，value={}", msg, key, value);
            }
        } catch (Exception e) {
            logger.error("从缓存中获取【{}】出现异常：key={}，e={}", msg, key, e);
        }
        return t;
    }

    /**
     * 封装缓存get方法，避免缓存操作影响程序正常逻辑
     *
     * @param jimClient
     * @param key
     * @param msg
     * @param clazz
     * @param <T>
     * @return 返回clazz类型的List
     */
    public static <T> List<T> safeGetList(Cluster jimClient, String key, String msg, Class<T> clazz) {
        List<T> list = null;
        try {
            String value = jimClient.get(key);
            if(value != null) {
                list = JSON.parseArray(value, clazz);
                logger.info("缓存命中，获取【{}】成功：key={}，value={}", msg, key, value);
            }
        } catch (Exception e) {
            logger.error("从缓存中获取【{}】出现异常：key={}，e={}", msg, key, e);
        }
        return list;
    }

    /**
     * 封装删除缓存方法
     *
     * @param jimClient
     * @param key
     * @return
     */
    public static void safeDel(Cluster jimClient, String key) {
        try {
            if (!jimClient.exists(key)) {
                logger.info("要删除的缓存key不存在：key={}", key);
                return;
            }
            jimClient.del(key);
            logger.info("从缓存中删除key成功：key={}", key);

        } catch (Exception e) {
            logger.error("从缓存中删除key出现异常：key={}，e={}", key, e);
        }
    }

    public static <R> R safeCacheOp(CacheOperation<R> cacheOperation) {
        try {
            return cacheOperation.cacheOp();

        } catch (Exception e) {
            logger.error("缓存操作出现异常：e={}", e);
        }
        return null;
    }

    /**
     * 先从缓存获取，命中直接返回
     * 没命中查询数据库，然后放入缓存
     * 返回值为普通类型数据
     * @param jimClient            缓存集群客户端
     * @param key                  缓存的key
     * @param msg                  缓存的描述，用于打印日志消息
     * @param clazz                缓存的数据的类型（也即数据库查询出的数据的类型，一般是String,PO或者List<PO>）
     * @param dbObjectFunction     数据库查询接口的一个具体实现
     * @return
     */
    public static <T> T getObjectFromCacheOrDB(Cluster jimClient, String key, String msg, Class<T> clazz, DbObjectFunction<T> dbObjectFunction) {
        //从缓存中获取数据
        T t = safeGetObject(jimClient, key, msg, clazz);

        //缓存命中，直接返回
        if (t != null) {
            return t;
        }
        //缓存没命中，从数据库读取数据
        logger.info("没有命中缓存，查询数据库获取【{}】", msg);
        T t1 = dbObjectFunction.dbQueryObject();

        if (t1 != null) {
            //放入缓存
            safeSet(jimClient, key, t1, msg);
        } else{
            logger.info("数据库查询结果为null");
        }
        return t1;
    }

    /**
     * 先从缓存获取，命中直接返回
     * 没命中查询数据库，然后放入缓存
     * 返回值为List类型数据
     * @param jimClient         缓存集群客户端
     * @param key               缓存的key
     * @param msg               缓存的描述，用于打印日志消息
     * @param clazz             缓存的数据的类型（也即数据库查询出的数据的类型，一般是String,PO或者List<PO>，此方法值适用于List类型，只需传PO.class）
     * @param dbListFunction    数据库查询接口的一个具体实现
     * @return
     */
    public static <T> List<T> getListFromCacheOrDB(Cluster jimClient, String key, String msg, Class<T> clazz, DbListFunction<T> dbListFunction) {
        //从缓存中获取数据
        List<T> list = safeGetList(jimClient, key, msg, clazz);

        //缓存命中，直接返回
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        //缓存没命中，从数据库读取数据
        logger.info("没有命中缓存，查询数据库获取【{}】", msg);
        List<T> list1 = dbListFunction.dbQueryList();

        if (!CollectionUtils.isEmpty(list1)) {
            //放入缓存
            safeSet(jimClient, key, list1, msg);
        } else{
            logger.info("数据库查询结果为null");
        }
        return list1;
    }

    public static <T> T safeGetObject(CacheOperation<String> getOperation, Class<T> clazz) {
        T t = null;
        try {
            String value = getOperation.cacheOp();
            if(value != null) {
                t = JSON.parseObject(value, clazz);
            }
        } catch (Exception e) {
            logger.error("获取缓存出现异常e={}", e);
        }
        return t;
    }

    public static void safeSet(CacheSetOperation setOperation, Object value) {
        //缓存操作必须捕捉异常，不能影响程序正常逻辑执行
        try {
            //放入缓存
            String jsonStr = JSON.toJSONString(value);
            setOperation.setOp(jsonStr);
        } catch (Exception e) {
            logger.error("放入缓存出现异常：e={}", e);
        }
    }

    public static <T> T getObjectFromCacheOrDB(Class<T> clazz, CacheOperation<String> getOperation, DbObjectFunction<T> dbObjectFunction, CacheSetOperation setOperation) {
        //从缓存中获取数据
        T t = safeGetObject(getOperation, clazz);

        //缓存命中，直接返回
        if (t != null) {
            return t;
        }
        //缓存没命中，从数据库读取数据
        logger.info("没有命中缓存，查询数据库");
        T t1 = dbObjectFunction.dbQueryObject();

        if (t1 != null) {
            //放入缓存
            safeSet(setOperation,t1);
        } else{
            logger.info("数据库查询结果为null");
        }
        return t1;
    }

    public static <T> T getObjectFromCacheOrDBWithDefaultValue(Class<T> clazz, CacheOperation<String> getOperation, DbObjectFunction<T> dbObjectFunction, CacheSetOperation setOperation, Object defaultValue) {
        //从缓存中获取数据
        T t = safeGetObject(getOperation, clazz);

        //缓存命中，直接返回
        if (t != null) {
            return t;
        }
        //缓存没命中，从数据库读取数据
        logger.info("没有命中缓存，查询数据库");
        T t1 = dbObjectFunction.dbQueryObject();

        if (t1 != null) {
            //放入缓存
            safeSet(setOperation,t1);
        } else{
            //放入缓存默认值
            safeSet(setOperation,defaultValue);
            logger.info("数据库查询结果为null，放入缓存默认值");
        }
        return t1;
    }

    public static <T> List<T> getListFromCacheOrDB(Class<T> clazz, CacheOperation<String> getOperation, DbListFunction<T> dbListFunction, CacheSetOperation setOperation) {
        //从缓存中获取数据
        List<T> list = safeGetList(getOperation, clazz);

        //缓存命中，直接返回
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        //缓存没命中，从数据库读取数据
        logger.info("没有命中缓存，查询数据库");
        list = dbListFunction.dbQueryList();

        if (list != null) {
            //放入缓存
            safeSet(setOperation,list);
        } else{
            logger.info("数据库查询结果为null");
        }
        return list;
    }

    public static <T> List<T> safeGetList(CacheOperation<String> getOperation, Class<T> clazz) {
        List<T> list = null;
        try {
            String value = getOperation.cacheOp();
            if(value != null) {
                list = JSON.parseArray(value, clazz);
            }
        } catch (Exception e) {
            logger.error("获取缓存出现异常：e={}", e);
        }
        return list;
    }

    public static String getStaffPhoneMutexKey(String serverPhone) {
        return generateKey(CacheConstant.STAFF_PHONE_MUTEX_KEY,serverPhone);
    }

    public static String getVenderConfigKey() {
        return generateKey(CacheConstant.VENDER_CONFIG_KEY);
    }
}
