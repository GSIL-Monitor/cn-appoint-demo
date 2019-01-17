package com.jd.appoint.service.stock;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.StockOptStatusEnum;
import com.jd.appoint.domain.stock.StockOperatePO;
import com.jd.appoint.domain.stock.query.StockOperateQuery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 */
public class StockOperateServiceTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StockOperateServiceTest.class);

    @Autowired
    private StockOperateService stockOperateService;

    @Test
    public void testInsert(){
        stockOperateService.insert(mockStockOperatePO());
    }

    @Test
    public void testQuerySelective(){
        List<StockOperatePO> stockOperatePOs = stockOperateService.querySelective(mockStockOperateQuery());
        logger.info("===============查询库存操作信息结果：{}", JSON.toJSONString(stockOperatePOs));
    }

    /**
     * mock 库存操作信息
     * @return
     */
    private StockOperatePO mockStockOperatePO(){
        StockOperatePO stockOperatePO = new StockOperatePO();
        stockOperatePO.setAppointOrderId(123L);
        stockOperatePO.setStockStatus(StockOptStatusEnum.DECREASE);
        stockOperatePO.setStatus(StatusEnum.ENABLE);
        return stockOperatePO;
    }

    /**
     * mock 库存操作查询信息
     * @return
     */
    private StockOperateQuery mockStockOperateQuery(){
        StockOperateQuery query = new StockOperateQuery();
        query.setAppointOrderId(123L);
        return query;
    }
}
