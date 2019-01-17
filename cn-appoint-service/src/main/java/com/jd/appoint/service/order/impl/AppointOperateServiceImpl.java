package com.jd.appoint.service.order.impl;

import com.jd.appoint.domain.config.OrderOperateConfigPO;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.service.api.shop.convert.OrderOperateConfigConvert;
import com.jd.appoint.service.config.OrderOperateConfigService;
import com.jd.appoint.service.order.AppointOperateService;
import com.jd.appoint.vo.dynamic.OperateItemVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by gaoxiaoqing on 2018/6/28.
 */
@Service(value = "appointOperateService")
public class AppointOperateServiceImpl implements AppointOperateService {
    @Resource
    private OrderOperateConfigService orderOperateConfigService;

    @Override
    public Map<Integer, List<OperateItemVo>> operateList(String businessCode, Integer platform) {
        List<OrderOperateConfigPO> orderOperateConfigPOList =
                orderOperateConfigService.normalOperateList(businessCode,null,platform);
        if(CollectionUtils.isEmpty(orderOperateConfigPOList)){
            return null;
        }
        //分组获取状态对应的操作项
        Map<AppointStatusEnum, List<OrderOperateConfigPO>> orderOperateMap = orderOperateConfigPOList.stream()
                .collect(Collectors.groupingBy(OrderOperateConfigPO::getAppointStatus, Collectors.toList()));
        Map<Integer, List<OperateItemVo>> operateListMap = new HashMap<>();
        //组装状态对应的操作项
        orderOperateMap.forEach((statusEnum, orderOperateConfigPOS) -> {
            List<OperateItemVo> operateVOList = orderOperateConfigPOS.stream().map(orderOperateConfigPO -> {
                return OrderOperateConfigConvert.convertToOperateItemVo(orderOperateConfigPO);
            }).collect(Collectors.toList());
            operateListMap.put(statusEnum.getIntValue(), operateVOList);
        });
        return operateListMap;
    }
}
