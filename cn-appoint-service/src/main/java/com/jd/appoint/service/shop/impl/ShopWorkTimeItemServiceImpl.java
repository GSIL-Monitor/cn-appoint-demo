package com.jd.appoint.service.shop.impl;

import com.jd.appoint.dao.shop.ShopWorkTimeItemDao;
import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeItemQuery;
import com.jd.appoint.service.shop.ShopWorkTimeItemService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by luqiang3 on 2018/5/5.
 */
@Service("shopWorkTimeItemService")
public class ShopWorkTimeItemServiceImpl implements ShopWorkTimeItemService {

    private static final Logger logger = LoggerFactory.getLogger(ShopWorkTimeItemServiceImpl.class);

    @Autowired
    private ShopWorkTimeItemDao shopWorkTimeItemDao;

    /**
     * 添加店铺服务时间项
     *
     * @param shopWorkTimeItemPO
     * @return
     */
    @Override
    public int insertShopWorkTimeItem(ShopWorkTimeItemPO shopWorkTimeItemPO) {
        return shopWorkTimeItemDao.insert(shopWorkTimeItemPO);
    }

    /**
     * 查询ShopWorkTimeItems
     *
     * @param query
     * @return
     */
    @Override
    public List<ShopWorkTimeItemPO> queryShopWorkTimeItems(ShopWorkTimeItemQuery query) {
        return shopWorkTimeItemDao.queryShopWorkTimeItems(query);
    }

    /**
     * 更新店铺服务时间项信息
     *
     * @param shopWorkTimeItemPO
     * @return
     */
    @Override
    public int updateShopWorkTimeItem(ShopWorkTimeItemPO shopWorkTimeItemPO) {
        return shopWorkTimeItemDao.updateShopWorkTimeItem(shopWorkTimeItemPO);
    }

    /**
     * 批量更新店铺服务时间项信息
     *
     * @param shopWorkTimeItemPOs
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateShopWorkTimeItems(List<ShopWorkTimeItemPO> shopWorkTimeItemPOs) {
        if(CollectionUtils.isEmpty(shopWorkTimeItemPOs)){
            return Boolean.FALSE;
        }
        for(ShopWorkTimeItemPO shopWorkTimeItemPO : shopWorkTimeItemPOs){
            shopWorkTimeItemDao.updateShopWorkTimeItem(shopWorkTimeItemPO);
        }
        return Boolean.TRUE;
    }

    /**
     * 通过主键更新
     *
     * @param shopWorkTimeItemPO
     * @return
     */
    @Override
    public int updateById(ShopWorkTimeItemPO shopWorkTimeItemPO) {
        return shopWorkTimeItemDao.update(shopWorkTimeItemPO);
    }
}
