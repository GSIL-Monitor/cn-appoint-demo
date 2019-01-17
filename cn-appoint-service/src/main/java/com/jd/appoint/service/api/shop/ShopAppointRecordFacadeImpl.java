package com.jd.appoint.service.api.shop;

import com.jd.appoint.domain.enums.BuriedRecordEnum;
import com.jd.appoint.domain.enums.ProcessTypeEnum;
import com.jd.appoint.service.record.AppointRecordService;
import com.jd.appoint.service.record.BuriedPointService;
import com.jd.appoint.shopapi.ShopAppointRecordFacade;
import com.jd.appoint.shopapi.vo.AppointRecordQueryVO;
import com.jd.appoint.shopapi.vo.AppointRecordVO;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取预约足迹信息
 * Created by gaoxiaoqing on 2018/5/16.
 */
@Service(value = "shopAppointRecordFacade")
public class ShopAppointRecordFacadeImpl implements ShopAppointRecordFacade {

    private static final Logger logger = LoggerFactory.getLogger(ShopAppointRecordFacadeImpl.class);
    @Resource
    private AppointRecordService appointRecordService;
    @Resource
    private BuriedPointService buriedPointService;

    @UmpMonitor(serverType = ServerEnum.SOA,logCollector =
    @LogCollector(description = "获取预约记录",classify = ShopAppointRecordFacadeImpl.class))
    @Override
    public SoaResponse<List<AppointRecordVO>> getAppointRecordInfo(@ValideGroup SoaRequest<AppointRecordQueryVO> soaRequest) {
        if(null == soaRequest || null == soaRequest.getData()){
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION,"获取预约记录参数异常！");
        }
        String traceNO = soaRequest.getData().getAppointOrderId() + BuriedRecordEnum.APPOINT_PROCESS.getValue();
        List<AppointRecordVO> appointRecordVOList = appointRecordService.getAppointRecordInfo(traceNO);
        return new SoaResponse(appointRecordVOList);
    }

    @Override
    public void insertBuriedTest(String traceNO,String content) {
        buriedPointService.processBuried(traceNO, ProcessTypeEnum.CHANGE_APPOINT_STATUS.getCode(),null);
        logger.info("埋点测试，埋点ID = {}，埋点内容 = {}",traceNO,content);
    }
}
