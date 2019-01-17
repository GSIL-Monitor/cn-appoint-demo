package com.jd.appoint.inner.man;

import com.jd.appoint.inner.man.dto.VenderConfigDTO;
import com.jd.appoint.inner.man.dto.VenderConfigFormDTO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 18:03
 */
public interface ManVenderConfigFacade {
    /**
     * 查询配置分页数据
     *
     * @param request
     * @return
     */
    SoaResponse<List<VenderConfigDTO>> getVenderConfig(SoaRequest<VenderConfigFormDTO> request);

    /**
     * 更新配置分页数据
     * 添加配置分页数据
     *
     * @param request
     * @return
     */
    SoaResponse invoke(SoaRequest<VenderConfigFormDTO> request, String method);

}
