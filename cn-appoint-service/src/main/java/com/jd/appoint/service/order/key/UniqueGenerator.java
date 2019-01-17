package com.jd.appoint.service.order.key;

import com.jd.appoint.vo.order.AppointOrderDetailVO;

/**
 * Created by shaohongsen on 2018/7/4.
 */
public interface UniqueGenerator {
    String generator(AppointOrderDetailVO detailVO);
}
