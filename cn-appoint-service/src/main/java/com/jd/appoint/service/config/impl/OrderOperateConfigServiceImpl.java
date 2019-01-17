package com.jd.appoint.service.config.impl;

import com.jd.appoint.dao.config.OrderOperateConfigDao;
import com.jd.appoint.domain.config.OrderOperateConfigPO;
import com.jd.appoint.service.config.OrderOperateConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaoxiaoqing on 2018/6/22.
 */
@Service(value = "orderOperateConfigService")
public class OrderOperateConfigServiceImpl implements OrderOperateConfigService {
    @Resource
    private OrderOperateConfigDao orderOperateConfigDao;


    @Override
    public List<OrderOperateConfigPO> batchOperateList(String businessCode, Integer serverType, Integer platform) {
        List<OrderOperateConfigPO> list = orderOperateConfigDao.findByBusinessCodeAndPlatform(businessCode, serverType, platform);
        return list.stream().filter(orderOperateConfigPO -> orderOperateConfigPO.getIsBatch() == 1).collect(Collectors.toList());
    }

    @Override
    public List<OrderOperateConfigPO> normalOperateList(String businessCode, Integer serverType, Integer platform) {
        List<OrderOperateConfigPO> list = orderOperateConfigDao.findByBusinessCodeAndPlatform(businessCode, serverType, platform);
        return list.stream().filter(orderOperateConfigPO -> orderOperateConfigPO.getIsBatch() == 0).collect(Collectors.toList());
    }
}
