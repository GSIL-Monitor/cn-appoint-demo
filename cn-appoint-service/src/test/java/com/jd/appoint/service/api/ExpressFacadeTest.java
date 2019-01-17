package com.jd.appoint.service.api;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.api.ExpressFacade;
import com.jd.appoint.vo.express.ExpressInfo;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/7/5.
 */
public class ExpressFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ExpressFacadeTest.class);

    @Autowired
    private ExpressFacade expressFacade;

    @Test
    public void testExpressInfo(){
        SoaRequest<Long> request = new SoaRequest<>();
        request.setData(123456789L);
        SoaResponse<ExpressInfo> expressInfo = expressFacade.getExpressInfo(request);
        logger.info("查询物流信息返回结果：expressInfo={}", JSON.toJSONString(expressInfo));
    }
}
