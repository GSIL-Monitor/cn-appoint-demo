package com.jd.appoint.service.shop;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.shop.query.ShopBusinessQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by yangyuan on 5/16/18.
 */
public class ShopBusinessServiceTest extends JUnit4SpringContextTests {

    @Autowired
    private ShopBusinessService shopBusinessService;

    @Test
    public void testQueryOnPage(){
        System.out.println(JSON.toJSONString(shopBusinessService.queryOnPage(new ShopBusinessQuery())));
    }
}
