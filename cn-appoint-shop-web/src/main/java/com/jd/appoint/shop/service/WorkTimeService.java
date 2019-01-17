package com.jd.appoint.shop.service;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shop.util.Constants;
import com.jd.appoint.shop.util.Utils;
import com.jd.appoint.shop.util.VenderIdGetter;
import com.jd.appoint.shopapi.ShopWorkTimeFacade;
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
    private ShopWorkTimeFacade shopWorkTimeFacade ;
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
            workTimeQuery.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            workTimeQuery.setBusinessCode(businessCode);
            logger.info("shopWorkTimeFacade.searchTime 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return  shopWorkTimeFacade.searchTime(soaRequest);
        }) ;

    }

    public SoaResponse saveOrUpdate(@RequestBody WorkTime workTime) {
        return Utils.call(() -> {
            SoaRequest<WorkTime> soaRequest = new SoaRequest<>() ;
            workTime.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            workTime.setBusinessCode(businessCode);
            if(workTime.getTimeShowType() == null){
                workTime.setTimeShowType(3);
            }
            soaRequest.setData(workTime);
            logger.info("shopWorkTimeFacade.saveOrUpdateTime 入参 ：【{}】", JSON.toJSONString(soaRequest));

            if(workTime.getId() == null ) {
                return shopWorkTimeFacade.saveTime(soaRequest);
            }
            return shopWorkTimeFacade.editTime(soaRequest);
        }) ;

    }
}
