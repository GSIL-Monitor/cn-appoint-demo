package com.jd.appoint.service.api.convert;

import com.jd.appoint.api.vo.OrderDetailWithOperateVO;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.page.Page;

/**
 * Created by shaohongsen on 2018/7/3.
 */
public class PageConvert {
    public static <T> Page<T> copyPage(Page<AppointOrderDetailVO> page) {
        Page<T> result = new Page<>();
        result.setTotalCount(page.getTotalCount());
        result.setPageSize(page.getPageSize());
        result.setPageNumber(page.getPageNumber());
        result.setSorts(page.getSorts());
        return result;
    }
}
