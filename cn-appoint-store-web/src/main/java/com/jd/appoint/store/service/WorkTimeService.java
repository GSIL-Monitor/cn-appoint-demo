package com.jd.appoint.store.service;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shopapi.ShopWorkTimeFacade;
import com.jd.appoint.store.util.LoginInfoGetter;
import com.jd.appoint.store.util.Utils;
import com.jd.appoint.storeapi.StoreWorkTimeFacade;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

@Service
public class WorkTimeService {
    @Resource
    private StoreWorkTimeFacade storeWorkTimeFacade ;
    @Resource
    private BusinessCodeService businessCodeService;

    private static final Logger logger = LoggerFactory.getLogger(WorkTimeService.class);


    /**
     * 一期获取服务时间
     * @return
     */
    public SoaResponse<WorkTime> getWorkTime(WorkTimeQuery workTimeQuery) {
        return Utils.call(() -> {
            SoaRequest<WorkTimeQuery> soaRequest = new SoaRequest<>() ;
            soaRequest.setData(workTimeQuery);
            workTimeQuery.setVenderId(LoginInfoGetter.getVenderId());
            workTimeQuery.setStoreCode(LoginInfoGetter.getStoreId()+"");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            workTimeQuery.setBusinessCode(businessCode);
            logger.info("storeWorkTimeFacade.searchTime 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return  storeWorkTimeFacade.searchTime(soaRequest);
        }) ;

    }

    public SoaResponse saveOrUpdate(@RequestBody WorkTime workTime) {
        return Utils.call(() -> {
            SoaRequest<WorkTime> soaRequest = new SoaRequest<>() ;
            workTime.setVenderId(LoginInfoGetter.getVenderId());
            workTime.setStoreCode(LoginInfoGetter.getStoreId()+"");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            workTime.setBusinessCode(businessCode);
            if(workTime.getTimeShowType() == null){
                workTime.setTimeShowType(3);
            }
            soaRequest.setData(workTime);
            logger.info("storeWorkTimeFacade.saveOrUpdateTime 入参 ：【{}】", JSON.toJSONString(soaRequest));

            if(workTime.getId() == null ) {
                return storeWorkTimeFacade.saveTime(soaRequest);
            }
            return storeWorkTimeFacade.editTime(soaRequest);
        }) ;

    }
}
