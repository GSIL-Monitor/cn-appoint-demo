package com.jd.appoint.dao.config;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.config.OrderListConfigPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisRepository
public interface OrderListConfigDao extends MybatisDao<OrderListConfigPO> {

    List<OrderListConfigPO> findByBusinessCodeAndServerTypeAndPlatform(
            @Param("businessCode") String businessCode,
            @Param("serverType") Integer serverType,
            @Param("platform") Integer platform);

    List<Integer> findDistinctServerType(@Param("businessCode") String businessCode, @Param("platform") Integer platform);
}