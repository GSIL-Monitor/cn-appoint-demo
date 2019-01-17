package com.jd.appoint.service.order.convert;

import com.jd.appoint.domain.stock.Stock;
import com.jd.appoint.vo.order.AppointOrderDetailVO;

/**
 * Created by shaohongsen on 2018/6/27.
 */
public class StockConverter {
    public static Stock convert(AppointOrderDetailVO detailVO) {
        Stock stock = new Stock();
        stock.setAppointOrderId(detailVO.getId());
        stock.setBusinessCode(detailVO.getBusinessCode());
        stock.setDate(detailVO.getAppointStartTime());
        stock.setVenderId(detailVO.getVenderId());
        stock.setStoreCode(detailVO.getStoreCode());
        stock.setSkuId(detailVO.getSkuId());
        return stock;
    }
}
