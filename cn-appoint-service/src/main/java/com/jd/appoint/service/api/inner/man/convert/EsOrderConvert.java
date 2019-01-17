package com.jd.appoint.service.api.inner.man.convert;

import com.jd.appoint.inner.man.dto.OrderListDTO;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.page.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/18.
 */
public class EsOrderConvert {

    /**
     * 转换为Es的请求参数
     * @param page
     * @return
     */
    public static Page<AppointOrderDetailVO> toEsRequest(com.jd.appoint.page.Page page){
        com.jd.appoint.vo.page.Page<AppointOrderDetailVO> target = new com.jd.appoint.vo.page.Page();
        BeanUtils.copyProperties(page, target);
        return target;
    }

    /**
     * 将Es的结果转换为man端查询的结果
     * @param page
     * @return
     */
    public static com.jd.appoint.page.Page<OrderListDTO> toManResponse(Page<AppointOrderDetailVO> page){
        com.jd.appoint.page.Page<OrderListDTO> pageResult = new com.jd.appoint.page.Page<>();
        if(null == page || CollectionUtils.isEmpty(page.getList())){
            return pageResult;
        }
        BeanUtils.copyProperties(page, pageResult);
        List<OrderListDTO> orderListResultDTOList = new ArrayList<>();
        for(AppointOrderDetailVO vo : page.getList()){
            OrderListDTO dto = new OrderListDTO();
            BeanUtils.copyProperties(vo, dto);
            orderListResultDTOList.add(dto);
        }
        pageResult.setList(orderListResultDTOList);
        return pageResult;
    }
}
