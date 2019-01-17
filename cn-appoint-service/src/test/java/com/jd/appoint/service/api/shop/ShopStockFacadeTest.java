package com.jd.appoint.service.api.shop;

import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.shopapi.ShopStockFacade;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/6/15.
 */
public class ShopStockFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopStockFacadeTest.class);

    @Autowired
    private ShopStockFacade shopStockFacade;

    @Test
    public void testSaveOrUpdateStock(){
        shopStockFacade.saveOrUpdateStock(new SoaRequest<>(mockStockInfoVO()));
    }

    private StockInfoVO mockStockInfoVO(){
        StockInfoVO vo = new StockInfoVO();
        vo.setBusinessCode("101");
        vo.setVenderId(60461L);
//        vo.setStoreId(-1L);
        vo.setSkuId(6442962L);
        vo.setStartDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-07-30"));
        vo.setEndDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-07-30"));
        vo.setTotalQty(101);
        return vo;
    }
}
