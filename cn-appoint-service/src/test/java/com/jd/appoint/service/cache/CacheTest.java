package com.jd.appoint.service.cache;

import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.common.utils.RedisCache;
import org.junit.Test;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created by luqiang3 on 2018/8/8.
 */
public class CacheTest extends JUnit4SpringContextTests {

    @Resource
    private RedisCache redisCache;

    @Test
    public void test(){
        String businessCode = "101";
        Long venderId = 60461L;
        String storeCode = "-1";
        Long skuId = -1L;
        String key1;
        String key2;
        String key3;
        key1 = CacheUtils.getScheduleStockKey(businessCode, venderId, storeCode, skuId);//产能日历库存
        key2 = CacheUtils.getShopWorkTimeKey(businessCode, venderId, storeCode, skuId);//服务时间
        key3 = CacheUtils.getScheduleWorkTimeKey(businessCode, venderId, storeCode, skuId);//产能日历服务时间
        redisCache.delete(key1);
        redisCache.delete(key2);
        redisCache.delete(key3);
    }
}
