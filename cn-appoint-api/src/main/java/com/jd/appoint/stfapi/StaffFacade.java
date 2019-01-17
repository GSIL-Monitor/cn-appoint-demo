package com.jd.appoint.stfapi;

import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by lishuaiwei on 2018/5/7.
 * 服务人员管理服务（供小程序端调用）
 */
public interface StaffFacade {

    /**
     * 根据userPin查询服务人员详情接口
     * @param soaRequest
     * @return
     */
    SoaResponse<ShopStaffVO> getStaffDetailByUserPin(SoaRequest<String> soaRequest);

    /**
     * 服务人员登录接口
     * @param soaRequest
     * @return
     */
    SoaResponse<String> staffLogin(SoaRequest<String> soaRequest);


}
