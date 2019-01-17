package com.jd.appoint.service.outerapi;

import com.alibaba.fastjson.JSON;
import com.jd.virtual.appoint.SkuGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.sku.SkuInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.List;

/**
 * Created by luqiang3 on 2018/7/5.
 */
public class SkuGwServiceTest extends JUnit4SpringContextTests {

    private Logger logger = LoggerFactory.getLogger(ScheduleServiceTest.class);

    @Autowired
    private SkuGwService skuGwService;

    @Test
    public void testGetSkuInfo(){
        CommonRequest request = new CommonRequest();
        request.setContextId("39f39502-13dd-4a97-b189-31a9c6c3db0d");
        CommonResponse<List<SkuInfo>> skuInfo = skuGwService.getSkuInfo(request);
        logger.info("getSkuInfo返回结果：skuInfo={}", JSON.toJSONString(skuInfo));
    }
}
