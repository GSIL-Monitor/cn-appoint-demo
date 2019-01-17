package com.jd.appoint.service.util;

import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.fastjson.JSON;
import com.jd.jim.cli.Cluster;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CacheUtilsTest extends JUnit4SpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtilsTest.class);
    @Autowired
    private Cluster jmiClient;

    @Test
    public void testStringCache() {

        String key = "test_string_key";
        //jmiClient.del(key);
        String value = CacheUtils.getObjectFromCacheOrDB(String.class, () -> {
            return jmiClient.get(key);
        }, () -> {
            return "cache_string_value";
        }, (v) -> {
            jmiClient.set(key, v);
        });
        logger.info("=============" + value);
    }

    @Test
    public void testObjectCache() {
        String key = "test_object_key";
        long expire = 180;//3分钟过期
        AppointOrderDetailVO value = CacheUtils.getObjectFromCacheOrDB(AppointOrderDetailVO.class, () -> {
            return jmiClient.get(key);
        }, () -> {
            AppointOrderDetailVO detail = new AppointOrderDetailVO();
            detail.setId(3L);
            detail.setBusinessCode("FUZHUANG");
            detail.setStaffName("张三");
            return detail;
        }, (v) -> {
            jmiClient.setEx(key, v, expire, TimeUnit.SECONDS);
        });
        logger.info("=============" + JSON.toJSONString(value));
    }

    @Test
    public void testHashKeyAndListCache() {
        String key = "WORK_TIME_ITEM";
        String field = "0524";
        List<TimeSpan> list = CacheUtils.getListFromCacheOrDB(TimeSpan.class, () -> {
            return jmiClient.hGet(key, field);
        }, () -> {
            //模拟获取可用时间区间
            List<TimeSpan> timeSpanList = new ArrayList<>();
            TimeSpan timeSpan1 = new TimeSpan();
            timeSpan1.setStartTime("9:00");
            timeSpan1.setEndTime("11:00");
            timeSpanList.add(timeSpan1);
            TimeSpan timeSpan2 = new TimeSpan();
            timeSpan2.setStartTime("11:00");
            timeSpan2.setEndTime("13:00");
            timeSpanList.add(timeSpan2);
            return timeSpanList;
        }, (v) -> {
            jmiClient.hSet(key, field, v);
        });
        logger.info("=============" + JSON.toJSONString(list));
    }

    @Test
    public void testSafeCacheOp() {
        String key = "WORK_TIME_ITEM";

        Boolean exists = CacheUtils.safeCacheOp(() -> {
            return jmiClient.exists(key);
        });

        Long del = CacheUtils.safeCacheOp(() -> {
            return jmiClient.del(key);
        });

        Boolean expire = CacheUtils.safeCacheOp(() -> {
            return jmiClient.expire(key, 30, TimeUnit.SECONDS);
        });

        logger.info("=============" + exists);
        logger.info("=============" + del);
        logger.info("=============" + expire);

    }

    @Test
    public void testGetSet() {
        jmiClient.del("mutex");
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    long timeOut = 5;
                    long expireTime;
                    while (true) {
                        expireTime = System.currentTimeMillis() + timeOut * 1000;
                        Boolean getLock = jmiClient.setNX("mutex", expireTime + "");

                        System.out.println(Thread.currentThread().getName() + "获得mutex锁"+getLock);
                        if (getLock) {//拿到锁
                            System.out.println(Thread.currentThread().getName() + "获得mutex锁");
                            break;
                        } else if (System.currentTimeMillis() > Long.parseLong(jmiClient.get("mutex"))) {//防止拿到锁的进程挂掉
                            if (System.currentTimeMillis() > Long.parseLong(jmiClient.getSet("mutex", expireTime + ""))){
                                break;
                            }
                        } else{
                            try {//休息0.1秒重试
                                Thread.sleep(100);
                                System.out.println(Thread.currentThread().getName() + "休息了0.1s");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "do job");
                    try {
                        Thread.sleep(170);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(System.currentTimeMillis()<expireTime){
                        jmiClient.del("mutex");
                        System.out.println(Thread.currentThread().getName() + "删除锁");
                    }
                }
            });
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetSet2() {
        if(getBoolean()){
            System.out.println("==============ok");
        }else{
            System.out.println("==============wrong");
        }
    }

    private Boolean getBoolean() {
        return null;
    }
}
