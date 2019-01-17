package com.jd.appoint.service.api.convert;

import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.stock.StockInfoPO;
import com.jd.appoint.domain.stock.query.StockInfoQuery;
import com.jd.appoint.vo.schedule.DateItem;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 * 库存信息转换
 */
public class StockConvert {

    /**
     * shop-api日历VO转换为数据库库存Query
     * @param scheduleVO
     * @return
     */
    public static StockInfoQuery scheduleVO2StockInfoQuery(ScheduleVO scheduleVO){
        StockInfoQuery query = new StockInfoQuery();
        query.setBusinessCode(scheduleVO.getBusinessCode());
        query.setVenderId(scheduleVO.getVenderId());
        query.setStoreCode(scheduleVO.getStoreCode());
        query.setSkuId(scheduleVO.getSkuId());
        query.setStartDate(AppointDateUtils.getDate2Date("yyyy-MM-dd", scheduleVO.getStartDate()));
        query.setEndDate(AppointDateUtils.getDate2Date("yyyy-MM-dd", scheduleVO.getEndDate()));
        return query;
    }

    /**
     * shop-api数据库库存PO转换为产能日历结果
     * @param stockInfoPOs
     * @return
     */
    public static ScheduleResult stockInfoPO2ScheduleResult(List<StockInfoPO> stockInfoPOs){
        if(CollectionUtils.isEmpty(stockInfoPOs)){
            return null;
        }
        ScheduleResult result = new ScheduleResult();
        List<DateItem> dateItems = new ArrayList<>();
        for(StockInfoPO stockInfoPO : stockInfoPOs){
            DateItem dateItem = new DateItem();
            dateItem.setDate(stockInfoPO.getDate());
            dateItem.setTotalQty(stockInfoPO.getTotalQty());
            dateItem.setSaleQty(stockInfoPO.getSaleQty());
            dateItems.add(dateItem);
        }
        result.setDateItems(dateItems);
        return result;
    }
}
