package com.jd.appoint.service.order;

import com.jd.appoint.domain.dto.AppointDispatcherDTO;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import com.jd.appoint.vo.order.AppointOrderDetailVO;

import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/15 10:10
 */
public interface AppointOrderServiceItemService {

    /**
     * 根据预约单号查询预约服务item
     *
     * @param appointOrderId
     * @return
     */
    AppointOrderServiceItemPO getServiceItemByAppointOrderId(Long appointOrderId);

    int insert(AppointOrderServiceItemPO appointOrderServiceItemPO);

    /**
     * 更新预约单条目表
     * @param appointOrderServiceItemPO
     */
    void updateServiceItem(AppointOrderServiceItemPO appointOrderServiceItemPO);

    /**
     * 派单
     * @param appointDispatcherDTO
     */
    void dispatchOrder(AppointDispatcherDTO appointDispatcherDTO);

    /**
     *
     * @param appointOrderIds
     * @return
     */
    List<AppointOrderServiceItemPO> getAppointServiceItems(List<Long> appointOrderIds);


    /**
     * 导入物流单号
     * @param appointOrderDetailVO
     */
    int inputLsns(AppointOrderDetailVO appointOrderDetailVO);

}
