package com.jd.appoint.service.shop;

import com.jd.appoint.api.vo.FormControlQuery;
import com.jd.appoint.domain.shop.ShopBusinessPO;
import com.jd.appoint.domain.shop.query.FormControlItemQuery;
import com.jd.appoint.domain.shop.query.ShopBusinessQuery;

import java.util.List;

/**
 * Created by yangyuan on 5/14/18.
 */
public interface ShopBusinessService {

    /**
     * 根据业务编码查询级联数据
     * use cache
     * @param formControlItemQuery
     * @return
     */
    ShopBusinessPO queryByCondition(FormControlItemQuery formControlItemQuery);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    ShopBusinessPO queryById(long id);

    /**
     * 插入
     * @param shopBusinessPO
     * @return
     */
    int insert(ShopBusinessPO shopBusinessPO);

    boolean edit(ShopBusinessPO shopBusinessPO);

    /**
     * 分页查询
     * @param shopBusinessQuery
     * @return
     */
    List<ShopBusinessPO> queryOnPage(ShopBusinessQuery shopBusinessQuery);

    /**
     * 获取所有可用业务类型
     * @return
     */
    List<ShopBusinessPO> getAllAvailable();

    /**
     * 数据总数
     * @param shopBusinessQuery
     * @return
     */
    int totalCount(ShopBusinessQuery shopBusinessQuery);

}
