package com.jd.appoint.dao.shop;


import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.domain.shop.query.ShopStaffQueryPO;

import java.util.List;

@MybatisRepository
public interface ShopStaffDao extends MybatisDao<ShopStaffPO> {

    /**
     * 条件查询服务人员列表
     *
     * @param shopStaffQueryPO
     * @return
     */
    List<ShopStaffPO> selectStaffListByCondition(ShopStaffQueryPO shopStaffQueryPO);

    /**
     * 查询服务人员详情
     *
     * @param shopStaffPO
     * @return
     */
    ShopStaffPO selectStaffDetail(ShopStaffPO shopStaffPO);

    /**
     * 添加服务人员
     *
     * @param shopStaffPO
     * @return
     */
    Integer insertStaff(ShopStaffPO shopStaffPO);

    /**
     * 修改服务人员
     *
     * @param shopStaffPO
     * @return
     */
    Integer updateStaff(ShopStaffPO shopStaffPO);

    /**
     * 根据手机号查询服务人员
     *
     * @param serverPhone
     * @return
     */
    ShopStaffPO fetchStaffByPhone(String serverPhone);

    Integer updateUserPinByPhone(ShopStaffPO shopStaffPO);

    /**
     * 根据手机号查询员工列表，包含所有状态的
     *
     * @param serverPhone
     * @return
     */
    List<ShopStaffPO> getStaffListByPhone(String serverPhone);

    Long getDeletedStaffByPhoneAndVenderId(ShopStaffPO shopStaffPO);

    /**
     * 被删除的员工新增时重新启用
     *
     * @param shopStaffPO
     */
    void recoverStaff(ShopStaffPO shopStaffPO);

    /**
     * 删除员工
     *
     * @param shopStaffPO
     */
    Integer deleteStaff(ShopStaffPO shopStaffPO);

    /**
     * 根据userPin 获取
     *
     * @param userPin
     * @return
     */
    ShopStaffPO getStaffByUserPin(String userPin);
}