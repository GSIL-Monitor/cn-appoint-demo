package com.jd.appoint.service.order;

import com.google.common.collect.Lists;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.shopapi.vo.LsnInputVO;
import com.jd.appoint.shopapi.vo.LsnVO;
import com.jd.appoint.vo.order.ShopAppointOrderListRequest;
import com.jd.fastjson.JSON;
import com.jd.fastjson.JSONArray;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.List;

/**
 * Created by shaohongsen on 2018/5/25.
 */
public class TestOrderServiceFacede extends JUnit4SpringContextTests {
    @Autowired
    private ShopAppointOrderFacade facade;

    @Test
    public void testList() {
        SoaRequest<ShopAppointOrderListRequest> soaRequest = new SoaRequest<>();
        ShopAppointOrderListRequest request = new ShopAppointOrderListRequest();
        request.setPageNumber(1);
        request.setPageSize(20);
        request.setBusinessCode("101");
        request.setVenderId(60461l);
        request.setServerType(2);
        soaRequest.setData(request);
        System.out.println(JSON.toJSONString(facade.dynamicList(soaRequest)));
    }


    /**
     * 测试导入物流单号
     */
    @Test
    public void testInputLsns() {
        SoaRequest<LsnInputVO> soaRequest = new SoaRequest<>();
        LsnInputVO lsnInputVO = new LsnInputVO();
        lsnInputVO.setVenderId(60461L);
        List<LsnVO> lsnVoList = Lists.newArrayList();
        LsnVO lsnVO = new LsnVO();
        lsnVO.setLogisticsSiteId(2009);
        lsnVO.setLogisticsNo("12411");
        lsnVO.setAppointOrderId(666L);
        lsnVO.setLogisticsSource("天天快递");
        //lsnVO.setOrderId(10000L);
        lsnVoList.add(lsnVO);
        lsnInputVO.setOperateUser("鲁强");
        lsnInputVO.setLsnVos(lsnVoList);
        soaRequest.setData(lsnInputVO);
        SoaResponse soaResponse = facade.inputLsns(soaRequest);
        System.out.println(JSONArray.toJSON(soaResponse));
    }


}
