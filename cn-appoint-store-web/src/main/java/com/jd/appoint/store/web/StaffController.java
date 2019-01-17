package com.jd.appoint.store.web;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shopapi.ShopStaffFacade;
import com.jd.appoint.store.service.JICCService;
import com.jd.appoint.store.util.Constants;
import com.jd.appoint.store.util.LoginInfoGetter;
import com.jd.appoint.store.util.Utils;
import com.jd.appoint.store.vo.IdentityVO;
import com.jd.appoint.store.vo.StaffVO;
import com.jd.appoint.storeapi.StoreStaffFacade;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.staff.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bjliuyong on 2018/5/15.
 */
@Controller
@RequestMapping("/api/staff")
public class StaffController {

    @Resource
    private StoreStaffFacade storeStaffFacade;

    @Resource
    private JICCService jiccService;
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @ApiOperation(value = "服务人员列表", httpMethod = "GET", response = ShopStaffVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", paramType = "query"),
            @ApiImplicitParam(name = "name", paramType = "query"),
            @ApiImplicitParam(name = "pin", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<Page<ShopStaffVO>> getStaffList(String phone, String name, String pin,
                                                       @RequestParam(defaultValue = "1") int pageNumber
            , @RequestParam(defaultValue = Integer.MAX_VALUE + "") int pageSize) {

        return Utils.call(() -> {
            SoaRequest<StoreStaffQueryVO> soaRequest = new SoaRequest<>();
            StoreStaffQueryVO storeStaffQueryVO = new StoreStaffQueryVO();
            storeStaffQueryVO.setVenderId(LoginInfoGetter.getVenderId());
            storeStaffQueryVO.setStoreId(LoginInfoGetter.getStoreId());
            storeStaffQueryVO.setServerPhone(phone);
            storeStaffQueryVO.setServerName(name);
            storeStaffQueryVO.setUserPin(pin);
            Page page = new Page();
            page.setPageNumber(pageNumber);
            page.setPageSize(pageSize);
            storeStaffQueryVO.setPage(page);
            soaRequest.setData(storeStaffQueryVO);
            logger.info("storeStaffFacade.getStaffListByCondition 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeStaffFacade.getStaffListByCondition(soaRequest);
        });
    }


    @ApiOperation(value = "选择服务人员列表", httpMethod = "GET", response = ShopStaffVO.class)
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<ShopStaffVO>> select() {
        return Utils.call(() -> {
            SoaRequest<Long> soaRequest = new SoaRequest<>();
            soaRequest.setData(LoginInfoGetter.getStoreId());
            logger.info("storeStaffFacade.getStaffListByStoreId 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeStaffFacade.getStaffListByStoreId(soaRequest);
        });
    }

    @ApiOperation(value = "服务人员详情", httpMethod = "GET", response = StaffVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "query"),
    })
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<StaffVO> getStaffDetail(long id) {

        return Utils.call(() -> {
            SoaRequest<StaffIdVenderIdStoreIdVO> soaRequest = new SoaRequest<>();
            StaffIdVenderIdStoreIdVO staffIdVenderIdStoreIdVO = new StaffIdVenderIdStoreIdVO();
            Long venderId = LoginInfoGetter.getVenderId();
            staffIdVenderIdStoreIdVO.setVenderId(venderId);
            staffIdVenderIdStoreIdVO.setStoreId(LoginInfoGetter.getStoreId());
            staffIdVenderIdStoreIdVO.setId(id);
            soaRequest.setData(staffIdVenderIdStoreIdVO);
            logger.info("storeStaffFacade.getStaffDetail 入参 ：【{}】", JSON.toJSONString(soaRequest));
            SoaResponse<ShopStaffVO> soaResponse = storeStaffFacade.getStaffDetail(soaRequest);

            SoaResponse<StaffVO> ret = new SoaResponse(soaResponse.isSuccess(),
                    soaResponse.getCode(), soaResponse.getReason());
            StaffVO staffVO = new StaffVO();
            ret.setResult(staffVO);
            ShopStaffVO shopStaffVO = soaResponse.getResult();
            staffVO.setShopStaffVO(shopStaffVO);

            String identityId = shopStaffVO == null ? null : shopStaffVO.getSecurityId();
            if (StringUtils.isNotBlank(identityId)) {
                IdentityVO identityVO = Utils.call(() -> jiccService.queryIdentityAndImg("test", identityId, String.valueOf(venderId))
                        , Constants.UMP_JICC_QUERY);
                staffVO.setIdentityVO(identityVO);
            }
            return ret;
        });
    }

    @ApiOperation(value = "服务人员保存", httpMethod = "POST", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopStaffVO", dataType = "ShopStaffVO")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse getSaveOrEdit(@RequestBody ShopStaffVO shopStaffVO) {

        return Utils.call(() -> {
            SoaRequest<ShopStaffVO> soaRequest = new SoaRequest<>();
            shopStaffVO.setVenderId(LoginInfoGetter.getVenderId());
            shopStaffVO.setStoreId(LoginInfoGetter.getStoreId());
            soaRequest.setData(shopStaffVO);
            Long id = shopStaffVO.getId();
            if (id == null || id == 0) {
                return storeStaffFacade.addStaff(soaRequest);
            } else {
                return storeStaffFacade.editStaff(soaRequest);
            }

        });
    }

    @ApiOperation(value = "服务人员删除", httpMethod = "DELETE", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path")
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public SoaResponse deleteStaff(@PathVariable Long id) {

        return Utils.call(() -> {
            SoaRequest<StaffIdVenderIdStoreIdVO> soaRequest = new SoaRequest<>();
            StaffIdVenderIdStoreIdVO staffIdVenderIdStoreIdVO = new StaffIdVenderIdStoreIdVO();
            staffIdVenderIdStoreIdVO.setVenderId(LoginInfoGetter.getVenderId());
            staffIdVenderIdStoreIdVO.setStoreId(LoginInfoGetter.getStoreId());
            staffIdVenderIdStoreIdVO.setId(id);
            soaRequest.setData(staffIdVenderIdStoreIdVO);
            return storeStaffFacade.deleteStaff(soaRequest);

        });
    }

}
