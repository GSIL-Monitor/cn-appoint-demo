package com.jd.appoint.service.api.shop;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.shopapi.ShopWorkTimeFacade;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeItem;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/5.
 */
public class ShopWorkTimeFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopWorkTimeFacadeTest.class);

    @Autowired
    private ShopWorkTimeFacade shopWorkTimeFacade;

    @Test
    public void testSaveTime(){
        SoaRequest<WorkTime> soaRequest = new SoaRequest<>();
        WorkTime workTime = null;
        workTime = mockFuZhuangWorkTime();
        //workTime = mockDaZhaXieWorkTime();
        soaRequest.setData(workTime);
        SoaResponse soaResponse = shopWorkTimeFacade.saveTime(soaRequest);
        logger.info("saveTime response.........{}", JSON.toJSONString(soaResponse));
    }

    @Test
    public void testSearchTime(){
        SoaRequest<WorkTimeQuery> soaRequest = new SoaRequest<>();
        WorkTimeQuery workTimeQuery = mockWorkTimeQuery();
        soaRequest.setData(workTimeQuery);
        SoaResponse<WorkTime> workTimeSoaResponse = shopWorkTimeFacade.searchTime(soaRequest);
        logger.info("searchTime response.................{}", JSON.toJSONString(workTimeSoaResponse));
    }

    @Test
    public void testEditTime(){
        //查询出需要修改的数据
        SoaRequest<WorkTimeQuery> soaQuery = new SoaRequest<>();
        WorkTimeQuery query = new WorkTimeQuery();
        query.setBusinessCode("101");
        query.setVenderId(1115L);
        query.setSkuId(8888L);
        soaQuery.setData(query);
        SoaResponse<WorkTime> searchTime = shopWorkTimeFacade.searchTime(soaQuery);
        logger.info("修改前的信息：{}", JSON.toJSONString(searchTime.getResult()));
        SoaRequest<WorkTime> soaRequest = new SoaRequest<>();
        WorkTime workTime = searchTime.getResult();
        //workTime.setStartDay(0);
        workTime.setEndDay(90);
        //workTime.setTimeShowType(2);
        //workTime.getWorkTimeItems().get(0).setWorkStart("10:00");
        //workTime.getWorkTimeItems().get(1).setWorkEnd("22:00");
        soaRequest.setData(workTime);
        shopWorkTimeFacade.editTime(soaRequest);
        logger.info("修改后的信息：{}", JSON.toJSONString(searchTime.getResult()));
    }

    /**
     * 模拟服装服务时间信息
     * @return
     */
    private WorkTime mockFuZhuangWorkTime(){
        WorkTime workTime = new WorkTime();
        workTime.setBusinessCode("12345645");
        workTime.setVenderId(1111L);
        workTime.setStartDay(0);
        workTime.setEndDay(10);
        workTime.setT0Advance(120);
        workTime.setT0AdvanceUnit(1);//默认1，分钟
        workTime.setTimeInterval(60);
        workTime.setTimeIntervalUnit(1);//默认1，分钟
        workTime.setTimeShowType(1);
        workTime.setWorkTimeItems(mockFuZhuangWorkTimeItems());
        return workTime;
    }

    /**
     * 模拟服装服务时间项集合信息
     * @return
     */
    private List<WorkTimeItem> mockFuZhuangWorkTimeItems(){
        List<WorkTimeItem> workTimeItems = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            workTimeItems.add(mockWorkTimeItem(i + 1));
        }
        return workTimeItems;
    }

    /**
     * 模拟大闸蟹服务时间信息
     * @return
     */
    private WorkTime mockDaZhaXieWorkTime(){
        WorkTime workTime = new WorkTime();
        workTime.setBusinessCode("101");
        workTime.setVenderId(1115L);
        workTime.setSkuId(6442961L);
        workTime.setStartDay(0);
        workTime.setEndDay(90);
        workTime.setTimeShowType(TimeShowTypeEnum.DAY.getIntValue());
        return workTime;
    }

    /**
     * 模拟服务时间项信息
     * @param weekday
     * @return
     */
    private WorkTimeItem mockWorkTimeItem(Integer weekday){
        WorkTimeItem workTimeItem = new WorkTimeItem();
        workTimeItem.setWeekday(weekday);
        workTimeItem.setWorkStart("09:00");
        workTimeItem.setWorkEnd("09:00");
        workTimeItem.setStatus(1);
        return workTimeItem;
    }

    /**
     * 模拟服务时间查询
     * @return
     */
    private WorkTimeQuery mockWorkTimeQuery(){
        WorkTimeQuery query = new WorkTimeQuery();
        query.setBusinessCode("101");
        query.setVenderId(1113L);
        return query;
    }
}
