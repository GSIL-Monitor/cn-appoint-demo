package com.jd.appoint.dao.order;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
@MybatisRepository
public interface AppointOrderServiceItemDao extends MybatisDao<AppointOrderServiceItemPO> {

    AppointOrderServiceItemPO selectByAppointOrderId(Long appointOrderId);

    /**
     * 更新服务条目信息
     *
     * @param appointOrderServiceItemPO
     */
    void updateServiceItem(AppointOrderServiceItemPO appointOrderServiceItemPO);

    /**
     * 批量查询预约单服务项目
     *
     * @param appointOrderIds
     * @return
     */
    List<AppointOrderServiceItemPO> getAppointServiceItems(@Param(value = "appointOrderIds") List<Long> appointOrderIds);

    /**
     * 更新物流数据
     *
     * @param appointOrderServiceItemPO
     */
    int updateLsns(AppointOrderServiceItemPO appointOrderServiceItemPO);
}