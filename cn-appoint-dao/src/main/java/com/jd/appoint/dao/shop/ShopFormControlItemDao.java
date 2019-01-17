package com.jd.appoint.dao.shop;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.query.FormControlItemQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangyuan on 5/8/18.
 */
@MybatisRepository
public interface ShopFormControlItemDao extends MybatisDao<ShopFormControlItemPO> {

    /**
     * 根据参数查询
     *
     * @param formControlItemQuery
     * @return
     */
    List<ShopFormControlItemPO> query(FormControlItemQuery formControlItemQuery);

    /**
     * 根据业务类型查询全量数据
     *
     * @param businessCode
     * @return
     */
    List<ShopFormControlItemPO> queryByBusinessCode(@Param("businessCode") String businessCode);

    int batchInsert(List<ShopFormControlItemPO> itemPOList);

    /**
     * 根据控制ID获取配置信息
     *
     * @return
     */
    List<ShopFormControlItemPO> queryFormControlItems(@Param("formControlIds") List<Long> formControlIds);

    List<ShopFormControlItemPO> findByBusinessCodeAndPageNoAndVenderId(@Param("businessCode") String businessCode,
                                                                       @Param("pageNo") String pageNo,
                                                                       @Param("venderId") Long venderId);
}
