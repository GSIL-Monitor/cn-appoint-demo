package com.jd.appoint.shop.web;

import com.jd.appoint.shop.service.JICCService;
import com.jd.appoint.shop.util.Utils;
import com.jd.appoint.shop.util.VenderIdGetter;
import com.jd.appoint.shop.vo.IdentityVO;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;
import com.jd.travel.base.soa.SoaResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static com.jd.appoint.shop.util.Constants.UMP_JICC_ADD;
import static com.jd.appoint.shop.util.Constants.UMP_JICC_IMG_UPLOAD;

/**
 * Created by bjliuyong on 2018/5/30.
 */
@Controller
@RequestMapping("/api/identity")
public class IdentityController {

    @Resource
    private JICCService jiccService ;

    @ApiOperation(value = "身份证信息新增接口", httpMethod = "POST", response = IdentityVO.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "identityVO", paramType = "IdentityVO" )
    })
    @RequestMapping(value = "/addOrUpdate" , method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse<String> add(@RequestBody IdentityVO identityVO ) {

        return Utils.call(()->{
            identityVO.validate();
            String user_pin = "test" ;
            String identityId ;
            if(StringUtils.isEmpty(identityVO.getSecurityId())) {
                identityId = jiccService.add(identityVO , user_pin) ;
            } else {
                identityId = jiccService.updateIdentity(identityVO , user_pin ) ;
            }
            SoaResponse<String> soaResponse = new SoaResponse<>() ;
            soaResponse.setResult(identityId);
            return soaResponse;
        } , UMP_JICC_ADD) ;
    }


    @ApiOperation(value = "身份证证件照新增时上传接口", httpMethod = "POST", response = ShopStaffVO.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "imgNo"  ,paramType = "query" ,value = "1.正面 2.反面"),
        @ApiImplicitParam(name = "sid"  ,paramType = "query" ,value = "回话id "),
    })
    @RequestMapping(value = "/img/upload" , method = RequestMethod.POST)
    @ResponseBody
    public  SoaResponse<String> uploadImg(@RequestParam("imgNo") int imgNo  ,
        @RequestParam(value = "sid" ,required = false) String sid,
        @RequestParam("img") MultipartFile img ) {
        String user_pin = "test" ;
        return Utils.call(()->{
            validateUploadArgs(imgNo , img) ;
            String retSid = sid ;
            if(StringUtils.isBlank(retSid)) {
                retSid = UUID.randomUUID().toString().replace("-" , "") ;
            }
            int certType = JICCPapersTypeEnum.IDENTITY.getCode();
            jiccService.uploadImg(user_pin ,certType, retSid , imgNo ,img.getBytes()) ;
            SoaResponse<String> soaResponse = new SoaResponse<>() ;
            soaResponse.setResult(retSid);
            return  soaResponse;
        } ,UMP_JICC_IMG_UPLOAD) ;
    }

    @ApiOperation(value = "身份证证件照更新上传接口", httpMethod = "POST", response = ShopStaffVO.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "imgNo"  ,paramType = "query" ,value = "1.正面 2.反面"),
        @ApiImplicitParam(name = "identityId"  ,paramType = "query" ,value = "户薄Id"),
    })
    @RequestMapping(value = "/img/update" , method = RequestMethod.POST)
    @ResponseBody
    public  SoaResponse<String> updateImgById(@RequestParam("identityId") String identityId ,@RequestParam("imgNo") int imgNo ,
        @RequestParam("img") MultipartFile img ) {
        String user_pin = "test" ;
        return Utils.call(()->{
            validateUploadArgs(imgNo , img) ;
            Utils.validateArgsBlank(identityId , "identityId 不能为空!");
            String sid = UUID.randomUUID().toString().replace("-" , "") ;
            int certType = JICCPapersTypeEnum.IDENTITY.getCode();
            jiccService.uploadImg(user_pin , certType, sid , imgNo ,img.getBytes()) ;
            String rid = jiccService.updateImg(identityId ,certType, user_pin , sid );
            SoaResponse<String> soaResponse = new SoaResponse<>() ;
            soaResponse.setResult(rid);
            return  soaResponse;
        } ,UMP_JICC_IMG_UPLOAD) ;
    }

    private void validateUploadArgs(int imgNo , MultipartFile img ) {
        Utils.validate(imgNo >= 1 && imgNo <= 2 , "imgNo must be 1 or 2");
        Utils.validateNull(img , "img can not be null ");
    }

 }
