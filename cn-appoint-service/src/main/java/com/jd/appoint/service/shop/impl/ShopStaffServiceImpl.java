package com.jd.appoint.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.appoint.common.enums.SoaCodeEnum;
import com.jd.appoint.dao.shop.ShopStaffDao;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.domain.shop.query.ShopStaffQueryPO;
import com.jd.appoint.service.shop.ShopStaffService;
import com.jd.jim.cli.Cluster;
import com.jd.travel.base.soa.SoaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lishuaiwei on 2018/5/5.
 */
@Service("shopStaffService")
public class ShopStaffServiceImpl implements ShopStaffService {

    private static final Logger logger = LoggerFactory.getLogger(ShopStaffServiceImpl.class);

    @Autowired
    private ShopStaffDao shopStaffDao;
    @Autowired
    private Cluster jimClient;

    /**
     * 条件查询服务人员列表（带分页）
     *
     * @param shopStaffQueryPO
     */
    @Override
    public PageInfo<ShopStaffPO> getStaffListByConditionWithPage(ShopStaffQueryPO shopStaffQueryPO) {
        PageInfo<ShopStaffPO> pages =
                PageHelper.startPage(shopStaffQueryPO.getPageNumber(), shopStaffQueryPO.getPageSize(), true).
                        doSelectPageInfo(() -> {
                            shopStaffDao.selectStaffListByCondition(shopStaffQueryPO);
                        });
        return pages;
    }

    /**
     * 条件查询服务人员列表（不分页）
     *
     * @param shopStaffQueryPO
     */
    @Override
    public List<ShopStaffPO> getStaffListByCondition(ShopStaffQueryPO shopStaffQueryPO) {
        return shopStaffDao.selectStaffListByCondition(shopStaffQueryPO);
    }

    /**
     * 查询服务人员详情
     *
     * @param shopStaffPO
     */
    @Override
    public ShopStaffPO getStaffDetail(ShopStaffPO shopStaffPO) {
        return shopStaffDao.selectStaffDetail(shopStaffPO);
    }

    /**
     * 添加服务人员
     *
     * @param shopStaffPO
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public SoaResponse addStaff(ShopStaffPO shopStaffPO) {
        Long id = getExistStaffByPhone(shopStaffPO.getServerPhone());
        //status=1的手机号已存在，提示重复
        if (id != null) {
            return new SoaResponse(SoaCodeEnum.DATA_DUPLICATED, "相同手机号重复添加");
        }

        //查询该商家该员工是否之前被删除过
        Long deletedId = shopStaffDao.getDeletedStaffByPhoneAndVenderId(shopStaffPO);
        if (deletedId == null) {
            //没被删除过则新增
            shopStaffDao.insertStaff(shopStaffPO);
        } else {
            //被删除过则更新
            shopStaffPO.setId(deletedId);
            shopStaffDao.recoverStaff(shopStaffPO);
        }
        return new SoaResponse();

    }


    private Long getExistStaffByPhone(String phone) {
        //验证没有有效的手机号
        ShopStaffPO phonePO = new ShopStaffPO();
        phonePO.setServerPhone(phone);
        phonePO = shopStaffDao.selectStaffDetail(phonePO);
        return phonePO == null ? null : phonePO.getId();
    }


    /**
     * 更新服务人员
     *
     * @param shopStaffPO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public SoaResponse editStaff(ShopStaffPO shopStaffPO) {

        //查询可用的员工id
        Long id = getExistStaffByPhone(shopStaffPO.getServerPhone());
        //手机号已存在且不属于自己则返回手机号重复
        if (id != null && !id.equals(shopStaffPO.getId())) {
            return new SoaResponse(SoaCodeEnum.DATA_DUPLICATED);
        }
        shopStaffDao.updateStaff(shopStaffPO);
        return new SoaResponse();

    }

    /**
     * 删除服务人员接口
     *
     * @param shopStaffPO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer deleteStaff(ShopStaffPO shopStaffPO) {
        return shopStaffDao.deleteStaff(shopStaffPO);
    }

    /**
     * 根据id查询服务人员
     *
     * @param staffId
     * @return
     */
    @Override
    public ShopStaffPO getStaffById(Long staffId) {
        return shopStaffDao.findById(staffId);
    }

    /**
     * 绑定userPin
     *
     * @param shopStaffPO
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void bindUserPin(ShopStaffPO shopStaffPO) {
        shopStaffDao.updateUserPinByPhone(shopStaffPO);
    }

    @Override
    public ShopStaffPO getStaffByUserPin(String staffUserPin) {
        return shopStaffDao.getStaffByUserPin(staffUserPin);
    }


}
