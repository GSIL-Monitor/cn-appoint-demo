package com.jd.appoint.dao.eggshell;

import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import org.apache.ibatis.annotations.Param;

/**
 * @author lijizhen1@jd.com
 * @date 2017/11/28 15:14
 */
@MybatisRepository
public interface EggshellDao {

    /**
     * 查询操作
     * @param sql
     * @return
     */
    Integer select(@Param(value = "sql") String sql);

    /**
     * 更新操作
     * @param sql
     * @return
     */
    Integer update(@Param(value = "sql") String sql);
}
