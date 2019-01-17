package com.jd.appoint.service.api.shop;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.common.enums.ScheduleModelEnum;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.service.stock.StockInfoService;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.shopapi.ShopStockFacade;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiang3 on 2018/6/14.
 */
@Service("shopStockFacade")
public class ShopStockFacadeImpl implements ShopStockFacade {

    private static final Logger logger = LoggerFactory.getLogger(ShopStockFacadeImpl.class);

    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private VenderConfigService venderConfigService;

    /**
     * 保存或更新库存信息
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "SHOP端保存或更新库存", classify = ShopStockFacadeImpl.class))
    public SoaResponse<List<String>> saveOrUpdateStock(@ValideGroup(groups = {ShopStockFacade.class}) SoaRequest<StockInfoVO> soaRequest) {
        StockInfoVO vo = soaRequest.getData();
        if(!validateAndInitParams(vo)){
            logger.error("SHOP端保存或更新库存参数校验失败，StockInfoVO={}", vo.toString());
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        List<String> failData = stockInfoService.saveOrUpdateStock(vo);
        if(CollectionUtils.isNotEmpty(failData)){
            logger.info("SHOP端保存或更新库存失败数据：failData={}", JSON.toJSONString(failData));
        }
        return new SoaResponse(failData);
    }

    /**
     * 校验和初始化参数
     * @param vo
     * @return
     */
    private Boolean validateAndInitParams(StockInfoVO vo){
        vo.setStoreCode(DefaultVenderConfig.STORE_CODE);
        ScheduleModelEnum scheduleModel = venderConfigService.getScheduleModel(vo.getBusinessCode());
        if(ScheduleModelEnum.SKU == scheduleModel){
            if(null == vo.getSkuId()){
                logger.error("产能日历模式为SKU维度，skuId is null，StockInfoVO={}", vo.toString());
                return Boolean.FALSE;
            }
        }else {
            vo.setSkuId(DefaultVenderConfig.SKU_ID);
        }
        if(AppointDateUtils.daysBetweenTwoDate(vo.getStartDate(), vo.getEndDate()) < 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
