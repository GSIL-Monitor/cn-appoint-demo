package com.jd.appoint.dao.stock;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.enums.StockOptStatusEnum;
import com.jd.appoint.domain.stock.StockOperatePO;
import com.jd.appoint.domain.stock.query.StockOperateQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 */
@MybatisRepository
public interface StockOperateDao extends MybatisDao<StockOperatePO> {

    List<StockOperatePO> querySelective(StockOperateQuery query);

    StockOperatePO queryByAppointOrderId(Long appointOrderId);

    int updateStockStatus(@Param(value = "appointOrderId")Long appointOrderId,
                          @Param(value = "stockStatus")StockOptStatusEnum stockStatus,
                          @Param(value = "preStockStatus")StockOptStatusEnum preStockStatus);

    int updateAppointDate(@Param(value = "appointOrderId")Long appointOrderId,
                          @Param(value = "stockStatus")StockOptStatusEnum stockStatus,
                          @Param(value = "appointDate")Date appointDate);
}
