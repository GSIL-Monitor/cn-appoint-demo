package com.jd.appoint.service.stock;

import com.jd.appoint.domain.stock.Stock;
import com.jd.appoint.domain.stock.StockInfoPO;
import com.jd.appoint.domain.stock.query.StockInfoQuery;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.stock.StockInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 */
@Service
public interface StockInfoService {

    int insert(StockInfoPO stockInfoPO);

    List<StockInfoPO> querySelective(StockInfoQuery query);

    int updateByPrimaryKeySelective(StockInfoPO stockInfoPO);

    /**
     * 更新总库存
     * @param id
     * @param latestTotalQty
     * @return
     */
    int updateTotalQty(Long id, int latestTotalQty);

    /**
     * 扣减库存
     * @param stock
     * @return
     */
    OperateResultEnum decreaseStock(Stock stock);

    /**
     * 回冲库存
     * @param stock
     * @return
     */
    OperateResultEnum increaseStock(Stock stock);

    /**
     * 改期
     * @param stock
     * @return
     */
    OperateResultEnum changeStock(Stock stock);

    /**
     * 保存或更新库存信息
     * @param vo
     * @return
     */
    List<String> saveOrUpdateStock(StockInfoVO vo);

    /**
     * 初始化所有库存信息
     */
    void init();
}
