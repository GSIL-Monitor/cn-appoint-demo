package com.jd.appoint.service.api.shop;

import com.jd.appoint.common.constants.DefaultVenderConfig;
import com.jd.appoint.service.shop.ShopWorkTimeItemService;
import com.jd.appoint.service.shop.ShopWorkTimeService;
import com.jd.appoint.shopapi.ShopWorkTimeFacade;
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
 * Created by luqiang3 on 2018/5/5.
 * SHOP端的服务时间实现类
 */
@Service("shopWorkTimeFacade")
public class ShopWorkTimeFacadeImpl implements ShopWorkTimeFacade {

    private Logger logger = LoggerFactory.getLogger(ShopWorkTimeFacadeImpl.class);

    @Autowired
    private ShopWorkTimeService shopWorkTimeService;
    @Autowired
    private ShopWorkTimeItemService shopWorkTimeItemService;

    /**
     * 保存服务时间接口
     * 该接口只接收新增操作，根据businessCode+venderId确定唯一性，如果有重复，则不新增
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "SHOP端保存服务时间", classify = ShopWorkTimeFacadeImpl.class))
    public SoaResponse saveTime(@ValideGroup(groups = ShopWorkTimeFacade.class) SoaRequest<WorkTime> soaRequest) {
        WorkTime workTime = soaRequest.getData();
        workTime.setStoreCode(DefaultVenderConfig.STORE_CODE);
        return shopWorkTimeService.saveTime(workTime);
    }

    /**
     * 查询服务时间接口
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "SHOP端查询服务时间", classify = ShopWorkTimeFacadeImpl.class))
    public SoaResponse<WorkTime> searchTime(@ValideGroup(groups = ShopWorkTimeFacade.class) SoaRequest<WorkTimeQuery> soaRequest) {
        WorkTimeQuery workTimeQuery = soaRequest.getData();
        workTimeQuery.setStoreCode(DefaultVenderConfig.STORE_CODE);
        return shopWorkTimeService.searchTime(workTimeQuery);
    }

    /**
     * 编辑服务时间接口
     *
     * @param soaRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "SHOP端编辑服务时间", classify = ShopWorkTimeFacadeImpl.class))
    public SoaResponse editTime(@ValideGroup(groups = ShopWorkTimeFacade.class) SoaRequest<WorkTime> soaRequest) {
        WorkTime workTime = soaRequest.getData();
        workTime.setStoreCode(DefaultVenderConfig.STORE_CODE);
        return shopWorkTimeService.editTime(workTime);
    }
}
