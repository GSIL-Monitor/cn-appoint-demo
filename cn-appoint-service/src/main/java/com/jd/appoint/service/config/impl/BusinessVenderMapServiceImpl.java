package com.jd.appoint.service.config.impl;

import com.jd.appoint.dao.config.BusinessVenderMapDao;
import com.jd.appoint.domain.config.BusinessVenderMapPO;
import com.jd.appoint.service.config.BusinessVenderMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyuan on 7/4/18.
 */
@Service
public class BusinessVenderMapServiceImpl implements BusinessVenderMapService{

    @Autowired
    private BusinessVenderMapDao businessVenderMapDao;

    public List<BusinessVenderMapPO> listVender(List<Long> venderIds){
        return businessVenderMapDao.listVender(venderIds);
    }

    public BusinessVenderMapPO queryByVenderId(Long venderId){
        return businessVenderMapDao.queryByVenderId(venderId);
    }

}
