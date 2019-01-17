package com.jd.appoint.service.order;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.stfapi.StaffFacade;
import com.jd.appoint.stfapi.StfAppointOrderFacade;
import com.jd.appoint.stfapi.vo.StaffAppointOrderListRequest;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.order.FilterItemVO;
import com.jd.appoint.vo.order.FilterOperator;
import com.jd.appoint.vo.order.ShopAppointOrderListRequest;
import com.jd.appoint.vo.page.Page;
import com.jd.fastjson.JSON;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by gaoxiaoqing on 2018/5/8.
 */
public class TestEsOrderService extends JUnit4SpringContextTests {

    @Resource
    private EsOrderService esOrderService;
    @Autowired
    private StfAppointOrderFacade appointOrderFacade;
    @Autowired
    private AppointOrderService appointOrderService;

    @Test
    public void testIndex() {
        for (long i = 248; i < 280; i++) {
            try{
                AppointOrderDetailVO detail = appointOrderService.detail(i);
                if (detail != null) {
                    esOrderService.index(detail);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testList() {
        StaffAppointOrderListRequest request = new StaffAppointOrderListRequest();
        request.setStaffUserPin("jjlin_0");
        request.setBusinessCode("1001");
        request.setPageSize(10);
        request.setPageNumber(1);
        appointOrderFacade.list(new SoaRequest<>(request));
    }
}
