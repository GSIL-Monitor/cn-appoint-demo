package com.jd.appoint.service.stock;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.stock.Stock;
import com.jd.appoint.domain.stock.StockInfoPO;
import com.jd.appoint.domain.stock.query.StockInfoQuery;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.Date;
import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 */
public class StockInfoServiceTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StockInfoServiceTest.class);

    @Autowired
    private StockInfoService stockInfoService;

    @Test
    public void testInsert(){
        Date currentDate = new Date();
        for(int i = 0; i < 10; i++){
            String dateStr = AppointDateUtils.getDate2Str("yyyy-MM-dd", currentDate);
            StockInfoPO stockInfoPO = mockStockInfoPO(dateStr);
            stockInfoService.insert(stockInfoPO);
            currentDate = AppointDateUtils.addDays(currentDate, 1);
        }
    }

    @Test
    public void testQuerySelective(){
        List<StockInfoPO> stockInfoPOs = stockInfoService.querySelective(mockStockInfoQuery());
        logger.info("=============QuerySelective 查询库存返回结果：{}", JSON.toJSONString(stockInfoPOs));
    }

    @Test
    public void testUpdateByPrimaryKeySelective(){
        List<StockInfoPO> stockInfoPOs = stockInfoService.querySelective(mockStockInfoQuery());
        if(CollectionUtils.isEmpty(stockInfoPOs)){
            logger.info("============UpdateByPrimaryKeySelective 未查询到数据");
            return;
        }
        StockInfoPO stockInfoPO = stockInfoPOs.get(0);
        logger.info("============UpdateByPrimaryKeySelective 更新前数据：{}", JSON.toJSONString(stockInfoPO));
        stockInfoPO.setSaleQty(stockInfoPO.getSaleQty() + 1);//模拟扣减库存操作
        int result = stockInfoService.updateByPrimaryKeySelective(stockInfoPO);
        logger.info("============UpdateByPrimaryKeySelective 更新结果：{}，更新后数据：{}", result, JSON.toJSONString(stockInfoPO));
    }

    @Test
    public void testDecreaseStock(){
        OperateResultEnum decreaseResult = stockInfoService.decreaseStock(mockStock());
        logger.info("扣减库存结果：decreaseResult={}", decreaseResult);
    }

    @Test
    public void testIncreaseStock(){
        OperateResultEnum increaseResult = stockInfoService.increaseStock(mockStock());
        logger.info("回冲库存结果：decreaseResult={}", increaseResult);
    }

    @Test
    public void testChangeSchedule(){
        OperateResultEnum changeResult = stockInfoService.changeStock(mockStock());
        logger.info("改期结果：changeResult={}", changeResult);
    }

    @Test
    public void testInit(){
        stockInfoService.init();
    }

    /**
     * mock 库存信息数据
     * @param date
     * @return
     */
    private StockInfoPO mockStockInfoPO(String date){
        StockInfoPO stockInfoPO = new StockInfoPO();
        stockInfoPO.setBusinessCode("101");
        stockInfoPO.setVenderId(123L);
        stockInfoPO.setStoreCode("-1");
        stockInfoPO.setSkuId(-1L);
        stockInfoPO.setDate(AppointDateUtils.getStrToDate("yyyy-MM-dd" , date));
        stockInfoPO.setTotalQty(10);
        stockInfoPO.setStatus(StatusEnum.ENABLE);
        return stockInfoPO;
    }

    /**
     * mock 查询库存请求数据
     * @return
     */
    private StockInfoQuery mockStockInfoQuery(){
        StockInfoQuery query = new StockInfoQuery();
        query.setBusinessCode("101");
        query.setVenderId(123L);
        query.setStoreCode("-1");
        query.setSkuId(-1L);
        return query;
    }

    /**
     * mock 库存扣减或回冲数据
     * @return
     */
    private Stock mockStock(){
        Stock stock = new Stock();
        stock.setAppointOrderId(286L);
        stock.setBusinessCode("101");
        stock.setVenderId(60461L);
        //stock.setStoreCode("-1");
        stock.setSkuId(6442962L);
        stock.setDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-08-24"));
        stock.setPreDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", "2018-07-05"));
        return stock;
    }
}
