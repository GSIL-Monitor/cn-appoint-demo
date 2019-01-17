package com.jd.appoint.service.api;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.api.VenderConfigFacade;
import com.jd.appoint.api.vo.QueryConfigVO;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

public class VenderConfigFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(VenderConfigFacadeTest.class);

    @Autowired
    private VenderConfigFacade venderConfigFacade;

    @Test
    public void testGetVenderConfig(){
        SoaRequest<QueryConfigVO> soaRequest = new SoaRequest<>();
        QueryConfigVO queryConfigVO = new QueryConfigVO();
        queryConfigVO.setKey("route");
        queryConfigVO.setBusinessCode("testnew");
        queryConfigVO.setVenderId(11111L);

        soaRequest.setData(queryConfigVO);
        SoaResponse<VenderConfigVO> soaResponse = venderConfigFacade.getVenderConfig(soaRequest);
        logger.info("======================="+ JSONArray.toJSON(soaResponse));

    }


}
