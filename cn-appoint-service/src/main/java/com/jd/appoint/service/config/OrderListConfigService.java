package com.jd.appoint.service.config;

import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.vo.dynamic.ServerTypeVO;


import java.util.List;

/**
 * Created by shaohongsen on 2018/6/21.
 */
public interface OrderListConfigService {

    /**
     * 获取列表项项
     *
     * @param businessCode
     * @param serverType
     * @param platform
     * @return
     */
    List<OrderListConfigPO> getListItems(String businessCode, Integer serverType, Integer platform);

    /**
     * 获取列表项项
     *
     * @param businessCode
     * @param serverType
     * @param platform
     * @return
     */
    List<OrderListConfigPO> getFilterItems(String businessCode, Integer serverType, Integer platform);


    /**
     * 获取筛选项
     *
     * @param businessCode
     * @param platform
     * @return
     */
    List<ServerTypeVO> getServerTypes(String businessCode, Integer platform);


}
