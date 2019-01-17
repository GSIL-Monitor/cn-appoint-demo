package com.jd.appoint.service.order;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.rpc.query.MappingStatusQuery;
import org.junit.Test;
import webJunit.fast.FastJUnit4Tests;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * Created by gaoxiaoqing on 2018/5/8.
 */
public class TestAppointOrderMapping extends FastJUnit4Tests{

    @Resource
    private PopConfigService popConifgService;

    @Resource
    private AppointOrderService appointOrderService;

    @Test
    public void testOrderMapping(){

        MappingStatusQuery mappingStatusQuery = new MappingStatusQuery();
        mappingStatusQuery.setBusinessCode("1001");
/*
        popConifgService.queryStatusMappingList("101");
*/
        mappingStatusQuery.setAppointStatusEnum(AppointStatusEnum.APPOINT_CANCEL);
        mappingStatusQuery.setServerTypeEnum(ServerTypeEnum.DAODIAN);
        popConifgService.querySystemStatus(mappingStatusQuery);
    }

    @Test
    public void testStatticOrder(){
        LocalDate localDate = LocalDate.of(2018,6,26);
        System.out.println(JSON.toJSONString(appointOrderService.statisticByDate(localDate,
                localDate.plusDays(1l), 48441l)));
    }

    @Test
    public void testServerTypeInfo(){
        /*AppointServerInfo appointServerInfo = new AppointServerInfo();
        appointServerInfo.setBusinessCode("1");
        ServerTypeInfo serverTypeInfo = new ServerTypeInfo();
        serverTypeInfo.setToHomeName("到家1");
        serverTypeInfo.setToShopName("到店1");
        appointServerInfo.setServerTypeInfo(serverTypeInfo);
        System.out.println("result = " + JSON.toJSONString(appointServerInfo));*/
        popConifgService.getServerTypeName("1",1);
    }
}
