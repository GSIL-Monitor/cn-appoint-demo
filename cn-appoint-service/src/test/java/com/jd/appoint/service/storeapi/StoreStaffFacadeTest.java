package com.jd.appoint.service.storeapi;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.storeapi.StoreStaffFacade;
import com.jd.appoint.storeapi.StoreWorkTimeFacade;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.staff.*;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created by luqiang3 on 2018/7/2.
 */
public class StoreStaffFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StoreStaffFacadeTest.class);

    @Resource
    private StoreStaffFacade storeStaffFacade;

    @Test
    public void testGetShopStaffListByCondition() {
        SoaRequest<StoreStaffQueryVO> soaRequest = new SoaRequest<>();
        StoreStaffQueryVO shopStaffQueryVO = new StoreStaffQueryVO();
        shopStaffQueryVO.setVenderId(48441L);
        shopStaffQueryVO.setStoreId(1L);
        /*Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(3);*/
        //shopStaffQueryVO.setPage(page);
        soaRequest.setData(shopStaffQueryVO);
        SoaResponse<Page<ShopStaffVO>> soaResponse = storeStaffFacade.getStaffListByCondition(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testGetShopStaffDetail() {
        SoaRequest<StaffIdVenderIdStoreIdVO> soaRequest = new SoaRequest<>();
        StaffIdVenderIdStoreIdVO shopStaffIdVenderIdVO = new StaffIdVenderIdStoreIdVO();
        shopStaffIdVenderIdVO.setId(29L);
        shopStaffIdVenderIdVO.setVenderId(48441L);
        shopStaffIdVenderIdVO.setStoreId(1L);
        soaRequest.setData(shopStaffIdVenderIdVO);
        SoaResponse<ShopStaffVO> soaResponse = storeStaffFacade.getStaffDetail(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testSaveShopStaff() {

        SoaRequest<ShopStaffVO> soaRequest = new SoaRequest<>();
        ShopStaffVO shopStaffVO = new ShopStaffVO();
        shopStaffVO.setVenderId(48441L);
        shopStaffVO.setStoreId(1L);
        shopStaffVO.setServerName("李帅伟测试");
        shopStaffVO.setServerPhone("13718225154");
        shopStaffVO.setSecurityId("abcde8");
        soaRequest.setData(shopStaffVO);
        SoaResponse soaResponse = storeStaffFacade.addStaff(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));


    }

    @Test
    public void testEditShopStaff() {
        SoaRequest<ShopStaffVO> soaRequest = new SoaRequest<>();
        ShopStaffVO shopStaffVO = new ShopStaffVO();
        shopStaffVO.setId(152L);
        shopStaffVO.setVenderId(48441L);
        shopStaffVO.setStoreId(1L);
        shopStaffVO.setServerName("李帅伟测试7");
        shopStaffVO.setServerPhone("18801232142");
        shopStaffVO.setUserPin("JD3465q7");
        shopStaffVO.setSecurityId("abcde7");
        soaRequest.setData(shopStaffVO);
        SoaResponse soaResponse = storeStaffFacade.editStaff(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testDeleteStaff() {
        SoaRequest<StaffIdVenderIdStoreIdVO> soaRequest = new SoaRequest<>();
        StaffIdVenderIdStoreIdVO shopStaffIdVenderIdVO = new StaffIdVenderIdStoreIdVO();
        shopStaffIdVenderIdVO.setId(29L);
        shopStaffIdVenderIdVO.setVenderId(8888L);
        soaRequest.setData(shopStaffIdVenderIdVO);
        SoaResponse<ShopStaffVO> soaResponse = storeStaffFacade.deleteStaff(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }
}
