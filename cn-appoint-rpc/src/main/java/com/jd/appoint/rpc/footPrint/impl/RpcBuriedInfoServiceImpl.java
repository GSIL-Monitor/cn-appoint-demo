package com.jd.appoint.rpc.footPrint.impl;

import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.rpc.AppointRecordInfo;
import com.jd.appoint.rpc.footPrint.RpcBuriedInfoService;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import com.jd.xn.footprint.client.FootPrint;
import com.jd.xn.footprint.jsfclient.dto.FootPrintDto;
import com.jd.xn.footprint.jsfclient.service.FootPrintSearchService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by gaoxiaoqing on 2018/5/9.
 */
@Service(value = "rpcBuriedInfoService")
public class RpcBuriedInfoServiceImpl implements RpcBuriedInfoService {

    private static final Logger logger = LoggerFactory.getLogger(RpcBuriedInfoServiceImpl.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    @Resource
    private FootPrintSearchService footPrintSearchService;

    @Override
    public List<AppointRecordInfo> getBuriedInfoByTraceNO(String traceNo,String token) {
        //调用足迹
        List<FootPrintDto> footPrintDtoList = null;
        List<AppointRecordInfo> appointRecordInfoList = new ArrayList<>();
        try {
            footPrintDtoList = footPrintSearchService.search(token,traceNo);
        }catch (Exception e){
            logger.error("足迹系统接口异常! 埋点标示ID = {}",traceNo,e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.GET_FOOT_PRINT);
            return appointRecordInfoList;
        }
        if(CollectionUtils.isEmpty(footPrintDtoList)){
            logger.info("足迹系统返回预约记录为空! 埋点标示ID = {}",traceNo);
            return appointRecordInfoList;
        }
        return convertAppointOrderRecord(footPrintDtoList);
    }

    /**
     * 转换
     * @param footPrintDtoList
     * @return
     */
    private List<AppointRecordInfo> convertAppointOrderRecord(List<FootPrintDto> footPrintDtoList){
        List<AppointRecordInfo> appointOrderRecordList = new ArrayList<>();
        footPrintDtoList.forEach(footPrintDto -> {
            if(null == footPrintDto){
                return;
            }
            AppointRecordInfo appointOrderRecord = new AppointRecordInfo();
            appointOrderRecord.setGenerateDate(AppointDateUtils.getStrToDate(DATE_FORMAT,footPrintDto.getBirthday()));
            appointOrderRecord.setRecordContent(footPrintDto.getContent());
            appointOrderRecordList.add(appointOrderRecord);
        });
        return appointOrderRecordList;
    }

    @Override
    public void trace(String traceNO, String content) {
        FootPrint.trace(traceNO,content, UUID.randomUUID().toString());
    }
}

