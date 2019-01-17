package com.jd.appoint.service.stock;

import com.jd.appoint.domain.stock.StockOperatePO;
import com.jd.appoint.domain.stock.query.StockOperateQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 */
@Service
public interface StockOperateService {

    int insert(StockOperatePO stockOperatePO);

    List<StockOperatePO> querySelective(StockOperateQuery query);
}
