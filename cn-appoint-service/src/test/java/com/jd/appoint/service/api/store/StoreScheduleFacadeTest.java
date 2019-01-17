package com.jd.appoint.service.api.store;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.storeapi.StoreScheduleFacade;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.Date;

/**
 * Created by luqiang3 on 2018/7/3.
 */
public class StoreScheduleFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StoreScheduleFacadeTest.class);

    @Autowired
    private StoreScheduleFacade storeScheduleFacade;

    @Test
    public void testSearchSchedules(){
        SoaResponse<ScheduleResult> scheduleResult = storeScheduleFacade.searchSchedules(new SoaRequest<>(mockScheduleVO()));
        logger.info("查询产能日历结果：{}", JSON.toJSONString(scheduleResult));
    }

    /**
     *
     * @return
     */
    private ScheduleVO mockScheduleVO(){
        ScheduleVO vo = new ScheduleVO();
        vo.setBusinessCode("101");
        vo.setVenderId(124L);
        vo.setStoreCode("-1");
        vo.setSkuId(-1L);
        vo.setStartDate(new Date());
        vo.setEndDate(AppointDateUtils.addDays(new Date(), 10));
        return vo;
    }
}
