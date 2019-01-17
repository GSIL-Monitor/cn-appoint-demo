package com.jd.appoint.service.stock.impl;

import com.jd.appoint.dao.stock.StockOperateDao;
import com.jd.appoint.domain.stock.StockOperatePO;
import com.jd.appoint.domain.stock.query.StockOperateQuery;
import com.jd.appoint.service.stock.StockOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 */
@Service("stockOperateService")
public class StockOperateServiceImpl implements StockOperateService {

    @Autowired
    private StockOperateDao stockOperateDao;

    @Override
    public int insert(StockOperatePO stockOperatePO) {
        return stockOperateDao.insert(stockOperatePO);
    }

    @Override
    public List<StockOperatePO> querySelective(StockOperateQuery query) {
        return stockOperateDao.querySelective(query);
    }
}
