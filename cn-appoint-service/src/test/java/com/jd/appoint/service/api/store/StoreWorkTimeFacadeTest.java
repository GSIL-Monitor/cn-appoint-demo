package com.jd.appoint.service.api.store;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.storeapi.StoreWorkTimeFacade;
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

/**
 * Created by luqiang3 on 2018/7/2.
 */
public class StoreWorkTimeFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StoreWorkTimeFacadeTest.class);

    @Autowired
    private StoreWorkTimeFacade storeWorkTimeFacade;

    @Test
    public void testSaveTime(){
        SoaRequest<WorkTime> soaRequest = new SoaRequest<>();
        soaRequest.setData(mockWorkTime());
        SoaResponse response = storeWorkTimeFacade.saveTime(soaRequest);
        logger.info("================SaveTime 返回结果：response={}", JSON.toJSONString(response));
    }

    @Test
    public void testSearchTime(){
        SoaRequest<WorkTimeQuery> soaRequest = new SoaRequest<>();
        soaRequest.setData(mockWorkTimeQuery());
        SoaResponse<WorkTime> response = storeWorkTimeFacade.searchTime(soaRequest);
        logger.info("=============SearchTime返回结果：response={}", JSON.toJSONString(response));
    }

    @Test
    public void testEditTime(){
        //查询出需要修改的数据
        SoaRequest<WorkTimeQuery> soaQuery = new SoaRequest<>();
        WorkTimeQuery query = new WorkTimeQuery();
        query.setBusinessCode("102");
        query.setVenderId(1111L);
        query.setStoreCode("123456");
        soaQuery.setData(query);
        SoaResponse<WorkTime> searchTime = storeWorkTimeFacade.searchTime(soaQuery);
        logger.info("修改前的信息：{}", JSON.toJSONString(searchTime.getResult()));
        SoaRequest<WorkTime> soaRequest = new SoaRequest<>();
        WorkTime workTime = searchTime.getResult();
        //workTime.setStartDay(0);
        workTime.setEndDay(4);
        //workTime.setTimeShowType(2);
        //workTime.getWorkTimeItems().get(0).setWorkStart("10:00");
        //workTime.getWorkTimeItems().get(1).setWorkEnd("22:00");
        soaRequest.setData(workTime);
        storeWorkTimeFacade.editTime(soaRequest);
        logger.info("修改后的信息：{}", JSON.toJSONString(searchTime.getResult()));
    }

    /**
     * 模拟服务时间信息
     * @return
     */
    private WorkTime mockWorkTime(){
        WorkTime workTime = new WorkTime();
        workTime.setBusinessCode("101");
        workTime.setVenderId(20160613L);
        workTime.setStartDay(30);
        workTime.setEndDay(90);
        workTime.setStoreCode("129802");
        workTime.setSkuId(6442961L);
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
        query.setBusinessCode("102");
        query.setVenderId(1111L);
        query.setStoreCode("123456");
        return query;
    }
}
