package com.jd.appoint.service.api.shop.convert;

import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.domain.shop.query.ShopStaffQueryPO;
import com.jd.appoint.vo.staff.ShopStaffQueryVO;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.appoint.vo.staff.StoreStaffQueryVO;
import com.jd.pop.wxo2o.spi.store.to.StoreQueryInfoTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class StaffConvert {

    /**
     * VO转化为StaffPO
     * @param vo
     * @return
     */
    public static ShopStaffPO toStaffPO(Object vo){
        ShopStaffPO shopStaffPO = new ShopStaffPO();
        BeanUtils.copyProperties(vo, shopStaffPO);

        return shopStaffPO;
    }

    /**
     * Staff转化为StaffPO
     * @param shopStaffPOS
     * @return
     */
    public static List<ShopStaffVO> toStaffList(List<ShopStaffPO> shopStaffPOS) {

        List<ShopStaffVO> shopStaffVOS = new ArrayList<>();
        if(shopStaffPOS.isEmpty()){
            return shopStaffVOS;
        }
        shopStaffPOS.forEach(staffPO -> {

            shopStaffVOS.add(toStaffVO(staffPO));
        });
        return shopStaffVOS;
    }

    /**
     * StaffPO转化为Staff
     * @param shopStaffPO
     * @return
     */
    public static ShopStaffVO toStaffVO(ShopStaffPO shopStaffPO) {
        ShopStaffVO shopStaffVO = new ShopStaffVO();
        BeanUtils.copyProperties(shopStaffPO, shopStaffVO);
        return shopStaffVO;
    }

    public static ShopStaffQueryPO toStaffQueryPO(ShopStaffQueryVO shopStaffQueryVO) {

        ShopStaffQueryPO shopStaffQueryPO = new ShopStaffQueryPO();
        shopStaffQueryPO.setVenderId(shopStaffQueryVO.getVenderId());
        shopStaffQueryPO.setServerName(shopStaffQueryVO.getServerName());
        shopStaffQueryPO.setServerPhone(shopStaffQueryVO.getServerPhone());
        shopStaffQueryPO.setUserPin(shopStaffQueryVO.getUserPin());
        if(shopStaffQueryVO.getPage() != null) {
            shopStaffQueryPO.setPageNumber(shopStaffQueryVO.getPage().getPageNumber());
            shopStaffQueryPO.setPageSize(shopStaffQueryVO.getPage().getPageSize());
        }
        return shopStaffQueryPO;
    }

    public static ShopStaffQueryPO toStaffQueryPO(StoreStaffQueryVO storeStaffQueryVO) {

        ShopStaffQueryPO shopStaffQueryPO = new ShopStaffQueryPO();
        shopStaffQueryPO.setVenderId(storeStaffQueryVO.getVenderId());
        shopStaffQueryPO.setServerName(storeStaffQueryVO.getServerName());
        shopStaffQueryPO.setServerPhone(storeStaffQueryVO.getServerPhone());
        shopStaffQueryPO.setUserPin(storeStaffQueryVO.getUserPin());
        shopStaffQueryPO.setStoreId(storeStaffQueryVO.getStoreId());
        if(storeStaffQueryVO.getPage() != null) {
            shopStaffQueryPO.setPageNumber(storeStaffQueryVO.getPage().getPageNumber());
            shopStaffQueryPO.setPageSize(storeStaffQueryVO.getPage().getPageSize());
        }
        return shopStaffQueryPO;
    }
}
