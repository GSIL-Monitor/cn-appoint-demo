package com.jd.appoint.service.shop;

import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.enums.TimeUnitEnum;
import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.ShopWorkTimePO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/5/5.
 */
public class ShopWorkTimeServiceTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopWorkTimeServiceTest.class);

    @Autowired
    private ShopWorkTimeService shopWorkTimeService;
    @Autowired
    private ShopWorkTimeItemService shopWorkTimeItemService;

    @Test
    public void testInsertShopWorkTime(){
        shopWorkTimeService.insertShopWorkTime(mockShopWorkTimePO());
    }

    @Test
    public void testInsertShopWorkTimeAndItem(){
        long start = System.currentTimeMillis();
        logger.info("InsertShopWorkTimeAndItem start......................");
        ShopWorkTimePO shopWorkTimePO = mockShopWorkTimePO();
        shopWorkTimeService.insertShopWorkTime(shopWorkTimePO);
        Long shopWorkTimeId = shopWorkTimePO.getId();
        if(null == shopWorkTimeId || shopWorkTimeId <= 0){
            logger.info("shopWorkTimeId is error!");
            return;
        }
        logger.info("shopWorkTimeId：{}", shopWorkTimeId);
        for (int i = 0; i < 7; i++){
            shopWorkTimeItemService.insertShopWorkTimeItem(mockShopWorkTimeItemPO(shopWorkTimeId, i + 1));
        }
        logger.info("InsertShopWorkTimeAndItem end......................耗时：{}", System.currentTimeMillis() - start);
    }

    /**
     * 模拟服务时间信息
     * @return
     */
    private ShopWorkTimePO mockShopWorkTimePO(){
        ShopWorkTimePO shopWorkTimePO = new ShopWorkTimePO();
        shopWorkTimePO.setBusinessCode("123");
        shopWorkTimePO.setVenderId(9999L);
        shopWorkTimePO.setStartDay(0);
        shopWorkTimePO.setEndDay(10);
        shopWorkTimePO.setT0Advance(4);
        shopWorkTimePO.setT0AdvanceUnit(TimeUnitEnum.MINUTE);
        shopWorkTimePO.setTimeInterval(60);
        shopWorkTimePO.setTimeIntervalUnit(TimeUnitEnum.MINUTE);
        shopWorkTimePO.setTimeShowType(TimeShowTypeEnum.POINT);
        shopWorkTimePO.setStatus(StatusEnum.ENABLE);
        return shopWorkTimePO;
    }

    /**
     * 模拟服务时间项信息
     * @param shopWorkTimeId
     * @param weekday
     * @return
     */
    private ShopWorkTimeItemPO mockShopWorkTimeItemPO(Long shopWorkTimeId, Integer weekday){
        ShopWorkTimeItemPO shopWorkTimeItemPO = new ShopWorkTimeItemPO();
        shopWorkTimeItemPO.setShopWorkTimeId(shopWorkTimeId);
        shopWorkTimeItemPO.setWeekday(weekday);
        shopWorkTimeItemPO.setWorkStart("08:00");
        shopWorkTimeItemPO.setWorkEnd("18:00");
        //shopWorkTimeItemPO.setRestStart();
        //shopWorkTimeItemPO.setRestEnd();
        shopWorkTimeItemPO.setStatus(StatusEnum.ENABLE);
        return shopWorkTimeItemPO;
    }
}
