package com.jd.appoint.service.order.impl;

import com.jd.appoint.dao.config.OrderDetailConfigDao;
import com.jd.appoint.domain.config.OrderDetailConfigPO;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.service.order.OrderDetailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lishuaiwei
 * @date 2018/5/15 10:11
 */
@Service
public class OrderDetailConfigServiceImpl implements OrderDetailConfigService {
    @Autowired
    private OrderDetailConfigDao orderDetailConfigDao;


    @Override
    public List<OrderDetailConfigPO> getDetailItems(String businessCode, Integer platform, Integer serverType) {
        OrderDetailConfigPO orderDetailConfigPO = new OrderDetailConfigPO();
        orderDetailConfigPO.setBusinessCode(businessCode);
        orderDetailConfigPO.setPlatform(PlatformEnum.getFromCode(platform));
        orderDetailConfigPO.setServerType(ServerTypeEnum.getFromCode(serverType));
        return orderDetailConfigDao.getDetailItems(orderDetailConfigPO);
    }
}
