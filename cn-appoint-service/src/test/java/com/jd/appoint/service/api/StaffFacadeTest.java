package com.jd.appoint.service.api;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.service.shopapi.ShopStaffFacadeTest;
import com.jd.appoint.stfapi.StaffFacade;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

public class StaffFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopStaffFacadeTest.class);

    @Autowired
    private StaffFacade staffFacade;

    @Test
    public void testGetStaffDetailByUserPin() {
        SoaRequest<String> soaRequest = new SoaRequest<>();
        String userPin = "JD3465q7";

        soaRequest.setData(userPin);
        SoaResponse<ShopStaffVO> soaResponse = staffFacade.getStaffDetailByUserPin(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testStaffLogin() {
        SoaRequest<String> soaRequest = new SoaRequest<>();

        String userPin = "lsw_test";

        soaRequest.setData(userPin);
        SoaResponse soaResponse = staffFacade.staffLogin(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }
}
