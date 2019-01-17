package com.jd.appoint.web.order.convert;

import com.jd.appoint.inner.man.dto.OrderListDTO;
import com.jd.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luqiang3 on 2018/5/18.
 * 订单列表相关转换
 */
public class OrderListConvert {

    /**
     * 转换为请求Es的入参
     * @param orderListDTO
     * @return
     */
    public static Map<String, String> toSearchMap(OrderListDTO orderListDTO){
        Map<String, String> map = new HashMap<>();
        if(null == orderListDTO){
            return map;
        }
        map.put("id.EQ", toStr(orderListDTO.getId()));
        map.put("businessCode.EQ", orderListDTO.getBusinessCode());
        map.put("venderId.EQ", toStr(orderListDTO.getVenderId()));
        map.put("appointStatus.EQ", toStr(orderListDTO.getAppointStatus()));
        map.put("customerName.EQ", orderListDTO.getCustomerName());
        map.put("customerUserPin.EQ", orderListDTO.getCustomerUserPin());
        map.put("serverType.EQ", toStr(orderListDTO.getServerType()));
        if(StringUtils.isNotBlank(orderListDTO.getCreatedRange())){
            //格式：yyyy-MM-hh HH:mm:ss - yyyy-MM-hh HH:mm:ss
            String[] createds = orderListDTO.getCreatedRange().split(" - ");
            map.put("created.GTE", toDateStr(createds[0]));
            map.put("created.LTE", toDateStr(createds[1]));
        }
        return map;
    }

    /**
     * 转换为订单列表页面VO
     * @param dtos
     * @return
     */
//    public static List<OrderListVO> toOrderListVOs(List<OrderListDTO> dtos){
//        List<OrderListVO> orderListVOs = new ArrayList<>();
//        if(CollectionUtils.isEmpty(dtos)){
//            return orderListVOs;
//        }
//        for(OrderListDTO dto : dtos){
//            OrderListVO vo = toOrderListVO(dto);
//            orderListVOs.add(vo);
//        }
//        return orderListVOs;
//    }

    /**
     * 转换为订单列表页面VO
     * @param dto
     * @return
     */
//    public static OrderListVO toOrderListVO(OrderListDTO dto){
//        OrderListVO orderListVO = new OrderListVO();
//        if(null == dto){
//            return orderListVO;
//        }
//        BeanUtils.copyProperties(dto, orderListVO);
//        return orderListVO;
//    }

    /**
     * 将任意类型的数据转换为字符类型
     * 如果为空，则返回null
     * @param obj
     * @return
     */
    private static String toStr(Object obj){
        return null == obj ? null : obj.toString();
    }

    /**
     * 将日期格式由yyyy-MM-hh HH:mm:ss 转换为 yyyy-MM-hhTHH:mm:ssZ
     * @param str
     * @return
     */
    private static String toDateStr(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        String[] arr = str.split(" ");
        return new StringBuilder(arr[0]).append("T").append(arr[1]).append("Z").toString();
    }
}
