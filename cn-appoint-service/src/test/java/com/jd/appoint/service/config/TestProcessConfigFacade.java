package com.jd.appoint.service.config;

import com.jd.appoint.api.ProcessConfigFacade;
import com.jd.appoint.api.vo.ProcessConfigRequest;
import com.jd.fastjson.JSON;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by shaohongsen on 2018/6/22.
 */
public class TestProcessConfigFacade extends JUnit4SpringContextTests {
    @Autowired
    private ProcessConfigFacade processConfigFacade;

}
