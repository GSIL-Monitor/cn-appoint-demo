package com.jd.appoint.service.api;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.api.ProcessConfigFacade;
import com.jd.appoint.api.vo.ProcessConfigRequest;
import com.jd.appoint.api.vo.ProcessConfigVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/8/1.
 */
public class ProcessConfigFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(AppointOrderFacadeTest.class);

    @Autowired
    private ProcessConfigFacade processConfigFacade;

    @Test
    public void testGetProcessConfig(){
        SoaRequest<ProcessConfigRequest> soaRequest = new SoaRequest<>();
        ProcessConfigRequest request = new ProcessConfigRequest();
        request.setPageNo("contact");
        request.setContextId("12345678");
        soaRequest.setData(request);
        SoaResponse<ProcessConfigVO> processConfig = processConfigFacade.getProcessConfig(soaRequest);
        logger.info("返回结果：processConfig={}", JSON.toJSONString(processConfig));
    }
}
