package com.jd.appoint.service.shop;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.enums.ProcessTypeEnum;
import com.jd.appoint.service.record.BuriedPointService;
import com.jd.appoint.service.util.BuriedPointUtil;
import com.jd.appoint.shopapi.ShopAppointRecordFacade;
import com.jd.appoint.shopapi.vo.AppointRecordQueryVO;
import com.jd.appoint.shopapi.vo.AppointRecordVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.xn.footprint.client.FootPrint;
import org.junit.Test;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoxiaoqing on 2018/5/16.
 */
public class ShopAppointRecordServiceTest extends JUnit4SpringContextTests{

    @Resource
    private ShopAppointRecordFacade shopAppointRecordFacade;
    @Resource
    private BuriedPointService buriedPointService;


    @Test
    public void getAppointRecordInfo(){
        SoaRequest<AppointRecordQueryVO> soaRequest = new SoaRequest<>();
        AppointRecordQueryVO appointRecordQueryVO = new AppointRecordQueryVO();
        appointRecordQueryVO.setAppointOrderId("259");
        soaRequest.setData(appointRecordQueryVO);
        System.out.println("request = " + JSON.toJSONString(soaRequest));
        SoaResponse<List<AppointRecordVO>> soaResponse = shopAppointRecordFacade.getAppointRecordInfo(soaRequest);
        System.out.println("result = " + JSON.toJSONString(soaResponse));



        /*String content1 = BuriedPointUtil.getBurryContent(ProcessTypeEnum.NEW_APPOINT_ORDER.getCode(),null);
        buriedPointService.appointBuried("259", content1, BuriedSourceEnum.SHOP.getCode(), BuriedRecordEnum.APPOINT_PROCESS.getCode());
        String content2 = BuriedPointUtil.getBurryContent(ProcessTypeEnum.CANCEL_APPOINT_ORDER.getCode(),null);
        buriedPointService.appointBuried("260", content2, BuriedSourceEnum.SHOP.getCode(), BuriedRecordEnum.APPOINT_PROCESS.getCode());
        Map<String,String> param = new HashMap<>();
        param.put("staffName","小伟");
        param.put("appointTime","2018-01-01 12:00-13:00");
        String content3 = BuriedPointUtil.getBurryContent(ProcessTypeEnum.WAIT_SERVICE.getCode(),param);
        buriedPointService.appointBuried("261", content3, BuriedSourceEnum.SHOP.getCode(), BuriedRecordEnum.APPOINT_PROCESS.getCode());*/

    }
}
