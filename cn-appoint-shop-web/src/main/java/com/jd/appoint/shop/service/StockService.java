package com.jd.appoint.shop.service;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shop.util.Utils;
import com.jd.appoint.shop.util.VenderIdGetter;
import com.jd.appoint.shop.vo.SingleStockVO;
import com.jd.appoint.shop.vo.UpdateStock;
import com.jd.appoint.shopapi.ShopScheduleFacade;
import com.jd.appoint.shopapi.ShopStockFacade;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    @Resource
    private ShopStockFacade shopStockFacade;
    @Resource
    private ShopScheduleFacade shopScheduleFacade;
    @Resource
    private BusinessCodeService businessCodeService;
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    public SoaResponse<ScheduleResult> getStockSchedule(ScheduleVO scheduleVO) {
        return Utils.call(() -> {
            SoaRequest<ScheduleVO> soaRequest = new SoaRequest<>();
            soaRequest.setData(scheduleVO);
            logger.info("shopScheduleFacade.searchSchedules 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopScheduleFacade.searchSchedules(soaRequest);
        });
    }

    public SoaResponse<List<String>> batchUpdateStock(StockInfoVO stockInfoVO){
        return Utils.call(() -> {

            SoaRequest<StockInfoVO> soaRequest = new SoaRequest<>() ;

            soaRequest.setData(stockInfoVO);
            logger.info("shopStockFacade.saveOrUpdateStock 入参 ：【{}】", JSON.toJSONString(soaRequest));

            return shopStockFacade.saveOrUpdateStock(soaRequest);

        }) ;
    }

    public SoaResponse<List<String>> updateStock(UpdateStock updateStock){
        return Utils.call(() -> {
            //逐个更新库存
            List<SingleStockVO> singleStockVOList = updateStock.getSingleStockVOList();
            List<String> failDates = new ArrayList<>();
            for (SingleStockVO singleStockVO : singleStockVOList) {
                SoaRequest<StockInfoVO> soaRequest = new SoaRequest<>();
                StockInfoVO stockInfoVO = new StockInfoVO();
                stockInfoVO.setStartDate(singleStockVO.getDate());
                stockInfoVO.setEndDate(singleStockVO.getDate());
                stockInfoVO.setTotalQty(singleStockVO.getTotalQty());
                stockInfoVO.setVenderId(VenderIdGetter.get());
                String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
                stockInfoVO.setBusinessCode(businessCode);
                stockInfoVO.setSkuId(updateStock.getSkuId());
                soaRequest.setData(stockInfoVO);
                logger.info("shopStockFacade.saveOrUpdateStock 入参 ：【{}】", JSON.toJSONString(soaRequest));

                SoaResponse<List<String>> soaResponse = shopStockFacade.saveOrUpdateStock(soaRequest);
                //记下不成功的记录
                if (!soaResponse.isSuccess() || (soaResponse.isSuccess() && !CollectionUtils.isEmpty(soaResponse.getResult()))) {
                    //failDates.add(DateUtils.formatDate(singleStockVO.getDate(), "yyyy-MM-dd"));todo 有时区问题
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    failDates.add(format.format(singleStockVO.getDate()));
                }
            }
            logger.info("零散修改库存失败的记录 ：【{}】", JSON.toJSONString(failDates));

            return new SoaResponse(failDates);
        });
    }



}
