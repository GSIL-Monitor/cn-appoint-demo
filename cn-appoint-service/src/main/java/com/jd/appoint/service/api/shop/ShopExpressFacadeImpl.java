package com.jd.appoint.service.api.shop;

import com.google.common.base.Preconditions;
import com.jd.appoint.rpc.ExpressService;
import com.jd.appoint.service.api.shop.convert.ExpressCompanyConverter;
import com.jd.appoint.service.api.shop.convert.ExpressInfoConverter;
import com.jd.appoint.shopapi.ShopExpressFacade;
import com.jd.appoint.vo.express.ExpressCompanyVO;
import com.jd.appoint.vo.express.ExpressInfo;
import com.jd.appoint.vo.express.ExpressSubscribeRequest;
import com.jd.etms.third.service.dto.WayBillDto;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by yangyuan on 6/27/18.
 */
@Service(value = "shopExpressFacadeImpl")
public class ShopExpressFacadeImpl implements ShopExpressFacade {

    private static final Logger log = LoggerFactory.getLogger(ShopExpressFacadeImpl.class);

    @Resource(name = "expressService")
    private ExpressService expressService;

    @Override
    public SoaResponse<List<ExpressCompanyVO>> getAllExpressCompany() {
        return new SoaResponse<>(expressService
                .getAllExpressList()
                .stream()
                .map(t -> new ExpressCompanyConverter().convert(t))
                .filter(expressCompanyVO -> {
                    if (expressCompanyVO.getThirdId() <= 0) {
                        return false;
                    }
                    return true;
                }).collect(Collectors.toList()));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "查询快递信息", classify = ShopExpressFacadeImpl.class))
    @Override
    public SoaResponse<ExpressInfo> getExpressInfo(SoaRequest<Long> orderId) {
        Preconditions.checkNotNull(orderId);
        try {
            Optional<WayBillDto> optional = Optional.ofNullable(expressService.getExpressInfo(orderId.getData()));
            return new SoaResponse<>(optional.map(t -> new ExpressInfoConverter().convert(t)).orElse(null));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.error("getExpressInfo error.", e);
            return new SoaResponse<>(SoaError.SERVER_EXCEPTION, "error");
        }
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "订阅快递信息", classify = ShopExpressFacadeImpl.class))
    @Override
    public SoaResponse<Boolean> routeSubscribe(SoaRequest<ExpressSubscribeRequest> request) {
        try {
            return new SoaResponse<>(expressService.routeSubscribe(request.getData().getOrderId(),
                    request.getData().getShipId(),
                    request.getData().getThirdId()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.error("route subscribe failure.", e);
        }
        return new SoaResponse<>(SoaError.SERVER_EXCEPTION, "server error.");
    }

}
