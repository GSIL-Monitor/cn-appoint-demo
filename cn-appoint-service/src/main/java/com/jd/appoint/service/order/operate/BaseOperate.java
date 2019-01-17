package com.jd.appoint.service.order.operate;

import com.jd.appoint.vo.order.AppointOrderDetailVO;

/**
 * Created by shaohongsen on 2018/6/13.
 * 订单基本操作
 */
public interface BaseOperate {


    OperateResultEnum execute(AppointOrderDetailVO detailVO);
}
