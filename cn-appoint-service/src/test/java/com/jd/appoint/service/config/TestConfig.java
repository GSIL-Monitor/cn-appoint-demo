package com.jd.appoint.service.config;

import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.rpc.AccessorInfo;
import com.jd.appoint.domain.rpc.AppointStatusInfo;
import com.jd.appoint.domain.rpc.ServiceTypeInfo;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.fastjson.JSONArray;
import org.junit.Test;
import webJunit.JUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gaoxiaoqing on 2018/5/7.
 */
public class TestConfig extends JUnit4SpringContextTests{

    private static final String APPOINT_STATUS_MAPPING = "appoint_status_mapping_";

    @Test
    public void testConfig(){
        String result =  ConfigData.getConfigValue(APPOINT_STATUS_MAPPING + "1");
        System.out.println("result = " + result);
    }

    //生成映射状态
    @Test
    public void generateStatusMapping() {
        List<SystemStatusInfo> systemStatusInfoList = new ArrayList();

        SystemStatusInfo systemStatusInfoAA = new SystemStatusInfo();
        systemStatusInfoAA.setSystemStatusCode(AppointStatusEnum.WAIT_ORDER.getIntValue());
        ServiceTypeInfo serviceTypeInfoAA = new ServiceTypeInfo();
        serviceTypeInfoAA.setToHomeStatusName("待提交");
        serviceTypeInfoAA.setToShopStatusName("待提交");
        systemStatusInfoAA.setServiceTypeInfo(serviceTypeInfoAA);
        systemStatusInfoList.add(systemStatusInfoAA);
        
        SystemStatusInfo systemStatusInfoA = new SystemStatusInfo();
        systemStatusInfoA.setSystemStatusCode(AppointStatusEnum.WAIT_ORDER.getIntValue());
        ServiceTypeInfo serviceTypeInfoA = new ServiceTypeInfo();
        serviceTypeInfoA.setToHomeStatusName("待审核");
        serviceTypeInfoA.setToShopStatusName("待审核");
        systemStatusInfoA.setServiceTypeInfo(serviceTypeInfoA);
        systemStatusInfoList.add(systemStatusInfoA);

        SystemStatusInfo systemStatusInfoB = new SystemStatusInfo();
        systemStatusInfoB.setSystemStatusCode(AppointStatusEnum.WAIT_SERVICE.getIntValue());
        ServiceTypeInfo serviceTypeInfoB = new ServiceTypeInfo();
        serviceTypeInfoB.setToHomeStatusName("待发货");
        serviceTypeInfoB.setToShopStatusName("待自提");
        systemStatusInfoB.setServiceTypeInfo(serviceTypeInfoB);
        systemStatusInfoList.add(systemStatusInfoB);

        SystemStatusInfo systemStatusInfoC = new SystemStatusInfo();
        systemStatusInfoC.setSystemStatusCode(AppointStatusEnum.APPOINT_FINISH.getIntValue());
        ServiceTypeInfo serviceTypeInfoC = new ServiceTypeInfo();
        serviceTypeInfoC.setToHomeStatusName("已完成");
        serviceTypeInfoC.setToShopStatusName("已完成");
        systemStatusInfoC.setServiceTypeInfo(serviceTypeInfoC);
        systemStatusInfoList.add(systemStatusInfoC);

        SystemStatusInfo systemStatusInfoD = new SystemStatusInfo();
        systemStatusInfoD.setSystemStatusCode(AppointStatusEnum.APPOINT_CANCEL.getIntValue());
        ServiceTypeInfo serviceTypeInfoD = new ServiceTypeInfo();
        serviceTypeInfoD.setToHomeStatusName("已取消");
        serviceTypeInfoD.setToShopStatusName("已取消");
        systemStatusInfoD.setServiceTypeInfo(serviceTypeInfoD);
        systemStatusInfoList.add(systemStatusInfoD);

        SystemStatusInfo systemStatusInfoE = new SystemStatusInfo();
        systemStatusInfoE.setSystemStatusCode(AppointStatusEnum.APPOINT_FAILURE.getIntValue());
        ServiceTypeInfo serviceTypeInfoE = new ServiceTypeInfo();
        serviceTypeInfoE.setToHomeStatusName("预约失败");
        serviceTypeInfoE.setToShopStatusName("预约失败");
        systemStatusInfoE.setServiceTypeInfo(serviceTypeInfoE);
        systemStatusInfoList.add(systemStatusInfoE);

        SystemStatusInfo systemStatusInfoF = new SystemStatusInfo();
        systemStatusInfoF.setSystemStatusCode(AppointStatusEnum.RESCHEDULING.getIntValue());
        ServiceTypeInfo serviceTypeInfoF = new ServiceTypeInfo();
        serviceTypeInfoF.setToHomeStatusName("改期中");
        serviceTypeInfoF.setToShopStatusName("改期中");
        systemStatusInfoF.setServiceTypeInfo(serviceTypeInfoF);
        systemStatusInfoList.add(systemStatusInfoF);

        SystemStatusInfo systemStatusInfoG = new SystemStatusInfo();
        systemStatusInfoG.setSystemStatusCode(AppointStatusEnum.CANCELING.getIntValue());
        ServiceTypeInfo serviceTypeInfoG = new ServiceTypeInfo();
        serviceTypeInfoG.setToHomeStatusName("取消中");
        serviceTypeInfoG.setToShopStatusName("取消中");
        systemStatusInfoG.setServiceTypeInfo(serviceTypeInfoG);
        systemStatusInfoList.add(systemStatusInfoG);

        AppointStatusInfo appointStatusInfo = new AppointStatusInfo();
        appointStatusInfo.setSystemStatusInfoList(systemStatusInfoList);

        System.out.println("result = " + JSONArray.toJSONString(appointStatusInfo));
    }
}
