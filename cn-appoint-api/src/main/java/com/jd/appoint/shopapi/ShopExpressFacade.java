package com.jd.appoint.shopapi;

import com.jd.appoint.vo.express.ExpressCompanyVO;
import com.jd.appoint.vo.express.ExpressInfo;
import com.jd.appoint.vo.express.ExpressSubscribeRequest;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * Created by yangyuan on 6/27/18.
 */
public interface ShopExpressFacade {

    /**
     * 获取所有快递公司
     * @return
     */
    SoaResponse<List<ExpressCompanyVO>> getAllExpressCompany();

    /**
     * 获取物流信息
     * @param orderId
     * @return
     */
    SoaResponse<ExpressInfo> getExpressInfo(SoaRequest<Long> orderId);

    /**
     * 订阅快递信息
     * @param request
     * @return
     */
    SoaResponse<Boolean> routeSubscribe(SoaRequest<ExpressSubscribeRequest> request);
}
