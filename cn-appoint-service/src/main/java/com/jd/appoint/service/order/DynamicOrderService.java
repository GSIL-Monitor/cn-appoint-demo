package com.jd.appoint.service.order;

import com.jd.appoint.api.vo.OrderDetailWithOperateVO;
import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.vo.dynamic.DynamicAppointOrder;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by shaohongsen on 2018/6/21.
 */
public interface DynamicOrderService {
    /**
     * 动态列表
     *
     * @param page
     * @param businessCode
     * @param filterItems
     * @return
     */
    Page<DynamicAppointOrder> dynamicList(Page page, String businessCode, List<OrderListConfigPO> filterItems);

    /**
     * 动态C端预约单列表
     * @return
     */
    Page<ApiAppointOrderDetailVO> cDynamicAppointList(AppointOrderListRequest appointOrderListRequest);

    /**
     * C端预约单列表
     * @param appointOrderListRequest
     * @return
     */
    Page<OrderDetailWithOperateVO> appointList(AppointOrderListRequest appointOrderListRequest);


    String invoke(String alias, AppointOrderDetailVO orderDetailVO, TimeShowTypeEnum timeShowTypeEnum);

    /**
     * 导出预约单列表
     * @return
     */
    List<LinkedHashMap<String, String>> exportDynamicList(AppointOrderListQuery appointOrderListQuery, Integer platform);

}
