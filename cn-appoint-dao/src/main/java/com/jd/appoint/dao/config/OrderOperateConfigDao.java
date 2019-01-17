package com.jd.appoint.dao.config;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.config.OrderDetailConfigPO;
import com.jd.appoint.domain.config.OrderOperateConfigPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisRepository
public interface OrderOperateConfigDao extends MybatisDao<OrderOperateConfigPO> {
    /**
     * 根据业务Code获取操作项
     *
     * @param businessCode
     * @return
     */
    List<OrderOperateConfigPO> queryOperateByBusinessCode(String businessCode);

    /**
     * 根据业务Code获取操作项
     *
     * @param businessCode
     * @return
     */
    List<OrderOperateConfigPO> findByBusinessCodeAndPlatform(@Param("businessCode") String businessCode, @Param("serverType") Integer serverType, @Param("platform") Integer platform);
}