package com.jd.appoint.service.rpc;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jd.appoint.api.ExpressFacade;
import com.jd.appoint.rpc.ExpressService;
import com.jd.appoint.rpc.RouteDto;
import com.jd.appoint.shopapi.ShopExpressFacade;
import com.jd.appoint.vo.express.ExpressSubscribeRequest;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by yangyuan on 6/22/18.
 */
public class ExpressServiceTest extends JUnit4SpringContextTests {

    @Autowired
    private ExpressService expressService;

    @Autowired
    private ShopExpressFacade shopExpressFacade;

    @Test
    public void testExpressInfo(){
        System.out.println(JSON.toJSONString(expressService.getAllExpressList()));
    }

    @Test
    public void testExpressInfoFacade(){
        System.out.println(shopExpressFacade.getAllExpressCompany());
    }
    @Test
    public void testGetInfo(){
        SoaRequest<Long> request = new SoaRequest<>();
        request.setData(349l);
        System.out.println(JSON.toJSONString(shopExpressFacade.getExpressInfo(request)));
    }

    @Test
    public void testGetCompanyName(){
        System.out.println(JSON.toJSONString(expressService.getExpressCompanyName(2009)));
    }

    @Test
    public void testRouteSubscribe() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SoaRequest<ExpressSubscribeRequest> requestSoaRequest = new SoaRequest<>();
//        ExpressSubscribeRequest request = new ExpressSubscribeRequest();
//        request.setShipId("VA00015018077");
//        request.setOrderId(1234567l);
//        request.setThirdId(2087);
//        requestSoaRequest.setData(request);
        System.out.println(expressService.routeSubscribe(237l, "VA00015018076", 2087));
    }

    @Test
    public void testSub() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<RouteDto> list = Lists.newArrayList();
        RouteDto routeDto1 = new RouteDto();
        routeDto1.setOrderId(123l);
        routeDto1.setShipId("VA00015018076");
        routeDto1.setThirdId(2087);
        list.add(routeDto1);
        RouteDto routeDto2 = new RouteDto();
        routeDto2.setOrderId(1234l);
        routeDto2.setShipId("VA00015018077");
        routeDto2.setThirdId(2087);
        list.add(routeDto2);
        expressService.routeSubscribe(list);
    }
}
