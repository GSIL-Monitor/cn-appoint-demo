package com.jd.appoint.service.shop;

import com.jd.appoint.domain.shop.ShopWorkTimeItemPO;
import com.jd.appoint.domain.shop.ShopWorkTimePO;
import com.jd.appoint.domain.shop.query.ShopWorkTimeQuery;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * Created by luqiang3 on 2018/5/5.
 */
public interface ShopWorkTimeService {

    /**
     * 添加店铺服务时间
     * @param shopWorkTimePO
     */
    int insertShopWorkTime(ShopWorkTimePO shopWorkTimePO);

    /**
     * 添加店铺服务时间及明细
     * @param shopWorkTimePO
     * @param shopWorkTimeItemPOs
     */
    Boolean insertShopWorkTimeAndItem(ShopWorkTimePO shopWorkTimePO, List<ShopWorkTimeItemPO> shopWorkTimeItemPOs);

    /**
     * 服务时间查询
     * @param shopWorkTimeQuery
     * @return
     */
    ShopWorkTimePO queryShopWorkTime(ShopWorkTimeQuery shopWorkTimeQuery);

    /**
     * 更新服务时间
     * @param shopWorkTimePO
     * @return
     */
    int updateShopWorkTime(ShopWorkTimePO shopWorkTimePO);

    /**
     * 通过主键分别更新服务时间和服务时间项表
     * @param shopWorkTimePO
     * @param shopWorkTimeItemPOs
     * @return
     */
    Boolean updateShopWorkTimeAndItemById(ShopWorkTimePO shopWorkTimePO, List<ShopWorkTimeItemPO> shopWorkTimeItemPOs);

    /**
     * 保存服务时间
     * @param workTime
     * @return
     */
    SoaResponse saveTime(WorkTime workTime);

    /**
     * 查询服务时间
     * @param workTimeQuery
     * @return
     */
    SoaResponse<WorkTime> searchTime(WorkTimeQuery workTimeQuery);

    /**
     * 编辑服务时间
     * @param workTime
     * @return
     */
    SoaResponse editTime(WorkTime workTime);

}
