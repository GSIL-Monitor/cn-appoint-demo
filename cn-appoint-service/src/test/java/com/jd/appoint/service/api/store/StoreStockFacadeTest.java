package com.jd.appoint.service.api.store;

import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.storeapi.StoreStockFacade;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/7/3.
 */
public class StoreStockFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StoreStockFacadeTest.class);

    @Autowired
    private StoreStockFacade storeStockFacade;

    @Test
    public void testSaveOrUpdateStock(){
        storeStockFacade.saveOrUpdateStock(new SoaRequest<>(mockStockInfoVO()));
    }

    private StockInfoVO mockStockInfoVO(){
        StockInfoVO vo = new StockInfoVO();
        vo.setBusinessCode("101");
        vo.setVenderId(20160613L);
        vo.setStoreCode("129802");
        vo.setSkuId(6442961L);
        vo.setStartDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-09-10"));
        vo.setEndDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-10-10"));
        vo.setTotalQty(2);
        return vo;
    }
}
