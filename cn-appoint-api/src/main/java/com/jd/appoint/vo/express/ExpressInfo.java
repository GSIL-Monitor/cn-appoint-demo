package com.jd.appoint.vo.express;

import com.jd.appoint.shopapi.vo.TraceInfo;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyuan on 6/27/18.
 */
public class ExpressInfo {

    /**
     * 快递公司名称
     */
    private String expressCompanyName;

    /**
     * 快递单号
     */
    private String shipId;

    /**
     * 物流信息详情
     */
    private List<TraceInfo> traceInfoList;


    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public List<TraceInfo> getTraceInfoList() {
        return traceInfoList;
    }

    public void setTraceInfoList(List<TraceInfo> traceInfoList) {
        this.traceInfoList = traceInfoList;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    @Override
    public String toString() {
        return "ExpressInfo{" +
                "expressCompanyName='" + expressCompanyName + '\'' +
                ", shipId='" + shipId + '\'' +
                ", traceInfoList=" + traceInfoList +
                '}';
    }
}
