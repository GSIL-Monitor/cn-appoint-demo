package com.jd.appoint.dao.config;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.config.BusinessVenderMapPO;
import com.jd.appoint.domain.config.OrderDetailConfigPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangyuan on 7/4/18.
 */
@MybatisRepository
public interface BusinessVenderMapDao extends MybatisDao<OrderDetailConfigPO> {

    List<BusinessVenderMapPO> listVender(@Param(value = "list")List<Long> venderIds);

    BusinessVenderMapPO queryByVenderId(@Param(value = "venderId")Long venderId);
}
