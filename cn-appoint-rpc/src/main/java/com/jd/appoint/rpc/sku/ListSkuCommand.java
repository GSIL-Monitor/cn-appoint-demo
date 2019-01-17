package com.jd.appoint.rpc.sku;

import com.jd.vsc.soa.domain.RequestWrap;
import com.jd.vsc.soa.domain.ResponseWrap;
import com.jd.vsc.soa.domain.bizconfig.SkuResult;
import com.jd.vsc.soa.service.VscBizConfigSoaService;
import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by yangyuan on 8/1/18.
 */
public class ListSkuCommand extends HystrixCommand<List<SkuResult>> {

    private static Logger log = LoggerFactory.getLogger(ListSkuCommand.class);

    private VscBizConfigSoaService vscBizConfigSoaService;

    private RequestWrap requestWrap;

    public ListSkuCommand(RequestWrap requestWrap, VscBizConfigSoaService vscBizConfigSoaService) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("skuService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("listSku"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("skuServicePool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        .withCircuitBreakerEnabled(true)));
        this.requestWrap = requestWrap;
        this.vscBizConfigSoaService = vscBizConfigSoaService;
    }

    @Override
    protected List<SkuResult> run() throws Exception {
        ResponseWrap<List<SkuResult>> responseWrap = vscBizConfigSoaService.getSkuListByMerchantCode(requestWrap);
        if (responseWrap == null || 200 != responseWrap.getCode()) {
            log.error("vsc rpc logic error.");
            return Collections.emptyList();
        }
        return responseWrap.getData() == null ? Collections.emptyList() : responseWrap.getData();
    }

    @Override
    protected List<SkuResult> getFallback() {
        log.error("error happened.", getFailedExecutionException());
        return Collections.emptyList();
    }

}
