package com.jd.appoint.service.api.shop;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.order.Attach;
import com.jd.appoint.vo.order.MailInformation;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

public class ShopAppointOrderFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopAppointOrderFacadeTest.class);

    private Attach attach;
    @Autowired
    private ShopAppointOrderFacade shopAppointOrderFacade;

    @Before
    public void init() {
        attach = new Attach();
        attach.setDesc("插入附件");
        attach.setOverwrite(true);
        attach.setUrls("1111,2323");
    }


    @Test
    public void testfinished() {
        SoaRequest<AppointFinishVO> soaRequest = new SoaRequest<>();
        AppointFinishVO appointFinishVO = new AppointFinishVO();
        appointFinishVO.setAppointOrderId(468L);
        appointFinishVO.setBusinessCode("101");
        appointFinishVO.setOperateUser("鲁强");
        MailInformation mailInformation = new MailInformation();
        mailInformation.setLogisticsNo("123456");
        mailInformation.setLogisticsSiteId(2009);
        mailInformation.setLogisticsSource("天天快递");
        /*attach.setUrls("23232");
        attach.setAppointOrderId(1L);
        attach.setOverwrite(false);
        appointFinishVO.setAttach(attach);*/
        appointFinishVO.setMailInformation(mailInformation);
        soaRequest.setData(appointFinishVO);
        SoaResponse soaResponse = shopAppointOrderFacade.finished(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }


    @Test
    public void Testcancel() {
        SoaRequest<AppointCancel> soaRequest = new SoaRequest<>();
        AppointCancel appointCancel = new AppointCancel();
        appointCancel.setAppointOrderId(3L);
        appointCancel.setCancelReason("sss");
        appointCancel.setBusinessCode("1");
        appointCancel.setUserPin("111");
        appointCancel.setVenderId(1L);
        soaRequest.setData(appointCancel);
        SoaResponse<AppointOrderDetailVO> soaResponse = shopAppointOrderFacade.cancel(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

}
