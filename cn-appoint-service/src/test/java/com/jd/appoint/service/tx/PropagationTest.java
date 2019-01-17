package com.jd.appoint.service.tx;

import com.jd.appoint.service.shop.ShopStaffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class PropagationTest {

    @Resource
    private ShopStaffService shopStaffService;

    @Test
    public void test(){
        //shopStaffService.testPropagation();
    }
}
