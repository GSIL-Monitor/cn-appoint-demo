package com.jd.appoint.service.api.staff;

import com.jd.appoint.stfapi.StaffFacade;
import com.jd.appoint.common.enums.SoaCodeEnum;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.service.shop.ShopStaffService;
import com.jd.appoint.service.api.shop.convert.StaffConvert;
import com.jd.appoint.vo.staff.ShopStaffVO;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.user.sdk.export.UserInfoExportService;
import com.jd.user.sdk.export.domain.UserBaseInfoVo;
import com.jd.user.sdk.export.exception.UserSdkExportException;
import com.jd.xn.slog.LogSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by lishuaiwei on 2018/5/7.
 * SHOP端的服务人员管理服务实现类
 */
@Service("staffFacade")
public class StaffFacadeImpl implements StaffFacade {

    private Logger logger = LoggerFactory.getLogger(StaffFacadeImpl.class);

    @Autowired
    private ShopStaffService shopStaffService;
    @Autowired
    private UserInfoExportService userInfoExportService;

    /**
     * 根据userPin查询服务人员详情接口
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "根据userPin查询服务人员详情接口", classify = StaffFacadeImpl.class))
    public SoaResponse<ShopStaffVO> getStaffDetailByUserPin(SoaRequest<String> soaRequest) {
        SoaResponse<ShopStaffVO> soaResponse = new SoaResponse<>();
        String userPin = soaRequest.getData();
        if (userPin == null) {
            logger.error("根据userPin查询服务人员详情userPin为null，soaRequest={}", LogSecurity.toJSONString(soaRequest));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "根据userPin查询服务人员详情userPin为null");
        }

        ShopStaffPO shopStaffPO = new ShopStaffPO();
        shopStaffPO.setUserPin(userPin);

        shopStaffPO = shopStaffService.getStaffDetail(shopStaffPO);
        if (shopStaffPO == null) {
            return soaResponse;
        }

        ShopStaffVO shopStaffVO = StaffConvert.toStaffVO(shopStaffPO);
        soaResponse.setResult(shopStaffVO);
        return soaResponse;

    }

    /**
     * 服务人员登录接口
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "服务人员登录接口", classify = StaffFacadeImpl.class))
    public SoaResponse<String> staffLogin(SoaRequest<String> soaRequest) {
        String userPin = soaRequest.getData();
        if (StringUtils.isEmpty(userPin)) {
            logger.error("服务人员登录接口userPin为空");
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, "userPin不能为空");
        }
        //判断userPin是否已经存在，存在返回true
        String staffName = getStaffNameByUserPin(userPin);
        if (staffName != null) {
            return new SoaResponse(staffName);
        }
        //不存在，主站接口查询手机号
        try {
            UserBaseInfoVo userBaseInfo = userInfoExportService.getUserBaseInfoByPin(userPin, 1);
            if(userBaseInfo == null){
                logger.error("查询京东主站，根据userPin没有查到信息");
                return new SoaResponse(SoaCodeEnum.USERPIN_NOT_EXIST);
            }
            String mobile = userBaseInfo.getMobile();
            if(StringUtils.isEmpty(mobile)){
                logger.error("查询京东主站，根据userPin没有查到手机号");
                return new SoaResponse(SoaCodeEnum.MOBILE_NOT_EXIST);
            }
            //根据手机号查询员工信息
            ShopStaffPO shopStaffPO = new ShopStaffPO();
            shopStaffPO.setServerPhone(mobile);
            shopStaffPO = shopStaffService.getStaffDetail(shopStaffPO);
            if (shopStaffPO == null) {
                logger.error("服务人员登录，根据userPin查询到的手机号不存在，userPin={}，mobile={}", userPin, mobile);
                return new SoaResponse(SoaCodeEnum.MOBILE_NOT_EXIST);
            }
            //判断userPin是否为null，如果为null绑定userPin,登录成功
            if (StringUtils.isEmpty(shopStaffPO.getUserPin())) {
                //绑定userPin
                shopStaffPO.setUserPin(userPin);
                shopStaffService.bindUserPin(shopStaffPO);
                return new SoaResponse(shopStaffPO.getServerName());
            }

            //如果不为null则登录失败，返回userPin不一致
            return new SoaResponse(SoaCodeEnum.USERPIN_MISMATCH);
        } catch (UserSdkExportException e) {
            logger.error("调用主站根据userPin查询手机号出现异常，userPin=" + userPin);
            return new SoaResponse(false, e.getExceptionCode(), e.getExceptionMessage());
        }
    }

    /**
     * 查询userPin是否存在,存在返回staffName
     */
    public String getStaffNameByUserPin(String userPin) {
        ShopStaffPO shopStaffPO = new ShopStaffPO();
        shopStaffPO.setUserPin(userPin);
        shopStaffPO = shopStaffService.getStaffDetail(shopStaffPO);
        return shopStaffPO != null?shopStaffPO.getServerName():null;

    }

}
