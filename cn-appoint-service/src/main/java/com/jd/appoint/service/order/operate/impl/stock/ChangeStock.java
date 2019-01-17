package com.jd.appoint.service.order.operate.impl.stock;

import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.stock.Stock;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.service.stock.StockInfoService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by shaohongsen on 2018/6/14.
 * 改期
 */
@Service("changeStock")
public class ChangeStock implements BaseOperate {

    @Resource
    private StockInfoService stockInfoService;
    @Resource
    private AppointOrderService appointOrderService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        AppointOrderPO appointOrderPO = appointOrderService.getAppointOrder(detailVO.getId());
        //库存扣减
        return stockInfoService.changeStock(stockConvert(detailVO,appointOrderPO));
    }

    private Stock stockConvert(AppointOrderDetailVO appointOrderDetailVO,AppointOrderPO appointOrderPO){
        Stock stock = new Stock();
        stock.setAppointOrderId(appointOrderPO.getId());
        stock.setBusinessCode(appointOrderPO.getBusinessCode());
        stock.setStoreCode(appointOrderDetailVO.getStoreCode());
        stock.setSkuId(appointOrderDetailVO.getSkuId());
        stock.setVenderId(appointOrderDetailVO.getVenderId());
        stock.setDate(appointOrderDetailVO.getAppointStartTime());
        stock.setPreDate(appointOrderPO.getAppointStartTime());
        return stock;
    }
}
