package com.jd.appoint.service.api.convert;

import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.enums.TimeUnitEnum;
import com.jd.appoint.domain.shop.ShopWorkTimePO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeQuery;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.common.util.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by luqiang3 on 2018/5/5.
 * 服务时间转换
 */
public class WorkTimeConvert {

    /**
     * WorkTime转换为ShopWorkTimePO
     * @param workTime
     * @return
     */
    public static ShopWorkTimePO toShopWorkTimePO(WorkTime workTime){
        ShopWorkTimePO shopWorkTimePO = new ShopWorkTimePO();
        BeanUtils.copyProperties(workTime, shopWorkTimePO);
        //时间单位转换，如果未传，默认分钟
        if(null == workTime.getTimeIntervalUnit() &&
                null != workTime.getTimeInterval()
                && workTime.getTimeInterval() >0){
            shopWorkTimePO.setTimeIntervalUnit(TimeUnitEnum.MINUTE);
        }else {
            shopWorkTimePO.setTimeIntervalUnit(TimeUnitEnum.byValue(workTime.getTimeIntervalUnit()));
        }
        if(null == workTime.getT0AdvanceUnit() &&
                null != workTime.getT0Advance()
                && workTime.getT0Advance() > 0){
            shopWorkTimePO.setT0AdvanceUnit(TimeUnitEnum.MINUTE);
        }else {
            shopWorkTimePO.setT0AdvanceUnit(TimeUnitEnum.byValue(workTime.getT0AdvanceUnit()));
        }
        //展示模式转换
        shopWorkTimePO.setTimeShowType(TimeShowTypeEnum.byValue(workTime.getTimeShowType()));
        //状态转换，当前默认有效
        shopWorkTimePO.setStatus(StatusEnum.ENABLE);
        if(StringUtils.isBlank(workTime.getStoreCode())){
            shopWorkTimePO.setStoreCode(DefaultVenderConfig.STORE_CODE);
        }
        if(null == workTime.getSkuId()){
            shopWorkTimePO.setSkuId(DefaultVenderConfig.SKU_ID);
        }
        return shopWorkTimePO;
    }

    /**
     * ShopWorkTimePO转换为WorkTime
     * @param shopWorkTimePO
     * @return
     */
    public static WorkTime toWorkTime(ShopWorkTimePO shopWorkTimePO){
        WorkTime workTime = new WorkTime();
        if(null == shopWorkTimePO){
            return workTime;
        }
        BeanUtils.copyProperties(shopWorkTimePO, workTime);
        //时间单位转换
        if(null != shopWorkTimePO.getT0AdvanceUnit()){
            workTime.setT0AdvanceUnit(shopWorkTimePO.getT0AdvanceUnit().getIntValue());
        }
        if(null != shopWorkTimePO.getTimeIntervalUnit()){
            workTime.setTimeIntervalUnit(shopWorkTimePO.getTimeIntervalUnit().getIntValue());
        }
        //展示模式转换
        workTime.setTimeShowType(shopWorkTimePO.getTimeShowType().getIntValue());
        return workTime;
    }

    /**
     * WorkTimeQuery -> ShopWorkTimeQuery
     * @param workTimeQuery
     * @return
     */
    public static ShopWorkTimeQuery workTimeQuery2ShopWorkTimeQuery(WorkTimeQuery workTimeQuery){
        ShopWorkTimeQuery query = new ShopWorkTimeQuery();
        query.setBusinessCode(workTimeQuery.getBusinessCode());
        query.setVenderId(workTimeQuery.getVenderId());
        query.setStoreCode(StringUtils.isNotBlank(workTimeQuery.getStoreCode()) ? workTimeQuery.getStoreCode() : DefaultVenderConfig.STORE_CODE);
        query.setSkuId(null != workTimeQuery.getSkuId() ? workTimeQuery.getSkuId() : DefaultVenderConfig.SKU_ID);
        return query;
    }
}
