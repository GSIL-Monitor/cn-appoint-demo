package com.jd.appoint.service.shop;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeItemQuery;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.List;

/**
 * Created by luqiang3 on 2018/5/8.
 */
public class ShopWorkTimeItemServiceTest extends JUnit4SpringContextTests {

    private static Logger logger = LoggerFactory.getLogger(ShopWorkTimeItemServiceTest.class);

    @Autowired
    private ShopWorkTimeItemService shopWorkTimeItemService;

    @Test
    public void testInsertShopWorkTimeItem(){
        long start = System.currentTimeMillis();
        logger.info("添加服务时间项 start.................");
        for(int i = 0 ; i < 7 ; i++){
            ShopWorkTimeItemPO item = new ShopWorkTimeItemPO();
            item.setShopWorkTimeId(1L);
            item.setWeekday(i + 1);
            item.setWorkStart("08:00");
            item.setWorkEnd("18:00");
            item.setRestStart("12:00");
            item.setRestEnd("13:00");
            item.setStatus(StatusEnum.ENABLE);
            shopWorkTimeItemService.insertShopWorkTimeItem(item);
        }
        logger.info("添加服务时间项 end.................耗时：{}", System.currentTimeMillis() - start);
    }

    @Test
    public void testQueryShopWorkTimeItems(){
        long start = System.currentTimeMillis();
        logger.info("查询服务时间项 start.................");
        ShopWorkTimeItemQuery query = new ShopWorkTimeItemQuery();
        query.setShopWorkTimeId(1L);
        List<ShopWorkTimeItemPO> shopWorkTimeItemPOs = shopWorkTimeItemService.queryShopWorkTimeItems(query);
        if(CollectionUtils.isEmpty(shopWorkTimeItemPOs)){
            logger.info("查询服务时间项 no result..................");
        }else {
            logger.info("查询服务时间项 result..................{}", JSON.toJSONString(shopWorkTimeItemPOs));
        }
        logger.info("查询服务时间项 end.................耗时：{}", System.currentTimeMillis() - start);
    }
}
