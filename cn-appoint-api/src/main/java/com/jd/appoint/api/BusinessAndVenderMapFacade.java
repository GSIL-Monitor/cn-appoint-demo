package com.jd.appoint.api;

import com.jd.appoint.api.vo.BusinessVenderMapVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by yangyuan on 7/4/18.
 */
public interface BusinessAndVenderMapFacade {

    SoaResponse<BusinessVenderMapVO>  queryByVenderId(SoaRequest<Long> venderId);
}
