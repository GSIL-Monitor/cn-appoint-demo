package com.jd.appoint.service.api.shop.convert;

import com.google.common.base.Converter;
import com.jd.appoint.rpc.RpcConfig;
import com.jd.appoint.shopapi.vo.SyncSkuRequest;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.vsc.soa.domain.RequestWrap;

/**
 * Created by yangyuan on 6/29/18.
 */
public class VscRequestWrapConverter extends Converter<SoaRequest<SyncSkuRequest>, RequestWrap>{

    @Override
    protected RequestWrap doForward(SoaRequest<SyncSkuRequest> requestSoaRequest) {
        RequestWrap requestWrap = new RequestWrap();
        requestWrap.setTrackerId(requestSoaRequest.getTraceId());
        requestWrap.setAppCode(RpcConfig.VSC_APPCODE);
        requestWrap.setToken(RpcConfig.VSC_TOKEN);
        requestWrap.setSource(RpcConfig.VSC_SOURCE);
        requestWrap.setBusinessType(requestSoaRequest.getData().getBusinessType());
        requestWrap.setMerchantCode(String.valueOf(requestSoaRequest.getData().getVenderId()));
        requestWrap.setClientIp(requestSoaRequest.getData().getClientIp());
        requestWrap.setClientPort("" + requestSoaRequest.getData().getPort());
        return requestWrap;
    }

    @Override
    protected SoaRequest<SyncSkuRequest> doBackward(RequestWrap requestWrap) {
        return null;
    }
}
