package com.jd.appoint.service.shop;

import com.github.pagehelper.PageInfo;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.domain.shop.query.ShopStaffQueryPO;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * Created by lishuaiwei on 2018/5/5.
 */
public interface ShopStaffService {
    /**
     * 条件查询服务人员列表(带分页）
     *
     * @param shopStaffQueryPO
     */
    PageInfo<ShopStaffPO> getStaffListByConditionWithPage(ShopStaffQueryPO shopStaffQueryPO);

    /**
     * 条件查询服务人员列表（不带分页）
     *
     * @param shopStaffQueryPO
     */
    List<ShopStaffPO> getStaffListByCondition(ShopStaffQueryPO shopStaffQueryPO);

    /**
     * 查询服务人员详情
     *
     * @param shopStaffPO
     */
    ShopStaffPO getStaffDetail(ShopStaffPO shopStaffPO);

    /**
     * 添加服务人员
     *
     * @param shopStaffPO
     */
    SoaResponse addStaff(ShopStaffPO shopStaffPO);

    /**
     * 更新服务人员信息
     *
     * @param shopStaffPO
     */
    SoaResponse editStaff(ShopStaffPO shopStaffPO);

    /**
     * 删除服务人员
     * @param shopStaffPO
     * @return
     */
    Integer deleteStaff(ShopStaffPO shopStaffPO);

    /**
     * 根据id获得员工
     * @param staffId
     * @return
     */
    ShopStaffPO getStaffById(Long staffId);

    /**
     * 绑定userPin
     * @param shopStaffPO
     */
    void bindUserPin(ShopStaffPO shopStaffPO);

    ShopStaffPO getStaffByUserPin(String staffUserPin);
}
