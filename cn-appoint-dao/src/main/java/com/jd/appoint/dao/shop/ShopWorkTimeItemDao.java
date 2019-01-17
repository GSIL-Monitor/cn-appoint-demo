package com.jd.appoint.dao.shop;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeItemQuery;

import java.util.List;

@MybatisRepository
public interface ShopWorkTimeItemDao extends MybatisDao<ShopWorkTimeItemPO> {

    List<ShopWorkTimeItemPO> queryShopWorkTimeItems(ShopWorkTimeItemQuery query);

    int updateShopWorkTimeItem(ShopWorkTimeItemPO shopWorkTimeItemPO);
}