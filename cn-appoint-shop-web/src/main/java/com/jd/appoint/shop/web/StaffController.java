package com.jd.appoint.shop.web;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shop.service.JICCService;
import com.jd.appoint.shop.util.Constants;
import com.jd.appoint.shop.util.Utils;
import com.jd.appoint.shop.util.VenderIdGetter;
import com.jd.appoint.shop.vo.IdentityVO;
import com.jd.appoint.shop.vo.StaffVO;
import com.jd.appoint.shopapi.ShopStaffFacade;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.staff.ShopStaffIdVenderIdVO;
import com.jd.appoint.vo.staff.ShopStaffQueryVO;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/5/15.
 */
@Controller
@RequestMapping("/api/staff")
public class StaffController {

    @Resource
    private ShopStaffFacade shopStaffFacade ;

    @Resource
    private JICCService jiccService ;
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @ApiOperation(value = "服务人员列表", httpMethod = "GET", response = ShopStaffVO.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phone", paramType = "query" ),
        @ApiImplicitParam(name = "name" , paramType = "query"),
        @ApiImplicitParam(name = "pin" , paramType = "query")
    })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<Page<ShopStaffVO>> getStaffList(String phone , String name ,String pin ,
        @RequestParam(defaultValue = "1") int pageNumber
        ,  @RequestParam(defaultValue = Integer.MAX_VALUE + "") int pageSize){

        return Utils.call(() -> {
            SoaRequest<ShopStaffQueryVO> soaRequest = new SoaRequest<>() ;
            ShopStaffQueryVO shopStaffQueryVO = new ShopStaffQueryVO() ;
            shopStaffQueryVO.setVenderId(VenderIdGetter.get());
            shopStaffQueryVO.setServerPhone(phone);
            shopStaffQueryVO.setServerName(name);
            shopStaffQueryVO.setUserPin(pin);
            Page page = new Page();
            page.setPageNumber(pageNumber);
            page.setPageSize(pageSize);
            shopStaffQueryVO.setPage(page);
            soaRequest.setData(shopStaffQueryVO);
            logger.info("shopStaffFacade.getStaffListByCondition 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return  shopStaffFacade.getStaffListByCondition(soaRequest) ;
        }) ;
    }


    @ApiOperation(value = "选择服务人员列表", httpMethod = "GET", response = ShopStaffVO.class)
    @RequestMapping(value = "/select" , method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<ShopStaffVO>> select(){
        return Utils.call(() -> {
            SoaRequest<Long> soaRequest = new SoaRequest<>() ;
            soaRequest.setData(VenderIdGetter.get());
            logger.info("shopStaffFacade.getStaffListByVenderId 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return  shopStaffFacade.getStaffListByVenderId(soaRequest) ;
        }) ;
    }

    @ApiOperation(value = "服务人员详情", httpMethod = "GET", response = StaffVO.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id" , paramType = "query"),
    })
    @RequestMapping(value = "/detail" , method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<StaffVO> getStaffDetail(long id ){

        return Utils.call(() -> {
            SoaRequest<ShopStaffIdVenderIdVO> soaRequest = new SoaRequest<>() ;
            ShopStaffIdVenderIdVO staffIdVenderIdVO = new ShopStaffIdVenderIdVO() ;
            long venderId = VenderIdGetter.get() ;
            staffIdVenderIdVO.setVenderId(VenderIdGetter.get());
            staffIdVenderIdVO.setId(id);
            soaRequest.setData(staffIdVenderIdVO);
            logger.info("shopStaffFacade.getStaffDetail 入参 ：【{}】", JSON.toJSONString(soaRequest));
            SoaResponse<ShopStaffVO> soaResponse = shopStaffFacade.getStaffDetail(soaRequest) ;

            SoaResponse<StaffVO> ret = new SoaResponse(soaResponse.isSuccess() ,
                soaResponse.getCode() , soaResponse.getReason()) ;
            StaffVO staffVO = new StaffVO() ;
            ret.setResult(staffVO);
            ShopStaffVO shopStaffVO = soaResponse.getResult() ;
            staffVO.setShopStaffVO(shopStaffVO);

            String identityId = shopStaffVO == null ? null : shopStaffVO.getSecurityId() ;
            if(StringUtils.isNotBlank(identityId)) {
                IdentityVO identityVO = Utils.call(()->jiccService.queryIdentityAndImg("test" , identityId ,String.valueOf(venderId))
                    , Constants.UMP_JICC_QUERY) ;
                staffVO.setIdentityVO(identityVO);
            }
            return ret ;
        }) ;
    }

    @ApiOperation(value = "服务人员保存", httpMethod = "POST", response = SoaResponse.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "shopStaffVO" , dataType="ShopStaffVO")
    })
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse getSaveOrEdit(@RequestBody ShopStaffVO shopStaffVO){

        return Utils.call(() -> {
            SoaRequest<ShopStaffVO> soaRequest = new SoaRequest<>() ;
            shopStaffVO.setVenderId(VenderIdGetter.get());
            soaRequest.setData(shopStaffVO);
            Long id = shopStaffVO.getId() ;
            if(id == null || id == 0 ) {
                return  shopStaffFacade.addStaff(soaRequest) ;
            } else {
                return  shopStaffFacade.editStaff(soaRequest) ;
            }

        }) ;
    }

    @ApiOperation(value = "服务人员删除", httpMethod = "DELETE", response = SoaResponse.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id" , paramType = "path")
    })
    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public SoaResponse deleteStaff(@PathVariable Long id ){

        return Utils.call(() -> {
            SoaRequest<ShopStaffIdVenderIdVO> soaRequest = new SoaRequest<>() ;
            ShopStaffIdVenderIdVO staffIdVenderIdVO = new ShopStaffIdVenderIdVO() ;
            staffIdVenderIdVO.setVenderId(VenderIdGetter.get());
            staffIdVenderIdVO.setId(id);
            soaRequest.setData(staffIdVenderIdVO);
            return  shopStaffFacade.deleteStaff(soaRequest) ;

        }) ;
    }


}
