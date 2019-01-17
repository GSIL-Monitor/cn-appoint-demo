package com.jd.appoint.service.api.shop;

import com.github.pagehelper.PageInfo;
import com.jd.appoint.common.enums.SoaCodeEnum;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.domain.shop.query.ShopStaffQueryPO;
import com.jd.appoint.service.shop.ShopStaffService;
import com.jd.appoint.service.api.shop.convert.StaffConvert;
import com.jd.appoint.shopapi.ShopStaffFacade;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.staff.ShopStaffIdVenderIdVO;
import com.jd.appoint.vo.staff.ShopStaffQueryVO;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.xn.slog.LogSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuaiwei on 2018/5/7.
 * SHOP端的服务人员管理服务实现类
 */
@Service("shopStaffFacade")
public class ShopStaffFacadeImpl implements ShopStaffFacade {

    private Logger logger = LoggerFactory.getLogger(ShopStaffFacadeImpl.class);
    @Autowired
    private ShopStaffService shopStaffService;

    /**
     * 条件查询服务人员列表接口
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "条件查询服务人员列表接口", classify = ShopStaffFacadeImpl.class))
    public SoaResponse<Page<ShopStaffVO>> getStaffListByCondition(@ValideGroup SoaRequest<ShopStaffQueryVO> soaRequest) {
        SoaResponse<Page<ShopStaffVO>> soaResponse = new SoaResponse<>();
        ShopStaffQueryVO shopStaffQueryVO = soaRequest.getData();
        Page<ShopStaffVO> page = new Page<>();

        ShopStaffQueryPO shopStaffQueryPO = StaffConvert.toStaffQueryPO(shopStaffQueryVO);
        //pageSize和pageNumber都不传 不进行分页
        if (shopStaffQueryVO.getPage() == null) {
            List<ShopStaffPO> staffList = shopStaffService.getStaffListByCondition(shopStaffQueryPO);
            List<ShopStaffVO> shopStaffVOS = StaffConvert.toStaffList(staffList);
            page.setList(shopStaffVOS);
        } else {//分页

            PageInfo<ShopStaffPO> pageInfo = shopStaffService.getStaffListByConditionWithPage(shopStaffQueryPO);
            page = convertPageInfo(pageInfo);
        }

        soaResponse.setResult(page);
        return soaResponse;

    }

    private Page<ShopStaffVO> convertPageInfo(PageInfo<ShopStaffPO> pageInfo) {
        Page<ShopStaffVO> page = new Page<>();
        page.setPageNumber(pageInfo.getPageNum());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotalCount(pageInfo.getTotal());
        List<ShopStaffPO> shopStaffPOS = pageInfo.getList();
        List<ShopStaffVO> shopStaffVOList = StaffConvert.toStaffList(shopStaffPOS);
        page.setList(shopStaffVOList);
        return page;
    }

    /**
     * 查询服务人员详情
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "根据id查询服务人员详情接口", classify = ShopStaffFacadeImpl.class))
    public SoaResponse<ShopStaffVO> getStaffDetail(@ValideGroup SoaRequest<ShopStaffIdVenderIdVO> soaRequest) {
        SoaResponse soaResponse = new SoaResponse();
        ShopStaffIdVenderIdVO shopStaffIdVenderIdVO = soaRequest.getData();

        ShopStaffPO staffPO = StaffConvert.toStaffPO(shopStaffIdVenderIdVO);
        ShopStaffPO shopStaffPO = shopStaffService.getStaffDetail(staffPO);

        //没查询到返回null
        if (shopStaffPO == null) {
            return soaResponse;
        }
        //正常返回
        ShopStaffVO shopStaffVO = StaffConvert.toStaffVO(shopStaffPO);

        soaResponse.setResult(shopStaffVO);
        return soaResponse;

    }

    /**
     * 添加服务人员接口
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "添加服务人员接口", classify = ShopStaffFacadeImpl.class))
    public SoaResponse addStaff(@ValideGroup SoaRequest<ShopStaffVO> soaRequest) {
        SoaResponse soaResponse = new SoaResponse<>();
        ShopStaffVO shopStaffVO = soaRequest.getData();

        ShopStaffPO shopStaffPO = StaffConvert.toStaffPO(shopStaffVO);

        return shopStaffService.addStaff(shopStaffPO);

    }

    /**
     * 修改服务人员信息接口
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "修改服务人员信息接口", classify = ShopStaffFacadeImpl.class))
    public SoaResponse editStaff(@ValideGroup SoaRequest<ShopStaffVO> soaRequest) {
        SoaResponse soaResponse = new SoaResponse<>();
        ShopStaffVO shopStaffVO = soaRequest.getData();
        if (shopStaffVO.getId() == null) {
            logger.error("修改服务人员id为空，soaRequest={}", LogSecurity.toJSONString(soaRequest));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "修改服务人员id为空");
        }
        //前端传来usePin也不更新
        shopStaffVO.setUserPin(null);
        ShopStaffPO shopStaffPO = StaffConvert.toStaffPO(shopStaffVO);
        return shopStaffService.editStaff(shopStaffPO);
    }

    /**
     * 删除服务人员
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "删除服务人员接口", classify = ShopStaffFacadeImpl.class))
    public SoaResponse deleteStaff(@ValideGroup SoaRequest<ShopStaffIdVenderIdVO> soaRequest) {
        SoaResponse soaResponse = new SoaResponse();
        ShopStaffIdVenderIdVO shopStaffIdVenderIdVO = soaRequest.getData();
        ShopStaffPO staffPO = StaffConvert.toStaffPO(shopStaffIdVenderIdVO);
        staffPO.setStatus(StatusEnum.DELETE);
        Integer rows = shopStaffService.deleteStaff(staffPO);
        if (rows == 0) {
            logger.error("要删除的服务人员不存在，soaRequest={}", LogSecurity.toJSONString(soaRequest));
            return new SoaResponse(SoaCodeEnum.DATA_DELETE_UNEXISTING, "要删除的服务人员不存在");
        }

        return soaResponse;

    }

    /**
     * 根据vender_id查询服务人员列表接口（用于下拉框展示）
     *
     * @param soaRequest
     * @return
     */
    @Override
    public SoaResponse<List<ShopStaffVO>> getStaffListByVenderId(SoaRequest<Long> soaRequest) {
        SoaResponse<List<ShopStaffVO>> soaResponse = new SoaResponse<>();
        Long venderId = soaRequest.getData();

        if (venderId == null) {
            logger.error("获取服务人员列表venderId为空，soaRequest={}", LogSecurity.toJSONString(soaRequest));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "获取服务人员列表venderId为空");
        }

        ShopStaffQueryPO shopStaffQueryPO = new ShopStaffQueryPO();
        shopStaffQueryPO.setVenderId(venderId);
        List<ShopStaffPO> staffList = shopStaffService.getStaffListByCondition(shopStaffQueryPO);
        List<ShopStaffVO> shopStaffVOS = new ArrayList<>();
        //只返回serverName和id
        for (ShopStaffPO shopStaffPO : staffList) {
            ShopStaffVO shopStaffVO = new ShopStaffVO();
            shopStaffVO.setId(shopStaffPO.getId());
            shopStaffVO.setServerName(shopStaffPO.getServerName());
            shopStaffVOS.add(shopStaffVO);
        }

        soaResponse.setResult(shopStaffVOS);
        return soaResponse;

    }


}
