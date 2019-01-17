package com.jd.appoint.service.store;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.storeapi.StoreAppointOrderFacade;
import com.jd.appoint.vo.order.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gaoxiaoqing on 2018/7/9.
 */
public class StoreAppointOrderFacadeTest extends JUnit4SpringContextTests {
    @Resource
    private StoreAppointOrderFacade storeAppointOrderFacade;

    @Test
    public void testStoreReschule() {
        SoaRequest soaRequest = new SoaRequest();
        ReschduleVO reschduleVO = new ReschduleVO();
        reschduleVO.setAppointOrderId(2L);
        reschduleVO.setAppointStartTime(AppointDateUtils.getStrToDate("yyyy-MM-dd hh:mm:ss","2018-7-20 10:11:11"));
        reschduleVO.setAppointEndTime(AppointDateUtils.getStrToDate("yyyy-MM-dd hh:mm:ss","2018-7-20 11:11:11"));
        reschduleVO.setStoreCode("ceshi01");
        reschduleVO.setBusinessCode("101");
        reschduleVO.setLoginUserPin("gaoxiaoqing5");
        reschduleVO.setVenderId(60461L);
        soaRequest.setData(reschduleVO);
        SoaResponse soaResponse = storeAppointOrderFacade.reschdule(soaRequest);
        System.out.println("reschule result = " + JSON.toJSONString(soaResponse));
    }

    //审核
    @Test
    public void testStoreCheckOrder() {
        SoaRequest soaRequest = new SoaRequest();
        CheckOrderVO checkOrderVO = new CheckOrderVO();
        checkOrderVO.setStoreCode("ceshi01");
        checkOrderVO.setVenderId(60461L);
        checkOrderVO.setBusinessCode("101");
        checkOrderVO.setLoginUserPin("gxq");
        List<Long> appointOrderIds = new ArrayList<>();
        appointOrderIds.add(10L);
        checkOrderVO.setAppointOrderIds(appointOrderIds);
        soaRequest.setData(checkOrderVO);
        SoaResponse soaResponse = storeAppointOrderFacade.checkAppointOrder(soaRequest);
        System.out.println("result = " + JSON.toJSONString(soaResponse));
    }

    @Test
    public void testStoreExportOrder() {
        SoaRequest<StoreAppointOrderListRequest> soaRequest = new SoaRequest<>();
        StoreAppointOrderListRequest storeAppointOrderListRequest = new StoreAppointOrderListRequest();
        storeAppointOrderListRequest.setStoreCode("130004");
        storeAppointOrderListRequest.setServerType(2);
        storeAppointOrderListRequest.setBusinessCode("101");
        storeAppointOrderListRequest.setPageNumber(1);
        storeAppointOrderListRequest.setPageSize(10000);
        storeAppointOrderListRequest.setSearchMap(new HashMap<>());
        soaRequest.setData(storeAppointOrderListRequest);
        storeAppointOrderFacade.exportAppointOrders(soaRequest);
    }

    @Test
    public void testStoreDynamicEditAppointOrder() {
        UpdateAppointVO updateAppointVO = new UpdateAppointVO();
        updateAppointVO.setAppointOrderId(1L);
        updateAppointVO.setStoreCode("1");
        updateAppointVO.setBusinessCode("1001");
        updateAppointVO.setVenderMemo("强哥最帅");
        SoaResponse soaResponse = storeAppointOrderFacade.dynamicUpdateAppoint(new SoaRequest<>(updateAppointVO));
        System.out.println(soaResponse);
    }
}
