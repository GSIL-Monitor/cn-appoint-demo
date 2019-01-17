package com.jd.appoint.shopapi;

import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 * 库存相关接口
 */
public interface ShopStockFacade {

    /**
     * 保存或更新库存接口
     * @param soaRequest
     * @return
     */
    SoaResponse<List<String>> saveOrUpdateStock(SoaRequest<StockInfoVO> soaRequest);

}
