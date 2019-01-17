package com.jd.appoint.service.shopapi;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.shopapi.ShopStaffFacade;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.staff.ShopStaffIdVenderIdVO;
import com.jd.appoint.vo.staff.ShopStaffQueryVO;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lishuaiwei on 2018/5/5.
 */
public class ShopStaffFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopStaffFacadeTest.class);

    @Autowired
    private ShopStaffFacade shopStaffFacade;

    @Test
    public void testGetShopStaffListByCondition() {
        SoaRequest<ShopStaffQueryVO> soaRequest = new SoaRequest<>();
        ShopStaffQueryVO shopStaffQueryVO = new ShopStaffQueryVO();
        shopStaffQueryVO.setVenderId(1L);
        /*Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(3);*/
        //shopStaffQueryVO.setPage(page);
        soaRequest.setData(shopStaffQueryVO);
        SoaResponse<Page<ShopStaffVO>> soaResponse = shopStaffFacade.getStaffListByCondition(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testGetShopStaffDetail() {
        SoaRequest<ShopStaffIdVenderIdVO> soaRequest = new SoaRequest<>();
        ShopStaffIdVenderIdVO shopStaffIdVenderIdVO = new ShopStaffIdVenderIdVO();
        shopStaffIdVenderIdVO.setId(151L);
        shopStaffIdVenderIdVO.setVenderId(48441L);
        soaRequest.setData(shopStaffIdVenderIdVO);
        SoaResponse<ShopStaffVO> soaResponse = shopStaffFacade.getStaffDetail(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testSaveShopStaff() {

        SoaRequest<ShopStaffVO> soaRequest = new SoaRequest<>();
        ShopStaffVO shopStaffVO = new ShopStaffVO();
        shopStaffVO.setVenderId(48441L);
        shopStaffVO.setServerName("李帅伟测试");
        shopStaffVO.setServerPhone("13718225650");
        shopStaffVO.setSecurityId("abcde8");
        soaRequest.setData(shopStaffVO);
        SoaResponse soaResponse = shopStaffFacade.addStaff(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));


    }

    @Test
    public void testEditShopStaff() {
        SoaRequest<ShopStaffVO> soaRequest = new SoaRequest<>();
        ShopStaffVO shopStaffVO = new ShopStaffVO();
        shopStaffVO.setId(152L);
        shopStaffVO.setVenderId(48441L);
        shopStaffVO.setServerName("李帅伟测试7");
        shopStaffVO.setServerPhone("18801232152");
        shopStaffVO.setUserPin("JD3465q7");
        shopStaffVO.setSecurityId("abcde7");
        soaRequest.setData(shopStaffVO);
        SoaResponse soaResponse = shopStaffFacade.editStaff(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testDeleteStaff() {
        SoaRequest<ShopStaffIdVenderIdVO> soaRequest = new SoaRequest<>();
        ShopStaffIdVenderIdVO shopStaffIdVenderIdVO = new ShopStaffIdVenderIdVO();
        shopStaffIdVenderIdVO.setId(29L);
        shopStaffIdVenderIdVO.setVenderId(8888L);
        soaRequest.setData(shopStaffIdVenderIdVO);
        SoaResponse<ShopStaffVO> soaResponse = shopStaffFacade.deleteStaff(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }

    @Test
    public void testGetStaffListByVenderId() {
        SoaRequest<Long> soaRequest = new SoaRequest<>();
        Long venderId = 1L;
        soaRequest.setData(venderId);
        SoaResponse<List<ShopStaffVO>> soaResponse = shopStaffFacade.getStaffListByVenderId(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));

    }
}
