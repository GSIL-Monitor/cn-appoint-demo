package com.jd.appoint.rpc.footPrint;

import com.jd.appoint.domain.rpc.AppointRecordInfo;

import java.util.List;


/**
 * 预约记录
 * Created by gaoxiaoqing on 2018/5/9.
 */
public interface RpcBuriedInfoService {
    /**
     * 查询流转记录信息
     * @param traceNo 足迹标示ID
     * @param token
     * @return
     */
    List<AppointRecordInfo> getBuriedInfoByTraceNO(String traceNo, String token);

    /**
     * 埋点
     * @param traceNO 足迹标示ID
     * @param content
     */
    void trace(String traceNO,String content);
}
