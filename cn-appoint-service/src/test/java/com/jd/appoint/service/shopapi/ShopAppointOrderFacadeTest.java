package com.jd.appoint.service.shopapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Preconditions;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.service.order.PopConfigService;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.shopapi.vo.OrderStatisticQuery;
import com.jd.appoint.shopapi.vo.ShopAppointOrderQueryVO;
import com.jd.appoint.shopapi.vo.ShopAppointUpdateVO;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.order.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * Shop端预约信息
 * Created by gaoxiaoqing on 2018/5/16.
 */
public class ShopAppointOrderFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopAppointOrderFacadeTest.class);

    @Autowired
    private ShopAppointOrderFacade shopAppointOrderFacade;
    @Resource
    private PopConfigService popConfigService;

    @Test
    public void testShopEditAppointOrder() {
        ShopAppointUpdateVO shopAppointUpdateVO = new ShopAppointUpdateVO();
        shopAppointUpdateVO.setAppointOrderId(1L);
        shopAppointUpdateVO.setVenderId(60461L);
        shopAppointUpdateVO.setCustomerName("111");
        shopAppointUpdateVO.setStaffId(23L);
        shopAppointUpdateVO.setAppointStartTime(AppointDateUtils.getStrToDate("yyyy-MM-dd hh:mm:ss","2018-08-34 16:11:11"));
        shopAppointUpdateVO.setAppointEndTime(AppointDateUtils.getStrToDate("yyyy-MM-dd hh:mm:ss","2018-08-34 17:11:11"));
        Map<String, String> params = new HashMap<>();
        params.put("name", "111");
        shopAppointUpdateVO.setBusinessCode("123");
        shopAppointUpdateVO.setFormItems(params);
        shopAppointUpdateVO.setServerType(2);
        shopAppointUpdateVO.setCustomerPhone("1111111");
        shopAppointUpdateVO.setOperateUserPin("gaoxiaoqing");
        System.out.println("params = " + JSON.toJSONString(params));
        SoaResponse soaResponse = shopAppointOrderFacade.editAppointOrder(new SoaRequest<>(shopAppointUpdateVO));
        System.out.println(soaResponse);
    }

    @Test
    public void testShopDynamicEditAppointOrder() {
        UpdateAppointVO updateAppointVO = new UpdateAppointVO();
        updateAppointVO.setAppointOrderId(5722L);
        updateAppointVO.setVenderId(48441L);
        updateAppointVO.setBusinessCode("1001");
        updateAppointVO.setVenderMemo("强哥最帅");
        SoaResponse soaResponse = shopAppointOrderFacade.dynamicUpdateAppoint(new SoaRequest<>(updateAppointVO));
        System.out.println(soaResponse);
    }

    @Test
    public void testAttachInfo() {
        UpdateAttachVO updateAttachVO = new UpdateAttachVO();
        updateAttachVO.setAppointOrderId(57L);
        updateAttachVO.setBusinessCode("1001");
        updateAttachVO.setVenderId(48441L);
        AttachVO attachVO = new AttachVO();
        attachVO.setAttrUrls("www.baidu.com");
        attachVO.setOverwrite(true);
        updateAttachVO.setAttachVO(attachVO);
        LogisticVO logisticVO = new LogisticVO();
        logisticVO.setLogisticsNo("2222");
        logisticVO.setLogisticsSiteId(11);
        updateAttachVO.setLogisticVO(logisticVO);
        updateAttachVO.setLoginUserPin("gaoxiaoqing");
        SoaResponse soaResponse = shopAppointOrderFacade.updateAttachInfo(new SoaRequest<>(updateAttachVO));
        System.out.println(soaResponse);
    }

    @Test
    public void testGetAppointOrderDetail() {
        SoaRequest<ShopAppointOrderQueryVO> soaRequest = new SoaRequest<>();
        ShopAppointOrderQueryVO shopAppointOrderQueryVO = new ShopAppointOrderQueryVO();
        shopAppointOrderQueryVO.setAppointOrderId(1L);
        shopAppointOrderQueryVO.setBusinessCode("123");
        Long venderId = 8888L;
        shopAppointOrderQueryVO.setVenderId(venderId);

        soaRequest.setData(shopAppointOrderQueryVO);
        SoaResponse<AppointOrderDetailVO> soaResponse = shopAppointOrderFacade.getAppointOrderDetail(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testGetDynamicAppointOrderDetail() {
        SoaRequest<DynamicShopAppointOrderQuery> soaRequest = new SoaRequest<>();
        DynamicShopAppointOrderQuery dynamicShopAppointOrderQuery = new DynamicShopAppointOrderQuery();
        dynamicShopAppointOrderQuery.setAppointOrderId(547L);
        dynamicShopAppointOrderQuery.setBusinessCode("101");
        dynamicShopAppointOrderQuery.setServerType(1);
        dynamicShopAppointOrderQuery.setVenderId(60461L);

        soaRequest.setData(dynamicShopAppointOrderQuery);
        SoaResponse<DynamicOrderDetailVO> soaResponse = shopAppointOrderFacade.dynamicGetAppointOrderDetail(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testOrderStatistic() {
        SoaRequest<OrderStatisticQuery> dateSoaRequest = new SoaRequest<>();
        OrderStatisticQuery vo = new OrderStatisticQuery();
        vo.setVenderId(48441l);
        vo.setDate(LocalDate.of(2018, 6, 26));
        dateSoaRequest.setData(vo);
        System.out.println(JSON.toJSONString(shopAppointOrderFacade.statisticByDate(dateSoaRequest)));
    }

    @Test
    public void Testcancel() {
        SoaRequest<AppointCancel> dateSoaRequest = new SoaRequest<AppointCancel>();
        AppointCancel appointCancel = new AppointCancel();
        appointCancel.setAppointOrderId(36L);
        appointCancel.setVenderId(123L);
        appointCancel.setBusinessCode("1");
        dateSoaRequest.setData(appointCancel);
        SoaResponse soaResponse = shopAppointOrderFacade.cancel(dateSoaRequest);
        System.out.println(soaResponse);
    }

    @Test
    public void testReschule() {

        ReschduleVO reschduleVO = new ReschduleVO();
        reschduleVO.setLoginUserPin("gaoxiaoqing5");
        Preconditions.checkNotNull(reschduleVO.getLoginUserPin());

        SoaRequest soaRequest = new SoaRequest();
        /*ReschduleVO reschduleVO = new ReschduleVO();*/
        reschduleVO.setAppointOrderId(1L);
        reschduleVO.setAppointStartTime(AppointDateUtils.getStrToDate("yyyy-MM-dd hh:mm:ss","2018-08-29 09:13:11"));
/*
        reschduleVO.setAppointEndTime(AppointDateUtils.getStrToDate("yyyy-MM-dd hh:mm:ss","2018-08-28 09:44:11"));

*/

        reschduleVO.setVenderId(60461L);
        reschduleVO.setBusinessCode("101");
        reschduleVO.setLoginUserPin("pro_pop_niesk");
        soaRequest.setData(reschduleVO);
        SoaResponse soaResponse = shopAppointOrderFacade.reschdule(soaRequest);
        System.out.println("reschule result = " + JSON.toJSONString(soaResponse));


    }

    //审核
    @Test
    public void testCheckOrder() {
        SoaRequest soaRequest = new SoaRequest();
        CheckOrderVO checkOrderVO = new CheckOrderVO();
        checkOrderVO.setVenderId(60461L);
        List<Long> appointOrderIds = new ArrayList<>();
        appointOrderIds.add(666L);
        appointOrderIds.add(665L);
        checkOrderVO.setAppointOrderIds(appointOrderIds);
        checkOrderVO.setLoginUserPin("test-xtlsj");
        checkOrderVO.setBusinessCode("101");
        soaRequest.setData(checkOrderVO);
        SoaResponse soaResponse = shopAppointOrderFacade.checkAppointOrder(soaRequest);
        System.out.println("result = " + JSON.toJSONString(soaResponse));
    }

    @Test
    public void testExportOrder() {
        SoaRequest<ShopAppointOrderListRequest> soaRequest = new SoaRequest<>();
        ShopAppointOrderListRequest shopAppointOrderListRequest = new ShopAppointOrderListRequest();
        shopAppointOrderListRequest.setServerType(1);
        shopAppointOrderListRequest.setBusinessCode("101");
        shopAppointOrderListRequest.setPageNumber(1);
        shopAppointOrderListRequest.setPageSize(10);
        shopAppointOrderListRequest.setVenderId(60461L);
        soaRequest.setData(shopAppointOrderListRequest);
        shopAppointOrderFacade.exportAppointOrders(soaRequest);
    }


    @Test
    public void testList() {
        SoaRequest<ShopAppointOrderListRequest> requestSoaRequest = new SoaRequest<>();
        ShopAppointOrderListRequest data = new ShopAppointOrderListRequest();
        data.setServerType(1);
        data.setVenderId(60461l);
        /*data.setBusinessCode("101");*/
        data.setPageNumber(1);
        data.setPageSize(10);
        requestSoaRequest.setData(data);
        shopAppointOrderFacade.dynamicList(requestSoaRequest);
    }
}
