package com.jd.appoint.service.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.api.AppointOrderFacade;
import com.jd.appoint.api.vo.ApiReschuleVO;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.order.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

public class AppointOrderFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(AppointOrderFacadeTest.class);

    private Attach attach;
    @Autowired
    private AppointOrderFacade appointOrderFacade;

    @Before
    public void init() {
        attach = new Attach();
        attach.setDesc("插入附件");
        attach.setOverwrite(true);
        attach.setUrls("1111,2323");
    }

    @Test
    public void testGetAppointOrderDetail() {
        SoaRequest<AppointOrderQueryVO> soaRequest = new SoaRequest<>();
        AppointOrderQueryVO appointOrderQueryVO = new AppointOrderQueryVO();
        appointOrderQueryVO.setAppointOrderId(1L);
        String userPin = "JD234";
        appointOrderQueryVO.setCustomerUserPin(userPin);

        soaRequest.setData(appointOrderQueryVO);
        logger.info("=======================" + JSONArray.toJSON(soaRequest));
        SoaResponse<AppointOrderDetailVO> soaResponse = appointOrderFacade.getAppointOrderDetail(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void Testfinished() {
        SoaRequest<AppointFinishVO> soaRequest = new SoaRequest<>();
        AppointFinishVO appointFinishVO = new AppointFinishVO();
        appointFinishVO.setAppointOrderId(42L);
        appointFinishVO.setBusinessCode("1001");
        attach.setUrls("23232");
        attach.setAppointOrderId(42L);
        attach.setOverwrite(false);
        appointFinishVO.setAttach(attach);
        appointFinishVO.setOperateUser("gaoxiaoqing");
        soaRequest.setData(appointFinishVO);
        SoaResponse soaResponse = appointOrderFacade.finished(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }


    @Test
    public void Testcancel() {
        SoaRequest<AppointCancel> soaRequest = new SoaRequest<>();
        AppointCancel appointCancel = new AppointCancel();
        appointCancel.setAppointOrderId(694L);
        //appointCancel.setCancelReason("sss");
        appointCancel.setBusinessCode("1001");
        appointCancel.setUserPin("jd_4e894d8092a73");
        /*appointCancel.setVenderId(48441L);*/
        soaRequest.setData(appointCancel);
        SoaResponse<AppointOrderDetailVO> soaResponse = appointOrderFacade.cancel(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void TestsubmitAttach() {
        SoaRequest<Attach> soaRequest = new SoaRequest<>();
        attach.setAppointOrderId(3L);
        soaRequest.setData(attach);
        //SoaResponse<AppointOrderDetailVO> soaResponse = appointOrderFacade.submitAttach(soaRequest);
        //logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }
    @Test
    public void testDynamicOrderList() {
        SoaRequest soaRequest = new SoaRequest();
        ApiAppointOrderListRequest apiAppointOrderListRequest = new ApiAppointOrderListRequest();
        apiAppointOrderListRequest.setCustomerUserPin("jd_zdm");
        apiAppointOrderListRequest.setBusinessCode("101");
        apiAppointOrderListRequest.setPageNumber(1);
        apiAppointOrderListRequest.setPageSize(5);
        soaRequest.setData(apiAppointOrderListRequest);

        appointOrderFacade.dynamicAppointList(soaRequest);
    }

    @Test
    public void testAppointList(){
        SoaRequest soaRequest = new SoaRequest();
        AppointOrderListRequest appointOrderListRequest = new AppointOrderListRequest();
        appointOrderListRequest.setCustomerUserPin("jd_44d2fc994a4c9");
        appointOrderListRequest.setBusinessCode("101");
        appointOrderListRequest.setPageNumber(2);
        appointOrderListRequest.setPageSize(10);
        soaRequest.setData(appointOrderListRequest);

        appointOrderFacade.appointList(soaRequest);
    }

    @Test
    public void testReschdule(){
        SoaRequest<ApiReschuleVO> request = new SoaRequest<>();
        ApiReschuleVO vo = new ApiReschuleVO();
        vo.setAppointOrderId(1L);
        vo.setUserPin("JD234");
        vo.setAppointStartTime(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-10-09"));
        //vo.setAppointEndTime();
        request.setData(vo);
        SoaResponse<AppointOrderResult> reschdule = appointOrderFacade.reschdule(request);
        logger.info("改期返回结果：reschdule={}", JSON.toJSONString(reschdule));
    }

    @Test
    public void finish() {
        SoaRequest<AppointFinishVO> soaRequest = new SoaRequest<>();
        AppointFinishVO finishVO = new AppointFinishVO();
        finishVO.setAppointOrderId(551L);
        finishVO.setBusinessCode("1001");
        finishVO.setEndOrderId(56289214657L);
        Attach attach = new Attach();
        attach.setAppointOrderId(551L);
        attach.setUrls("https://ttimgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529920210641&di=5baf76c451e89700ec9c90834e3789ee&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fupload%2F20170811%2Fd0221d83575341a8807118df02b14a91_th.png");
        attach.setOverwrite(true);
        finishVO.setAttach(attach);
        soaRequest.setData(finishVO);
        appointOrderFacade.finished(soaRequest);
    }
}
