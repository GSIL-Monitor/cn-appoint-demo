package com.jd.appoint.service.api.convert;

import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.vo.time.WorkTimeItem;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/5.
 * 服务时间项转换
 */
public class WorkTimeItemConvert {

    /**
     * WorkTimeItems转换为ShopWorkTimeItemPOs
     * @param workTimeItems
     * @return
     */
    public static List<ShopWorkTimeItemPO> toShopWorkTimeItemPOs(List<WorkTimeItem> workTimeItems, TimeShowTypeEnum timeShowType){
        if(CollectionUtils.isEmpty(workTimeItems)){
            return defaultShopWorkTimeItemPOs();
        }
        List<ShopWorkTimeItemPO> shopWorkTimeItemPOs = new ArrayList<>();
        workTimeItems.forEach(item->shopWorkTimeItemPOs.add(toShopWorkTimeItemPO(item, timeShowType)));
        return shopWorkTimeItemPOs;
    }

    /**
     * 默认的时间项
     * 当页面没有按周设置时，会存在没有ShopWorkTimeItemPO的情况
     * 为了数据统一，插入改默认项
     * @return
     */
    public static List<ShopWorkTimeItemPO> defaultShopWorkTimeItemPOs(){
        List<ShopWorkTimeItemPO> items = new ArrayList<>();
        for(int i = 1; i < 8; i++){
            ShopWorkTimeItemPO item = new ShopWorkTimeItemPO();
            item.setWeekday(i);
            item.setStatus(StatusEnum.ENABLE);
            items.add(item);
        }
        return items;
    }

    /**
     * WorkTimeItem转换为ShopWorkTimeItemPO
     * @param workTimeItem
     * @return
     */
    public static ShopWorkTimeItemPO toShopWorkTimeItemPO(WorkTimeItem workTimeItem, TimeShowTypeEnum timeShowType){
        ShopWorkTimeItemPO shopWorkTimeItemPO = new ShopWorkTimeItemPO();
        BeanUtils.copyProperties(workTimeItem, shopWorkTimeItemPO);
        if(TimeShowTypeEnum.DAY == timeShowType){
            shopWorkTimeItemPO.setWorkStart(null);
            shopWorkTimeItemPO.setWorkEnd(null);
        }
        //状态转换
        shopWorkTimeItemPO.setStatus(StatusEnum.byValue(workTimeItem.getStatus()));
        return shopWorkTimeItemPO;
    }

    /**
     * ShopWorkTimeItemPOs转换为WorkTimeItems
     * @param shopWorkTimeItemPOs
     * @return
     */
    public static List<WorkTimeItem> toWorkTimeItems(List<ShopWorkTimeItemPO> shopWorkTimeItemPOs){
        if(CollectionUtils.isEmpty(shopWorkTimeItemPOs)){
            return null;
        }
        List<WorkTimeItem> workTimeItems = new ArrayList<>();
        shopWorkTimeItemPOs.forEach(item -> workTimeItems.add(toWorkTimeItem(item)));
        return workTimeItems;
    }

    /**
     * ShopWorkTimeItemPO转换为WorkTimeItem
     * @param shopWorkTimeItemPO
     * @return
     */
    public static WorkTimeItem toWorkTimeItem(ShopWorkTimeItemPO shopWorkTimeItemPO){
        WorkTimeItem workTimeItem = new WorkTimeItem();
        BeanUtils.copyProperties(shopWorkTimeItemPO, workTimeItem);
        //状态转换
        workTimeItem.setStatus(shopWorkTimeItemPO.getStatus().getIntValue());
        return workTimeItem;
    }
}
