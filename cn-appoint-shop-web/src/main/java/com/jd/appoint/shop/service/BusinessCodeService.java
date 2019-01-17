package com.jd.appoint.shop.service;

import com.jd.appoint.api.BusinessAndVenderMapFacade;
import com.jd.appoint.api.vo.BusinessVenderMapVO;
import com.jd.appoint.shop.util.Constants;
import com.jd.appoint.shop.util.Utils;
import com.jd.appoint.shop.util.VenderIdGetter;
import com.jd.appoint.shop.vo.SingleStockVO;
import com.jd.appoint.shop.vo.UpdateStock;
import com.jd.appoint.shopapi.ShopScheduleFacade;
import com.jd.appoint.shopapi.ShopStockFacade;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.jim.cli.Cluster;
import com.jd.jim.cli.redis.jedis.Client;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessCodeService {



    @Resource
    private Cluster jimClient;

    @Resource
    private BusinessAndVenderMapFacade businessAndVenderMapFacade;

    public String getBusinessCodeByVenderId(Long venderId) {
        String businessCode = "";
        String key = "business_code_"+venderId+"_V1";
        businessCode = jimClient.get(key);
        if(!StringUtils.isEmpty(businessCode)){
            return businessCode;
        }

        BusinessVenderMapVO result = businessAndVenderMapFacade.queryByVenderId(new SoaRequest<>(venderId)).getResult();
        if(result != null){
            businessCode = result.getBusinessCode();
            jimClient.set(key,businessCode);
            return businessCode;
        }

        //出错了返回服装code
        return Constants.FZ_BIZ_CODE;

    }

}
