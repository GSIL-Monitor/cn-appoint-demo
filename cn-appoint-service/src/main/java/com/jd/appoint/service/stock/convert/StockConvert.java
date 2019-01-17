package com.jd.appoint.service.stock.convert;

import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.StockOptStatusEnum;
import com.jd.appoint.domain.stock.Stock;
import com.jd.appoint.domain.stock.StockInfoPO;
import com.jd.appoint.domain.stock.StockOperatePO;
import com.jd.appoint.domain.stock.query.StockInfoQuery;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.common.util.StringUtils;

import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/16.
 */
public class StockConvert {

    /**
     * Stock -> StockInfoPO
     * @param stock
     * @return
     */
    public static StockInfoPO stock2StockInfoPO(Stock stock, Date date){
        StockInfoPO po = new StockInfoPO();
        po.setBusinessCode(stock.getBusinessCode());
        po.setVenderId(stock.getVenderId());
        po.setStoreCode(stock.getStoreCode());
        po.setSkuId(stock.getSkuId());
        po.setDate(AppointDateUtils.getDate2Date("yyyy-MM-dd", date));
        return po;
    }

    /**
     * Stock -> StockOperatePO
     * @return
     */
    public static StockOperatePO stock2StockOperatePO(Stock stock, StockOptStatusEnum stockOptStatus){
        StockOperatePO stockOperatePO = new StockOperatePO();
        stockOperatePO.setAppointOrderId(stock.getAppointOrderId());
        stockOperatePO.setAppointDate(AppointDateUtils.getDate2Date("yyyy-MM-dd", stock.getDate()));
        stockOperatePO.setStockStatus(stockOptStatus);
        stockOperatePO.setStatus(StatusEnum.ENABLE);
        return stockOperatePO;
    }

    /**
     * shop-api库存信息VO转换为数据库库存Query
     * @param stockInfoVO
     * @return
     */
    public static StockInfoQuery stockInfoVO2StockInfoQuery(StockInfoVO stockInfoVO){
        StockInfoQuery query = new StockInfoQuery();
        query.setBusinessCode(stockInfoVO.getBusinessCode());
        query.setVenderId(stockInfoVO.getVenderId());
        query.setStoreCode(StringUtils.isNotBlank(stockInfoVO.getStoreCode()) ? stockInfoVO.getStoreCode() : DefaultVenderConfig.STORE_CODE);
        query.setSkuId(null != stockInfoVO.getSkuId() ? stockInfoVO.getSkuId() : DefaultVenderConfig.SKU_ID);
        query.setStartDate(AppointDateUtils.getDate2Date("yyyy-MM-dd", stockInfoVO.getStartDate()));
        query.setEndDate(AppointDateUtils.getDate2Date("yyyy-MM-dd", stockInfoVO.getEndDate()));
        return query;
    }

    /**
     * shop-api库存信息VO转换为数据库库存PO
     * @param stockInfoVO
     * @param day
     * @return
     */
    public static StockInfoPO stockInfoVO2StockInfoPO(StockInfoVO stockInfoVO, String day){
        StockInfoPO stockInfoPO = new StockInfoPO();
        stockInfoPO.setBusinessCode(stockInfoVO.getBusinessCode());
        stockInfoPO.setVenderId(stockInfoVO.getVenderId());
        stockInfoPO.setStoreCode(StringUtils.isNotBlank(stockInfoVO.getStoreCode()) ? stockInfoVO.getStoreCode() : DefaultVenderConfig.STORE_CODE);
        stockInfoPO.setSkuId(null != stockInfoVO.getSkuId() ? stockInfoVO.getSkuId() : DefaultVenderConfig.SKU_ID);
        stockInfoPO.setTotalQty(stockInfoVO.getTotalQty());
        stockInfoPO.setDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", day));
        stockInfoPO.setStatus(StatusEnum.ENABLE);
        return stockInfoPO;
    }
}
