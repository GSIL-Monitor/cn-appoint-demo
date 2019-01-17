package com.jd.appoint.dao.order;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.order.AppointOrderFormItemPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisRepository
public interface AppointOrderFormItemDao extends MybatisDao<AppointOrderFormItemPO> {
    List<AppointOrderFormItemPO> selectFormItemListByAppointOrderId(Long appointOrderId);

    AppointOrderFormItemPO insertSelective(AppointOrderFormItemPO item);

    void updateItemByOrderIdAndAlias(AppointOrderFormItemPO item);

    List<AppointOrderFormItemPO> queryItemsByOrderAndAlias(@Param(value = "appointOrderId")Long appointOrderId,
                                                          @Param(value = "aliasList") List<String> aliasList);

    void batchUpdatedByAppointIdAndAlias(@Param(value = "appointOrderFormItemPOList") List<AppointOrderFormItemPO> appointOrderFormItemPOList);

    /**
     * 批量获取配置项
     * @param appointOrderIds
     * @return
     */
    List<AppointOrderFormItemPO> getAppointFormItems(@Param(value = "appointOrderIds") List<Long> appointOrderIds);
}