package com.jd.appoint.service.api.shop.convert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.vo.dynamic.DynamicAppointOrder;
import com.jd.appoint.vo.dynamic.FieldVo;
import com.jd.appoint.vo.dynamic.table.DynamicTable;
import com.jd.appoint.vo.dynamic.table.Th;
import com.jd.appoint.vo.page.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/6/26.
 */
public class DynamicTableConverter {
    public static DynamicTable convert(Page<DynamicAppointOrder> dynamicAppointOrderPage, List<OrderListConfigPO> orderListConfigPOS) {
        DynamicTable table = new DynamicTable();
        if (orderListConfigPOS != null) {
            List<Th> ths = orderListConfigPOS.stream()
                    .map(orderListConfigPO -> {
                        Th th = new Th();
                        th.setKey(orderListConfigPO.getAlias());
                        th.setTitle(orderListConfigPO.getLabel());
                        return th;
                    })
                    .collect(Collectors.toList());
            table.setThList(ths);
        }
        if (dynamicAppointOrderPage.getList() != null) {
            List<Map<String, String>> dataSource = Lists.newArrayList();
            for (DynamicAppointOrder order : dynamicAppointOrderPage.getList()) {
                Map<String, String> map = Maps.newHashMap();
                //所有数据都有ID
                map.put("id", String.valueOf(order.getId()));
                if (order.getFieldVos() != null) {
                    for (FieldVo fieldVo : order.getFieldVos()) {
                        map.put(fieldVo.getAlias(), fieldVo.getValue());
                    }
                }
                dataSource.add(map);
            }
            table.setDataSource(dataSource);
        }
        table.setPageNum(dynamicAppointOrderPage.getPageNumber());
        table.setPageSize(dynamicAppointOrderPage.getPageSize());
        table.setTotal(dynamicAppointOrderPage.getTotalCount());
        return table;
    }
}
