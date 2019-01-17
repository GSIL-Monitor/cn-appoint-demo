package com.jd.appoint.dao.shop;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.shop.ShopBusinessPO;
import com.jd.appoint.domain.shop.query.ShopBusinessQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by yangyuan on 5/8/18.
 */
@MybatisRepository
public interface ShopBusinessDao extends MybatisDao<ShopBusinessPO> {

    /**
     * 根据业务编码获取相关所有属性
     * @param code
     * @return
     */
    ShopBusinessPO queryByCode(@Param(value = "code")String code);

    /**
     * 分页查询
     * @return
     */
    List<ShopBusinessPO> queryOnPage(ShopBusinessQuery shopBusinessQuery);

    /**
     * 获取数据总数
     * @param shopBusinessQuery
     * @return
     */
    int totalCount(ShopBusinessQuery shopBusinessQuery);

    /**
     * 获取所有业务类型
     * @return
     */
    List<ShopBusinessPO> queryAll(StatusEnum statusEnum);
}
