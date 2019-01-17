package com.jd.appoint.api;

import com.jd.appoint.vo.express.ExpressInfo;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by yangyuan on 6/27/18.
 */
public interface ExpressFacade {


    /**
     * 获取快递信息
     * @param soaRequest
     * @return
     */
    SoaResponse<ExpressInfo> getExpressInfo(SoaRequest<Long> soaRequest);

}
