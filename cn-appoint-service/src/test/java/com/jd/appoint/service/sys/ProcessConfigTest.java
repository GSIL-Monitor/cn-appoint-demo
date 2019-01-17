package com.jd.appoint.service.sys;

import com.jd.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by shaohongsen on 2018/6/15.
 */
public class ProcessConfigTest extends JUnit4SpringContextTests {
    @Autowired
    private ProcessConfigService processConfigService;

    @Test
    public void test() {
        System.out.println(JSON.toJSONString(processConfigService.getProcessConfig("1002", null, 1)));
        ;
    }

}
