package com.jd.appoint.dao.stock;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.stock.StockInfoPO;
import com.jd.appoint.domain.stock.query.StockInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiang3 on 2018/6/13.
 */
@MybatisRepository
public interface StockInfoDao extends MybatisDao<StockInfoPO> {

    List<StockInfoPO> querySelective(StockInfoQuery stockInfoQuery);

    int updateByPrimaryKeySelective(StockInfoPO stockInfoPO);

    int updateTotalQty(@Param(value = "id")Long id,
                       @Param(value = "latestTotalQty")int latestTotalQty);

    /**
     * 扣减库存
     * @param stockInfoPO
     * @return
     */
    int decreaseStock(StockInfoPO stockInfoPO);

    /**
     * 回冲库存
     * @param stockInfoPO
     * @return
     */
    int increaseStock(StockInfoPO stockInfoPO);

    List<StockInfoPO> queryOnPage(@Param("offset")Integer offset,
                                  @Param("pageSize")Integer pageSize);
}
