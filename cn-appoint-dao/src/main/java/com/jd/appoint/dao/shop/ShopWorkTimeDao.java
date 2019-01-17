package com.jd.appoint.dao.shop;


import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.shop.ShopWorkTimePO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeQuery;

@MybatisRepository
public interface ShopWorkTimeDao extends MybatisDao<ShopWorkTimePO> {

    ShopWorkTimePO queryShopWorkTime(ShopWorkTimeQuery shopWorkTimeQuery);

    int updateShopWorkTime(ShopWorkTimePO shopWorkTimePO);

    int updateByIdAndVenderId(ShopWorkTimePO shopWorkTimePO);
}