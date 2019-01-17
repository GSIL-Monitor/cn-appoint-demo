package com.jd.appoint.service.outerapi;

import com.alibaba.fastjson.JSON;
import com.jd.virtual.appoint.ScheduleGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.schedule.Schedule;
import com.jd.virtual.appoint.schedule.UpdateScheduleRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/5/11.
 */
public class ScheduleServiceTest extends JUnit4SpringContextTests {

    private Logger logger = LoggerFactory.getLogger(ScheduleServiceTest.class);

    @Autowired
    private ScheduleGwService scheduleGwService;

    @Test
    public void testGetScheduleList() {
        CommonRequest request = new CommonRequest();
        request.setContextId("aec792d6-96a7-4d4e-9ad7-b8ead9621850");
        logger.info("=================request={}", JSON.toJSONString(request));
        CommonResponse<Schedule> scheduleList = scheduleGwService.getScheduleList(request);
        logger.info("test getScheduleList .............response={}", JSON.toJSONString(scheduleList));
    }

    @Test
    public void testGetScheduleListByOrder(){
        UpdateScheduleRequest request = new UpdateScheduleRequest();
        request.setOrderId(377l);
        request.setUserPin("jd_zdm");
        //request.setStartDate("2018-07-29");
        //request.setEndDate("2018-09-08");
        request.setVenderId(20160613L);
        request.setBusinessCode("101");
        CommonResponse<Schedule> scheduleListByOrder = scheduleGwService.getScheduleListByOrder(request);
        logger.info("====================response={}", JSON.toJSONString(scheduleListByOrder));
    }
}
