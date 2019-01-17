package com.jd.appoint.service.api.shop.convert;

import com.jd.appoint.domain.config.OrderOperateConfigPO;
import com.jd.appoint.vo.dynamic.OperateItemVo;

/**
 * Created by shaohongsen on 2018/6/25.
 */
public class OrderOperateConfigConvert {
    public static OperateItemVo convertToOperateItemVo(OrderOperateConfigPO config) {
        OperateItemVo operateItemVo = new OperateItemVo();
        operateItemVo.setConfirmInfo(config.getConfirmInfo());
        operateItemVo.setCustomData(config.getCustomData());
        if (config.getCustomType() != null) {
            operateItemVo.setCustomType(config.getCustomType().getIntValue());
        }
        operateItemVo.setIsBatch(config.getIsBatch());
        operateItemVo.setLabel(config.getLabel());
        operateItemVo.setOperateType(config.getOperateType().getIntValue());
        if (config.getServerType() != null) {
            operateItemVo.setServerType(config.getServerType().getIntValue());
        }
        return operateItemVo;
    }
}
