package com.jd.appoint.service.shopapi;

import com.jd.appoint.shopapi.ShopDynamicConfigFacade;
import com.jd.appoint.vo.dynamic.ServerTypeRequest;
import com.jd.fastjson.JSON;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by shaohongsen on 2018/6/25.
 */
public class ShopDynamicConfigFacadeTest extends JUnit4SpringContextTests {
    @Autowired
    private ShopDynamicConfigFacade shopDynamicConfigFacade;

    @Test
    public void testserverTypeList() {
        SoaRequest<String> soaRequest = new SoaRequest<>();
        soaRequest.setData("1001");
        System.out.println(JSON.toJSONString(shopDynamicConfigFacade.serverTypeList(soaRequest)));
    }

    @Test
    public void testBatchOperateList() {
        SoaRequest<ServerTypeRequest> soaRequest = new SoaRequest<>();
        ServerTypeRequest request = new ServerTypeRequest();
        request.setServerType(1);
        request.setBusinessCode("1001");
        soaRequest.setData(request);
        System.out.println(JSON.toJSONString(shopDynamicConfigFacade.batchOperateList(soaRequest)));
    }

    @Test
    public void testDetailOperateList() {
        SoaRequest<Long> detailOperateRequestSoaRequest = new SoaRequest<>();
        detailOperateRequestSoaRequest.setData(317l);
        System.out.println(JSON.toJSONString(shopDynamicConfigFacade.detailOperateList(detailOperateRequestSoaRequest)));
    }


    @Test
    public void testFilterItemList() {
        SoaRequest<ServerTypeRequest> soaRequest = new SoaRequest<>();
        ServerTypeRequest request = new ServerTypeRequest();
        request.setServerType(1);
        request.setBusinessCode("101");
        soaRequest.setData(request);
        System.out.println(JSON.toJSONString(shopDynamicConfigFacade.filterItemList(soaRequest)));
    }
}
