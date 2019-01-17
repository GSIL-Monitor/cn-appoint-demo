package com.jd.appoint.service.order.operate.impl.stock;

import com.jd.appoint.domain.stock.Stock;
import com.jd.appoint.service.order.convert.StockConverter;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.ExecuteException;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.service.stock.StockInfoService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/6/14.
 * 库存减少
 */
@Service("reduceStock")
public class ReduceStock implements BaseOperate {
    @Autowired
    private StockInfoService stockInfoService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        Stock stock = StockConverter.convert(detailVO);
        return stockInfoService.decreaseStock(stock);
    }


}
