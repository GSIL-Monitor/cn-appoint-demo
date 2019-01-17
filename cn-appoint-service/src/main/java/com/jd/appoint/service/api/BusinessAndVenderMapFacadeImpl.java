package com.jd.appoint.service.api;

import com.google.common.base.Preconditions;
import com.jd.appoint.api.BusinessAndVenderMapFacade;
import com.jd.appoint.api.vo.BusinessVenderMapVO;
import com.jd.appoint.domain.config.BusinessVenderMapPO;
import com.jd.appoint.service.config.BusinessVenderMapService;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by yangyuan on 7/4/18.
 */
@Service("businessAndVenderMapFacade")
public class BusinessAndVenderMapFacadeImpl implements BusinessAndVenderMapFacade{

    @Autowired
    private BusinessVenderMapService businessVenderMapService;


    @Override
    public SoaResponse<BusinessVenderMapVO> queryByVenderId(SoaRequest<Long> venderId) {
        Preconditions.checkNotNull(venderId.getData());
        BusinessVenderMapPO businessVenderMapPO = businessVenderMapService.queryByVenderId(venderId.getData());
        if(businessVenderMapPO == null){
            return new SoaResponse<>();
        }
        BusinessVenderMapVO businessVenderMapVO = new BusinessVenderMapVO();
        BeanUtils.copyProperties(businessVenderMapPO, businessVenderMapVO);
        return new SoaResponse<>(businessVenderMapVO);
    }
}
