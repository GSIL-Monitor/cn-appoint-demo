package com.jd.appoint.api;

import com.jd.appoint.api.vo.ProcessConfigRequest;
import com.jd.appoint.api.vo.ProcessConfigVO;
import com.jd.appoint.api.vo.TabControlVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/15.
 */
public interface ProcessConfigFacade {
    /**
     * 获取流程配置
     *
     * @param request
     * @return
     */
    SoaResponse<ProcessConfigVO> getProcessConfig(SoaRequest<ProcessConfigRequest> request);
}
