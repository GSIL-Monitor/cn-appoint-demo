package com.jd.appoint.service.api.shop.convert;

import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.vo.dynamic.FilterItemVO;

import java.util.Optional;

/**
 * Created by shaohongsen on 2018/6/25.
 */
public class OrderListConfigConverter {

    public static FilterItemVO convertToFilterItemVO(OrderListConfigPO config) {
        FilterItemVO filterItemVO = new FilterItemVO();
        if(config.getQueryType() != null){
            filterItemVO.setAlias(config.getAlias()+"."+config.getQueryType());
        }else{
            filterItemVO.setAlias(config.getAlias());
        }

        filterItemVO.setInputType(config.getInputType().getIntValue());
        filterItemVO.setItemData(config.getItemData());
        filterItemVO.setLabel(config.getLabel());
        filterItemVO.setLineNo(config.getLineNo());
        filterItemVO.setWidth(config.getWidth());
        return filterItemVO;
    }
}
