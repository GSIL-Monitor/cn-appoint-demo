package com.jd.appoint.api;

import com.jd.appoint.api.vo.ProcessConfigRequest;
import com.jd.appoint.api.vo.ProcessConfigVO;
import com.jd.appoint.api.vo.QueryConfigVO;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.vo.CommonRequest;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/15 10:40
 */
public interface VenderConfigFacade {

    /**
     * 获取路由的配置信息
     *
     * @param request
     * @return
     */
    SoaResponse<VenderConfigVO> getVenderConfig(SoaRequest<QueryConfigVO> request);

}
