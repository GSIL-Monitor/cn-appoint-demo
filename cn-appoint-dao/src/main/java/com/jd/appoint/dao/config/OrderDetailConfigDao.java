package com.jd.appoint.dao.config;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.config.OrderDetailConfigPO;

import java.util.List;

@MybatisRepository
public interface OrderDetailConfigDao extends MybatisDao<OrderDetailConfigPO> {

    List<OrderDetailConfigPO> getDetailItems(OrderDetailConfigPO orderDetailConfigPO);
}