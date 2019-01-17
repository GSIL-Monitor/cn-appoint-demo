package com.jd.appoint.service.staffapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.shopapi.vo.OrderStatisticQuery;
import com.jd.appoint.shopapi.vo.ShopAppointOrderQueryVO;
import com.jd.appoint.shopapi.vo.ShopAppointUpdateVO;
import com.jd.appoint.stfapi.StfAppointOrderFacade;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Shop端预约信息
 * Created by gaoxiaoqing on 2018/5/16.
 */
public class StaffAppointOrderFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StaffAppointOrderFacadeTest.class);

    @Autowired
    private StfAppointOrderFacade stfAppointOrderFacade;

    @Test
    public void Testcancel() {
        SoaRequest<AppointCancel> soaRequest=new SoaRequest();
        AppointCancel appointCancel=new AppointCancel();
        appointCancel.setAppointOrderId(37L);
        //appointCancel.setStaffPin("wangyiwei1");
        appointCancel.setBusinessCode("1001");
        soaRequest.setData(appointCancel);
        SoaResponse soaResponse=stfAppointOrderFacade.cancel(soaRequest);
        System.out.println(soaResponse);
    }



}
