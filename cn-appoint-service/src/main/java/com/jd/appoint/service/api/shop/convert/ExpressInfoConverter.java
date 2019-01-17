package com.jd.appoint.service.api.shop.convert;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.jd.appoint.vo.express.ExpressInfo;
import com.jd.appoint.shopapi.vo.TraceInfo;
import com.jd.etms.third.service.dto.TraceDetailDto;
import com.jd.etms.third.service.dto.WayBillDto;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yangyuan on 6/27/18.
 */
public class ExpressInfoConverter  extends Converter<WayBillDto, ExpressInfo> {

    @Override
    protected ExpressInfo doForward(WayBillDto wayBillDto) {
        ExpressInfo expressInfo = new ExpressInfo();
        expressInfo.setExpressCompanyName(wayBillDto.getThirdName());
        expressInfo.setShipId(wayBillDto.getShipId());
        if (CollectionUtils.isNotEmpty(wayBillDto.getListTrace())) {
            expressInfo.setTraceInfoList(wayBillDto
                    .getListTrace()
                    .stream()
                    .map(t -> trans2TraceIno(t))
                    .collect(Collectors.toList()));
            expressInfo.getTraceInfoList().sort((TraceInfo t1, TraceInfo t2) -> t2.getTraceDate().compareTo(t1.getTraceDate()));
        }
        return expressInfo;
    }
    
    private TraceInfo trans2TraceIno(TraceDetailDto traceDetailDto) {
        TraceInfo traceInfo = new TraceInfo();
        traceInfo.setTraceDate(traceDetailDto.getProcessDate());
        traceInfo.setInfo(traceDetailDto.getProcessInfo());
        return traceInfo;
    }

    @Override
    protected WayBillDto doBackward(ExpressInfo expressInfo) {
        throw new RuntimeException("not implement.");
    }
}
