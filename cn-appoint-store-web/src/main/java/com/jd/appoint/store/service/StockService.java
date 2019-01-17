package com.jd.appoint.store.service;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shopapi.ShopScheduleFacade;
import com.jd.appoint.shopapi.ShopStockFacade;
import com.jd.appoint.store.util.LoginInfoGetter;
import com.jd.appoint.store.util.Utils;
import com.jd.appoint.store.vo.SingleStockVO;
import com.jd.appoint.store.vo.UpdateStock;
import com.jd.appoint.storeapi.StoreScheduleFacade;
import com.jd.appoint.storeapi.StoreStockFacade;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    @Resource
    private StoreStockFacade storeStockFacade;
    @Resource
    private StoreScheduleFacade storeScheduleFacade;
    @Resource
    private BusinessCodeService businessCodeService;
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    public SoaResponse<ScheduleResult> getStockSchedule(ScheduleVO scheduleVO) {
        return Utils.call(() -> {
            SoaRequest<ScheduleVO> soaRequest = new SoaRequest<>();
            soaRequest.setData(scheduleVO);
            logger.info("storeScheduleFacade.searchSchedules 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeScheduleFacade.searchSchedules(soaRequest);
        });
    }

    public SoaResponse<List<String>> batchUpdateStock(StockInfoVO stockInfoVO){
        return Utils.call(() -> {

            SoaRequest<StockInfoVO> soaRequest = new SoaRequest<>() ;

            soaRequest.setData(stockInfoVO);
            logger.info("storeStockFacade.saveOrUpdateStock 入参 ：【{}】", JSON.toJSONString(soaRequest));

            return storeStockFacade.saveOrUpdateStock(soaRequest);

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
                stockInfoVO.setSkuId(-1L);
                stockInfoVO.setStartDate(singleStockVO.getDate());
                stockInfoVO.setEndDate(singleStockVO.getDate());
                stockInfoVO.setTotalQty(singleStockVO.getTotalQty());
                stockInfoVO.setVenderId(LoginInfoGetter.getVenderId());
                stockInfoVO.setStoreCode(LoginInfoGetter.getStoreId()+"");
                String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
                stockInfoVO.setBusinessCode(businessCode);
                stockInfoVO.setSkuId(updateStock.getSkuId());
                soaRequest.setData(stockInfoVO);
                logger.info("storeStockFacade.saveOrUpdateStock 入参 ：【{}】", JSON.toJSONString(soaRequest));

                SoaResponse<List<String>> soaResponse = storeStockFacade.saveOrUpdateStock(soaRequest);
                //记下不成功的记录
                if (!soaResponse.isSuccess() || (soaResponse.isSuccess() && !CollectionUtils.isEmpty(soaResponse.getResult()))) {
                    failDates.add(DateUtils.formatDate(singleStockVO.getDate(), "yyyy-MM-dd"));
                }
            }
            logger.info("零散修改库存失败的记录 ：【{}】", JSON.toJSONString(failDates));

            return new SoaResponse(failDates);
        });
    }



}
