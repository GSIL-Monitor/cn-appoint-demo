package com.jd.appoint.service.api.inner.man;

import com.jd.appoint.inner.man.ManAppointOrderFacade;
import com.jd.appoint.inner.man.dto.OrderDetailDTO;
import com.jd.appoint.inner.man.dto.OrderListDTO;
import com.jd.appoint.page.Page;
import com.jd.appoint.service.api.inner.man.convert.EsOrderConvert;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiang3 on 2018/5/17.
 */
@Service("manAppointOrderFacade")
public class ManAppointOrderFacadeImpl implements ManAppointOrderFacade {

    @Autowired
    private EsOrderService esOrderService;
    @Autowired
    private AppointOrderService appointOrderService;

    /**
     * 订单列表接口
     * 需在man端将searchMap准备好
     *
     * @param page
     * @return
     */
    @Override
    public Page<OrderListDTO> list(Page page) {
        //请求ES获得数据
        com.jd.appoint.vo.page.Page<AppointOrderDetailVO> pageList = esOrderService.list(EsOrderConvert.toEsRequest(page), null);
        //返回结果转换
        Page<OrderListDTO> dtoPage = EsOrderConvert.toManResponse(pageList);
        return dtoPage;
    }

    /**
     * 订单详情接口
     *
     * @param id
     * @return
     */
    @Override
    public OrderDetailDTO detail(long id) {
        AppointOrderDetailVO detail = appointOrderService.detail(id);
        OrderDetailDTO dto = new OrderDetailDTO();
        if (null == detail) {
            return dto;
        }
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }
}
