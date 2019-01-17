package com.jd.appoint.service.shop;

import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeItemQuery;

import java.util.List;

/**
 * Created by luqiang3 on 2018/5/5.
 */
public interface ShopWorkTimeItemService {

    /**
     * 添加店铺服务时间项
     * @param shopWorkTimeItemPO
     * @return
     */
    int insertShopWorkTimeItem(ShopWorkTimeItemPO shopWorkTimeItemPO);

    /**
     * 查询店铺服务时间项信息
     * @param query
     * @return
     */
    List<ShopWorkTimeItemPO> queryShopWorkTimeItems(ShopWorkTimeItemQuery query);

    /**
     * 更新店铺服务时间项信息
     * @param shopWorkTimeItemPO
     * @return
     */
    int updateShopWorkTimeItem(ShopWorkTimeItemPO shopWorkTimeItemPO);

    /**
     * 批量更新店铺服务时间项信息
     * @param shopWorkTimeItemPOs
     * @return
     */
    Boolean updateShopWorkTimeItems(List<ShopWorkTimeItemPO> shopWorkTimeItemPOs);

    /**
     * 通过主键更新
     * @param shopWorkTimeItemPO
     * @return
     */
    int updateById(ShopWorkTimeItemPO shopWorkTimeItemPO);
}
