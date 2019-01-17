package com.jd.appoint.rpc.search;

import com.jd.appoint.rpc.ex.RpcException;
import com.jd.fastjson.JSON;
import com.jd.virtual.appoint.AppointmentGwService;
import com.jd.virtual.appoint.CityGwService;
import com.jd.virtual.appoint.PackageGwService;
import com.jd.virtual.appoint.StoreService;
import com.jd.virtual.appoint.appointment.AppointmentRequest;
import com.jd.virtual.appoint.city.CityItem;
import com.jd.virtual.appoint.city.CityItemRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.service.ServiceItem;
import com.jd.virtual.appoint.service.ServiceItemRequest;
import com.jd.virtual.appoint.store.StoreItem;
import com.jd.virtual.appoint.store.StoreItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/5/24.
 */
@Service
public class RpcAppointSearchGwService {
    private Logger logger = LoggerFactory.getLogger(RpcAppointSearchGwService.class);
    //@Autowired
    private CityGwService cityGwService;
    @Autowired
    private StoreService storeService;
    //@Autowired
    private PackageGwService packageGwService;
    //@Autowired
    private AppointmentGwService appointmentGwService;
    private static final String SUCCESS_STATUS = "000";

    /**
     * 获取城市信息
     *
     * @param cityId
     * @return
     */
    public CityItem getCity(String cityId) {
        CityItemRequest request = new CityItemRequest();
        request.setCityId(cityId);
        logger.info("调用网关cityGwService.getCityItemByCityId接口请求参数：" + JSON.toJSONString(request));
        CommonResponse<CityItem> response = cityGwService.getCityItemByCityId(request);
        logger.info("调用网关cityGwService.getCityItemByCityId接口原始返回：" + JSON.toJSONString(response));
        return response.getResult();
    }

    public StoreItem getStoreItemByStoreCode(String storeCode, String contextId) {
        StoreItemRequest request = new StoreItemRequest();
        request.setStoreCode(storeCode);
        request.setContextId(contextId);
        logger.info("调用网关 storeGwService.getStoreItemByStoreCode 接口请求参数：" + JSON.toJSONString(request));
        CommonResponse<StoreItem> response = storeService.getStoreItemByStoreCode(request);
        logger.info("调用网关 storeGwService.getStoreItemByStoreCode 接口原始返回：" + JSON.toJSONString(response));
        return response.getResult();
    }


    public ServiceItem getServiceItemByPackageCode(String packageCode) {
        ServiceItemRequest request = new ServiceItemRequest();
        request.setPackageCode(packageCode);
        logger.info("调用网关 packageGwService.getServiceItemByPackageCode 接口请求参数：" + JSON.toJSONString(request));
        CommonResponse<ServiceItem> response = packageGwService.getServiceItemByPackageCode(request);
        logger.info("调用网关 storeGwService.getServiceItemByPackageCode 接口原始返回：" + JSON.toJSONString(response));
        return response.getResult();
    }

    public String submit(AppointmentRequest request) {
        logger.info("调用网关 appointmentGwService.submit 接口请求参数：" + JSON.toJSONString(request));
        CommonResponse<String> response = appointmentGwService.submit(request);
        logger.info("调用网关 appointmentGwService.submit 接口原始返回：" + JSON.toJSONString(response));
        if (response != null && SUCCESS_STATUS.equals(response.getCode())) {//调用商家接口成功
            return response.getResult();
        }
        throw new RpcException("商家接口调用失败,原始返回：" + JSON.toJSONString(response));
    }


}
