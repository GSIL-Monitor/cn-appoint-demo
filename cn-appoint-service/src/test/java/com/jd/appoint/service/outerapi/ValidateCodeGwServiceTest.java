package com.jd.appoint.service.outerapi;

import com.jd.virtual.appoint.ValidateCodeGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/7/4.
 */
public class ValidateCodeGwServiceTest extends JUnit4SpringContextTests {

    @Autowired
    private ValidateCodeGwService validateCodeGwService;

    @Test
    public void testValidateCode(){
        CommonRequest request = new CommonRequest();
        request.setContextId("39f39502-13dd-4a97-b189-31a9c6c3db0d");
        validateCodeGwService.validateCode(request);
    }
}
