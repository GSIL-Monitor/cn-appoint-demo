package com.jd.appoint.storeapi;

import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.staff.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

public interface StoreStaffFacade {

    /**
     * 根据条件查询服务人员分页列表接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Page<ShopStaffVO>> getStaffListByCondition(SoaRequest<StoreStaffQueryVO> soaRequest);

    /**
     * 查询服务人员详情接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<ShopStaffVO> getStaffDetail(SoaRequest<StaffIdVenderIdStoreIdVO> soaRequest);

    /**
     * 添加服务人员接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse addStaff(SoaRequest<ShopStaffVO> soaRequest);

    /**
     * 修改服务人员信息接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse editStaff(SoaRequest<ShopStaffVO> soaRequest);

    /**
     * 删除服务人员接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse deleteStaff(SoaRequest<StaffIdVenderIdStoreIdVO> soaRequest);

    SoaResponse<List<ShopStaffVO>> getStaffListByStoreId(SoaRequest<Long> soaRequest);

}
