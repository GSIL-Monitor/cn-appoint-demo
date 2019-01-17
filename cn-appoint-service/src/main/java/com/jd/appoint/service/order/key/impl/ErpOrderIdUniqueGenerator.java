package com.jd.appoint.service.order.key.impl;

import com.jd.appoint.service.order.key.UniqueGenerator;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/7/4.
 */
@Service
public class ErpOrderIdUniqueGenerator implements UniqueGenerator {
    @Override
    public String generator(AppointOrderDetailVO detailVO) {
        if (detailVO.getErpOrderId() == null) {
            throw new IllegalArgumentException("当前业务线根据erp订单号唯一，erp订单号必须传");
        }
        return detailVO.getBusinessCode() + "_" + detailVO.getErpOrderId().toString();
    }
}
