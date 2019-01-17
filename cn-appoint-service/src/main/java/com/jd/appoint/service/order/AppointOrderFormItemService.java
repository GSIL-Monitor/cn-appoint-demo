package com.jd.appoint.service.order;

import com.jd.appoint.domain.order.AppointOrderFormItemPO;

import java.util.List;

public interface AppointOrderFormItemService {
    List<AppointOrderFormItemPO> getFormItemListByAppointOrderId(Long appointOrderId);

    int insert(AppointOrderFormItemPO appointOrderFormItemPO);

    /**
     * 批量获取配置项
     * @param appointOrderIds
     * @return
     */
    List<AppointOrderFormItemPO> getAppointFormItems(List<Long> appointOrderIds);
}
