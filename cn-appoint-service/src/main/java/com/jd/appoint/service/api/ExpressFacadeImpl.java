package com.jd.appoint.service.api;

import com.jd.appoint.api.ExpressFacade;
import com.jd.appoint.rpc.ExpressService;
import com.jd.appoint.service.api.shop.convert.ExpressInfoConverter;
import com.jd.appoint.vo.express.ExpressInfo;
import com.jd.etms.third.service.dto.WayBillDto;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Created by luqiang3 on 2018/6/27.
 */
@Service("expressFacade")
public class ExpressFacadeImpl implements ExpressFacade{

    private static final Logger logger = LoggerFactory.getLogger(ExpressFacadeImpl.class);

    @Autowired
    private ExpressService expressService;

    /**
     * 获取快递信息
     *
     * @param soaRequest
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "API查询快递信息", classify = ExpressFacadeImpl.class))
    @Override
    public SoaResponse<ExpressInfo> getExpressInfo(SoaRequest<Long> soaRequest) {
        Long appointOrderId = soaRequest.getData();
        if(null == appointOrderId){
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "预约单号不能为空");
        }
        try {
            Optional<WayBillDto> optional = Optional.ofNullable(expressService.getExpressInfo(appointOrderId));
            return new SoaResponse<>(optional.map(t -> new ExpressInfoConverter().convert(t)).orElse(new ExpressInfo()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("getExpressInfo error.", e);
            return new SoaResponse<>(SoaError.SERVER_EXCEPTION,"error");
        }
    }

}
