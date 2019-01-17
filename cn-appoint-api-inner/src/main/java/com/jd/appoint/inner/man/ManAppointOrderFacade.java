package com.jd.appoint.inner.man;

import com.jd.appoint.inner.man.dto.OrderDetailDTO;
import com.jd.appoint.inner.man.dto.OrderListDTO;
import com.jd.appoint.page.Page;

/**
 * Created by luqiang3 on 2018/5/17.
 */
public interface ManAppointOrderFacade {

    /**
     * 订单列表接口
     * @param page
     * @return
     */
    Page<OrderListDTO> list(Page<OrderListDTO> page);

    /**
     * 订单详情接口
     * @param id
     * @return
     */
    OrderDetailDTO detail(long id);
}
