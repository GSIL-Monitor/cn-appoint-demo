package com.jd.appoint.shop.vo;

import com.jd.appoint.vo.staff.ShopStaffVO;

/**
 * Created by bjliuyong on 2018/6/4.
 */
public class StaffVO {

    private ShopStaffVO shopStaffVO ;

    private IdentityVO identityVO  ;

    public StaffVO() {

    }

    public ShopStaffVO getShopStaffVO() {
        return shopStaffVO;
    }

    public void setShopStaffVO(ShopStaffVO shopStaffVO) {
        this.shopStaffVO = shopStaffVO;
    }

    public IdentityVO getIdentityVO() {
        return identityVO;
    }

    public void setIdentityVO(IdentityVO identityVO) {
        this.identityVO = identityVO;
    }
}
