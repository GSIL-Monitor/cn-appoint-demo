package com.jd.appoint.api;

import com.jd.appoint.api.vo.CardVO;
import com.jd.appoint.api.vo.InputCartVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * 相关卡密的操作
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/15 17:14
 */
public interface AppointCardFacade {

    /**
     * 批量导入卡密，导入失败需要重试
     * 导入支持幂等
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Boolean> batchInputCards(SoaRequest<InputCartVO> soaRequest);
}
