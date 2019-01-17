package com.jd.appoint.service.config;

import com.jd.appoint.domain.config.BusinessVenderMapPO;

import java.util.List;

/**
 * Created by yangyuan on 7/4/18.
 */
public interface BusinessVenderMapService {
    List<BusinessVenderMapPO> listVender(List<Long> venderIds);

    BusinessVenderMapPO queryByVenderId(Long venderId);
}
