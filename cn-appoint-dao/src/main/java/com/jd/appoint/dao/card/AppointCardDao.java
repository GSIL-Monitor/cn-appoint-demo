package com.jd.appoint.dao.card;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.card.AppointCardPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisRepository
public interface AppointCardDao extends MybatisDao<AppointCardPO> {

    /**
     * 导入预约卡密
     *
     * @param appointCardPos
     * @return
     */
    int batchInsert(@Param(value = "appointCardPos") List<AppointCardPO> appointCardPos);

}