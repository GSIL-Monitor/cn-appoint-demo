package com.jd.appoint.service.order;

import com.jd.appoint.domain.config.OrderDetailConfigPO;

import java.util.List;

public interface OrderDetailConfigService {
    List<OrderDetailConfigPO> getDetailItems(String businessCode, Integer platform, Integer serverType);
}
