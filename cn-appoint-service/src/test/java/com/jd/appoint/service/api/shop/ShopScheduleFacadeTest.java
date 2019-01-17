package com.jd.appoint.service.api.shop;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.shopapi.ShopScheduleFacade;
import com.jd.appoint.vo.schedule.ScheduleModel;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/6/15.
 */
public class ShopScheduleFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopScheduleFacadeTest.class);

    @Autowired
    private ShopScheduleFacade shopScheduleFacade;

    @Test
    public void testSearchSchedules(){
        SoaResponse<ScheduleResult> scheduleResult = shopScheduleFacade.searchSchedules(new SoaRequest<>(mockScheduleVO()));
        logger.info("查询产能日历结果：{}", JSON.toJSONString(scheduleResult));
    }

    @Test
    public void testSearchScheduleModel(){
        SoaResponse<ScheduleModel> response = shopScheduleFacade.searchScheduleModel(new SoaRequest<>("101"));
        logger.info("查询产能日历模式结果：{}", JSON.toJSONString(response));
    }

    /**
     *
     * @return
     */
    private ScheduleVO mockScheduleVO(){
        ScheduleVO vo = new ScheduleVO();
        vo.setBusinessCode("101");
        vo.setVenderId(20160613L);
        //vo.setStoreCode("-1");
        //vo.setSkuId(-1L);
        vo.setStartDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-07-01"));
        vo.setEndDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-07-31"));
        return vo;
    }
}
