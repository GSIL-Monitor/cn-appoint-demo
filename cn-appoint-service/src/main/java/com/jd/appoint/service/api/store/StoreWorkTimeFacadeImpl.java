package com.jd.appoint.service.api.store;

import com.jd.appoint.service.shop.ShopWorkTimeService;
import com.jd.appoint.storeapi.StoreWorkTimeFacade;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
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

/**
 * Created by luqiang3 on 2018/7/2.
 */
@Service("storeWorkTimeFacade")
public class StoreWorkTimeFacadeImpl implements StoreWorkTimeFacade {

    private Logger logger = LoggerFactory.getLogger(StoreWorkTimeFacadeImpl.class);

    @Autowired
    private ShopWorkTimeService shopWorkTimeService;

    /**
     * 保存服务时间接口
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "STORE端保存服务时间", classify = StoreWorkTimeFacadeImpl.class))
    public SoaResponse saveTime(@ValideGroup(groups = StoreWorkTimeFacade.class) SoaRequest<WorkTime> soaRequest) {
        return shopWorkTimeService.saveTime(soaRequest.getData());
    }

    /**
     * 查询服务时间接口
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "STORE端查询服务时间", classify = StoreWorkTimeFacadeImpl.class))
    public SoaResponse<WorkTime> searchTime(@ValideGroup(groups = StoreWorkTimeFacade.class) SoaRequest<WorkTimeQuery> soaRequest) {
        return shopWorkTimeService.searchTime(soaRequest.getData());
    }

    /**
     * 编辑服务时间接口
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "STORE端编辑服务时间", classify = StoreWorkTimeFacadeImpl.class))
    public SoaResponse editTime(@ValideGroup(groups = StoreWorkTimeFacade.class) SoaRequest<WorkTime> soaRequest) {
        return shopWorkTimeService.editTime(soaRequest.getData());
    }
}
