package com.jd.appoint.service.record.impl;


import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.rpc.AppointRecordInfo;
import com.jd.appoint.domain.rpc.query.AppointRecordQuery;
import com.jd.appoint.domain.rpc.BuriedInfo;
import com.jd.appoint.domain.sys.RecordConfig;
import com.jd.appoint.rpc.footPrint.RpcBuriedInfoService;
import com.jd.appoint.service.record.AppointRecordService;
import com.jd.appoint.shopapi.vo.AppointRecordVO;
import com.jd.fastjson.JSON;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaoxiaoqing on 2018/5/22.
 */
@Service(value = "appointRecordService")
public class AppointRecordServiceImpl implements AppointRecordService {

    private static final Logger logger = LoggerFactory.getLogger(AppointRecordServiceImpl.class);

    @Resource
    private RpcBuriedInfoService rpcBuriedInfoService;
    @Resource(name = "recordConfig")
    private RecordConfig recordConfig;

    @Override
    public List<AppointRecordVO> getAppointRecordInfo(String traceNo) {
        //参数转换
        List<AppointRecordInfo> appointRecordInfoList = null;
        List<AppointRecordVO> appointRecordVOList = new ArrayList<>();
        try {
            appointRecordInfoList = rpcBuriedInfoService.getBuriedInfoByTraceNO(traceNo,recordConfig.getToken());
            if(CollectionUtils.isEmpty(appointRecordInfoList)){
                return appointRecordVOList;
            }
            appointRecordVOList = getAppointRecordInfoByType(appointRecordInfoList,traceNo);
        }catch (Exception e){
            logger.error("获取预约记录异常，预约单ID = {},异常信息 = {}",traceNo,e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.GET_FOOT_PRINT);
            return appointRecordVOList;
        }
        return appointRecordVOList;
    }

    /**
     * 获取指定类型的预约记录
     * @param appointRecordInfoList
     * @return
     */
    private List<AppointRecordVO> getAppointRecordInfoByType(List<AppointRecordInfo> appointRecordInfoList,String traceNo){
        //转换
        List<AppointRecordVO> appointRecordVOList = new ArrayList<>();
        appointRecordInfoList.forEach(appointRecordInfo -> {
            AppointRecordVO appointRecordVO = new AppointRecordVO();
            appointRecordVO.setAppointOrderId(traceNo);
            appointRecordVO.setGenerateDate(appointRecordInfo.getGenerateDate());
            appointRecordVO.setRecordContent(appointRecordInfo.getRecordContent());
            appointRecordVOList.add(appointRecordVO);
        });
        //排序
        return appointRecordVOList.stream().sorted(Comparator.comparing(AppointRecordVO :: getGenerateDate)).collect(Collectors.toList());
    }
}
