package com.jd.appoint.shopapi;

import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.staff.ShopStaffIdVenderIdVO;
import com.jd.appoint.vo.staff.ShopStaffQueryVO;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * Created by lishuaiwei on 2018/5/7.
 * 服务人员管理服务（供shop端商家管理调用）
 */
public interface ShopStaffFacade {

    /**
     * 根据条件查询服务人员分页列表接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<Page<ShopStaffVO>> getStaffListByCondition(SoaRequest<ShopStaffQueryVO> soaRequest);

    /**
     * 查询服务人员详情接口
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<ShopStaffVO> getStaffDetail(SoaRequest<ShopStaffIdVenderIdVO> soaRequest);

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
    SoaResponse deleteStaff(SoaRequest<ShopStaffIdVenderIdVO> soaRequest);

    /**
     * 根据vender_id查询服务人员列表接口（用于下拉框展示）
     *
     * @param soaRequest
     * @return
     */
    SoaResponse<List<ShopStaffVO>> getStaffListByVenderId(SoaRequest<Long> soaRequest);

}
