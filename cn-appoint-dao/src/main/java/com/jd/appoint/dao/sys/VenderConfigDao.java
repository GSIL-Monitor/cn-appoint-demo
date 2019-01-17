package com.jd.appoint.dao.sys;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.sys.VenderConfigPO;

import java.util.List;
import java.util.Map;

@MybatisRepository
public interface VenderConfigDao extends MybatisDao<VenderConfigPO> {
    /**
     * 通过业务code码、venderId、key查询配置
     *
     * @param venderConfigPO
     * @return
     */
    String findByBusinessCodeAndVenderIdAndKey(VenderConfigPO venderConfigPO);

    /**
     * 通过query条件查询
     *
     * @param query
     * @return
     */
    List<VenderConfigPO> findAll(Map query);
}