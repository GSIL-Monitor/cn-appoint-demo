package com.jd.appoint.service.record.impl;
import com.alibaba.fastjson.JSON;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.BuriedRecordEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.rpc.query.MappingStatusQuery;
import com.jd.appoint.rpc.footPrint.RpcBuriedInfoService;
import com.jd.appoint.service.order.PopConfigService;
import com.jd.appoint.service.record.BuriedPointService;
import com.jd.appoint.service.util.BuriedPointUtil;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoxiaoqing on 2018/5/29.
 */
@Service
public class BuriedPointServiceImpl implements BuriedPointService{

    private static final Logger logger = LoggerFactory.getLogger(BuriedPointServiceImpl.class);
    @Resource
    private RpcBuriedInfoService rpcBuriedInfoService;
    @Resource
    private PopConfigService popConifgService;

    /**
     * 流程变更埋点
     * @param traceNO
     * @param processType
     * @param param
     */
    public void processBuried(String traceNO, int processType,Map<String,String> param) {
        String content = BuriedPointUtil.getBurryContent(processType,param);
        logger.info("预约单号={}，content={}",traceNO,content);
        traceNO = traceNO + BuriedRecordEnum.APPOINT_PROCESS.getValue();
        rpcBuriedInfoService.trace(traceNO,content);
    }

    /**
     * 状态变更埋点
     * @param appointOrderPO
     * @param processType
     */
    public void statusChangeBuried(AppointOrderPO appointOrderPO, int processType) {
        if(null == appointOrderPO.getServerType() || null == appointOrderPO.getBusinessCode()
                || null == appointOrderPO.getAppointStatus() || null == appointOrderPO.getOperateUser()){
            logger.error("状态埋点参数异常，入参 = {} ",JSON.toJSONString(appointOrderPO));
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_BURIED_POINT);
            return;
        }
        try {
            //获取指定业务线的状态名称
            MappingStatusQuery mappingStatusQuery = new MappingStatusQuery();
            mappingStatusQuery.setServerTypeEnum(ServerTypeEnum.getFromCode(appointOrderPO.getServerType()));
            mappingStatusQuery.setBusinessCode(appointOrderPO.getBusinessCode());
            mappingStatusQuery.setAppointStatusEnum(appointOrderPO.getAppointStatus());
            String statusName = popConifgService.querySystemStatus(mappingStatusQuery);
            //获取埋点内容
            Map<String,String> param = new HashMap<>();
            param.put("statusName",statusName);
            param.put("operateUser",appointOrderPO.getOperateUser());
            String content = BuriedPointUtil.getBurryContent(processType,param);
            String traceNO = appointOrderPO.getId().toString() + BuriedRecordEnum.APPOINT_PROCESS.getValue();
            logger.info("预约单号={}，content={}",traceNO,content);
            //埋点
            rpcBuriedInfoService.trace(traceNO,content);
        }catch (Exception e){
            logger.error("状态埋点异常",e);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_BURIED_POINT);
        }

    }
}
