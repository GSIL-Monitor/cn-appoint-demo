package com.jd.appoint.service.config;

import com.jd.appoint.domain.config.OrderOperateConfigPO;

import java.util.List;

/**
 * Created by gaoxiaoqing on 2018/6/22.
 */
public interface OrderOperateConfigService {


    /**
     * 获取批量操作列表
     *
     * @param businessCode
     * @param serverType
     * @param platform
     * @return
     */
    List<OrderOperateConfigPO> batchOperateList(String businessCode, Integer serverType, Integer platform);

    /**
     * 获取一般操作列表
     *
     * @param businessCode
     * @param serverType
     * @param platform
     * @return
     */
    List<OrderOperateConfigPO> normalOperateList(String businessCode, Integer serverType, Integer platform);
}
