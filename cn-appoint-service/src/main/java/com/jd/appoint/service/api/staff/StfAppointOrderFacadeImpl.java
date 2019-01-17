package com.jd.appoint.service.api.staff;

import com.jd.appoint.domain.enums.DateForm;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.PopConfigService;
import com.jd.appoint.service.order.convert.AppointOrderConvert;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.service.shop.ShopStaffService;
import com.jd.appoint.stfapi.StfAppointOrderFacade;
import com.jd.appoint.stfapi.vo.StaffAppointOrderListRequest;
import com.jd.appoint.stfapi.vo.StfAppointOrderQueryVO;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.order.Attach;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.xn.slog.LogSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "stfAppointOrderFacade")
public class StfAppointOrderFacadeImpl implements StfAppointOrderFacade {

    private static Logger logger = LoggerFactory.getLogger(StfAppointOrderFacadeImpl.class);

    @Autowired
    private AppointOrderService appointOrderService;
    @Autowired
    private PopConfigService popConifgService;
    @Autowired
    private EsOrderService esOrderService;
    @Autowired
    private ShopStaffService shopStaffService;

    /**
     * 预约单详情接口
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "获取预约单详情", classify = StfAppointOrderFacadeImpl.class))
    public SoaResponse<AppointOrderDetailVO> getAppointOrderDetail(@ValideGroup SoaRequest<StfAppointOrderQueryVO> soaRequest) {
        SoaResponse<AppointOrderDetailVO> soaResponse = new SoaResponse<>();
        StfAppointOrderQueryVO stfAppointOrderQueryVO = soaRequest.getData();
        try {

            //获取预约单详情
            AppointOrderDetailVO appointOrderDetailVO = appointOrderService.detail(stfAppointOrderQueryVO.getAppointOrderId());
            //没有查到order详情返回null
            if (appointOrderDetailVO == null) {
                return soaResponse;
            }

            //验证入参的合法性并赋值中文状态
            validate(stfAppointOrderQueryVO, appointOrderDetailVO);

            soaResponse.setResult(appointOrderDetailVO);
            return soaResponse;
        } catch (IllegalArgumentException e) {
            logger.error("获取预约单详情入参不合法，soaRequest={}, 异常：{}", LogSecurity.toJSONString(soaRequest), e.getMessage());
            return new SoaResponse(SoaError.PARAMS_EXCEPTION, e.getMessage());
        }
    }

    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "提交上传预约附件信息", classify = StfAppointOrderFacadeImpl.class))
    public SoaResponse submitAttach(@ValideGroup SoaRequest<Attach> soaRequest) {
        Attach attach = soaRequest.getData();
        //对象装换
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setAttrUrls(attach.getUrls());
        appointOrderPO.setId(attach.getAppointOrderId());
        //转换数据
        appointOrderService.submitAttach(appointOrderPO, attach.getOverwrite());
        return new SoaResponse();
    }

    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "量体师端取消接口", classify = StfAppointOrderFacadeImpl.class))
    public SoaResponse cancel(@ValideGroup(groups = StfAppointOrderFacade.class) SoaRequest<AppointCancel> soaRequest) {
        AppointCancel cancel = soaRequest.getData();
        //对象装换
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setStaffPin(cancel.getUserPin());
        appointOrderPO.setId(cancel.getAppointOrderId());
        appointOrderPO.setOperateUser(cancel.getUserPin());
        appointOrderPO.setDateForm(DateForm.STAFF);
        //转换数据
        appointOrderService.cancel(appointOrderPO);
        return new SoaResponse();
    }

    @UmpMonitor(logCollector =
    @LogCollector(description = "量体师端获取预约单列表", classify = StfAppointOrderFacadeImpl.class))
    @Override
    public SoaResponse<Page<AppointOrderDetailVO>> list(@ValideGroup SoaRequest<StaffAppointOrderListRequest> pageSoaRequest) {
        SoaResponse<Page<AppointOrderDetailVO>> result = new SoaResponse<>();
        StaffAppointOrderListRequest data = pageSoaRequest.getData();
        ShopStaffPO shopStaffPO = shopStaffService.getStaffByUserPin(data.getStaffUserPin());
        if (shopStaffPO == null) {
            throw new IllegalArgumentException(data.getStaffUserPin() + "不存在对应的技师");
        }
        Page toPage = AppointOrderConvert.convertToPage(data);
        toPage.getSearchMap().put("staffCode.EQ", shopStaffPO.getId().toString());
        Page<AppointOrderDetailVO> page = esOrderService.list(toPage, pageSoaRequest.getData().getBusinessCode());
        result.setResult(page);
        List<SystemStatusInfo> systemStatusInfos = popConifgService.queryStatusMappingList(pageSoaRequest.getData().getBusinessCode());
        AppointOrderConvert.mapChStatus(page.getList(), systemStatusInfos);
        return result;
    }

    private void validate(StfAppointOrderQueryVO stfAppointOrderQueryVO, AppointOrderDetailVO appointOrderDetailVO) {
        if (!stfAppointOrderQueryVO.getBusinessCode().equals(appointOrderDetailVO.getBusinessCode())) {
            logger.error("参数{}不合法，入参为：{}，数据库中为：{}", "businessCode", stfAppointOrderQueryVO.getBusinessCode(), appointOrderDetailVO.getBusinessCode());
            throw new IllegalArgumentException("入参业务类型不匹配");
        }
        //校验staffUserPin
        if (!appointOrderDetailVO.getStaffUserPin().equals(stfAppointOrderQueryVO.getStaffUserPin())) {
            logger.error("参数{}不合法，入参为：{}，数据库中为：{}", "staffUserPin", stfAppointOrderQueryVO.getStaffUserPin(), appointOrderDetailVO.getStaffUserPin());
            throw new IllegalArgumentException("入参staffUserPin不匹配");
        }
    }

}
