package com.jd.appoint.service.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by yangyuan on 7/4/18.
 */
public class BusinessVenderMapServiceTest extends JUnit4SpringContextTests {

    @Autowired
    private BusinessVenderMapService b;

    @Test
    public void test(){
        b.listVender(Lists.newArrayList(13l,4223l,345l));
    }

    @Test
    public void test2(){
        System.out.println(JSON.toJSONString(b.queryByVenderId(345l)));
    }
}
