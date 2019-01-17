package com.jd.appoint.service.order;

import com.google.common.collect.Maps;
import com.jd.appoint.api.AppointOrderFacade;
import com.jd.appoint.vo.order.ContextSubmitAppointVO;
import com.jd.appoint.vo.order.SubmitAppointVO;
import com.jd.fastjson.JSON;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.fast.FastJUnit4Tests;

import java.util.Date;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/5/25.
 */
public class TestOrderService extends FastJUnit4Tests {
    @Autowired
    private AppointOrderFacade facade;
    @Autowired
    private AppointOrderService appointOrderService;
//    @Autowired
//    private LocalSecurityClient localSecurityClient;

    @Test
    public void testSubmit() {
        SoaRequest<SubmitAppointVO> request = new SoaRequest<>();
        SubmitAppointVO submitAppointVO = new SubmitAppointVO();
        submitAppointVO.setAppointStartTime(new Date());
        submitAppointVO.setAppointEndTime(new Date());
        submitAppointVO.setServerType(1);
        submitAppointVO.setCustomerName("邵县x");
        submitAppointVO.setCustomerPhone("17612396627");
        submitAppointVO.setCustomerUserPin("123");
        submitAppointVO.setSkuId(200100633944l);
        Map<String, String> stringStringMap = Maps.newHashMap();
        stringStringMap.put("areaIds", "1_23_456");
        stringStringMap.put("address", "1_23_456");
        submitAppointVO.setFormItems(stringStringMap);
        submitAppointVO.setBusinessCode("1001");
        submitAppointVO.setVenderId(12345l);
        submitAppointVO.setContextId("123");
        submitAppointVO.setErpOrderId(6231000799l);
        request.setData(submitAppointVO);
        System.out.println(JSON.toJSONString(facade.submitAppoint(request)));
        ;
    }

    @Test
    public void contextSubmit() {
        SoaRequest<ContextSubmitAppointVO> request = new SoaRequest<>();
        ContextSubmitAppointVO contextSubmitAppointVO = new ContextSubmitAppointVO();
        Map<String, String> map = Maps.newHashMap();
        map.put("appointStartTime", "2018-10-08 00:00:00");
        map.put("customerPhone", "18632789338");
        map.put("serverType", "2");
        map.put("customerName", "hh");
        map.put("skuId", "6442961");
        map.put("storeCode", "124602");
        map.put("area", "asd");
        map.put("address", "xxxx");
        contextSubmitAppointVO.setMap(map);
        contextSubmitAppointVO.setContextId("27616fd4-14eb-37e7-97a4-97e9c70302e9");
        request.setData(contextSubmitAppointVO);
        facade.contextSubmitAppoint(request);
    }

    @Test
    public void testFastDetail(){
        appointOrderService.fastDetail(666L);
    }
}
