package com.jd.appoint.service.api.shop;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.common.enums.ScheduleModelEnum;
import com.jd.appoint.domain.stock.StockInfoPO;
import com.jd.appoint.service.api.convert.StockConvert;
import com.jd.appoint.service.stock.StockInfoService;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.shopapi.ShopScheduleFacade;
import com.jd.appoint.vo.schedule.ScheduleModel;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.common.util.StringUtils;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by luqiang3 on 2018/6/15.
 */
@Service("shopScheduleFacade")
public class ShopScheduleFacadeImpl implements ShopScheduleFacade {

    private static Logger logger = LoggerFactory.getLogger(ShopScheduleFacadeImpl.class);

    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private VenderConfigService venderConfigService;

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "SHOP端查询产能日历", classify = ShopScheduleFacadeImpl.class))
    public SoaResponse<ScheduleResult> searchSchedules(@ValideGroup(groups = {ShopScheduleFacade.class}) SoaRequest<ScheduleVO> soaRequest) {
        ScheduleVO scheduleVO = soaRequest.getData();
        scheduleVO.setStoreCode(DefaultVenderConfig.STORE_CODE);
        ScheduleModelEnum scheduleModel = venderConfigService.getScheduleModel(soaRequest.getData().getBusinessCode());
        if(ScheduleModelEnum.VENDER == scheduleModel){
            scheduleVO.setSkuId(DefaultVenderConfig.SKU_ID);
        }
        if(scheduleVO.getStartDate().before(new Date())){
            scheduleVO.setStartDate(new Date());
        }
        List<StockInfoPO> stockInfoPOs = stockInfoService.querySelective(StockConvert.scheduleVO2StockInfoQuery(scheduleVO));
        ScheduleResult scheduleResult = StockConvert.stockInfoPO2ScheduleResult(stockInfoPOs);
        return new SoaResponse<>(scheduleResult);
    }

    /**
     * 获取产能日历模式
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "SHOP端查询产能日历模式", classify = ShopScheduleFacadeImpl.class))
    public SoaResponse<ScheduleModel> searchScheduleModel(SoaRequest<String> soaRequest) {
        if(StringUtils.isBlank(soaRequest.getData())){
            logger.info("SHOP端查询产能日历模式入参错误：soaRequest={}", JSON.toJSONString(soaRequest));
            return new SoaResponse<ScheduleModel>(SoaError.DATA_EXCEPTION);
        }
        ScheduleModelEnum scheduleModel = venderConfigService.getScheduleModel(soaRequest.getData());
        Integer model = ScheduleModelEnum.SKU == scheduleModel ? 2 : 1;
        ScheduleModel result = new ScheduleModel();
        result.setModel(model);
        return new SoaResponse<>(result);
    }
}
