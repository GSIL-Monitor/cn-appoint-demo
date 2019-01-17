package com.jd.appoint.service.order.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Preconditions;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.rpc.AppointServerInfo;
import com.jd.appoint.domain.rpc.AppointStatusInfo;
import com.jd.appoint.domain.rpc.ServerTypeInfo;
import com.jd.appoint.domain.rpc.query.MappingStatusQuery;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.appoint.service.order.PopConfigService;
import com.jd.appoint.service.util.PopConfigProxy;
import com.jd.appoint.vo.order.OrderFieldVO;
import com.jd.common.util.StringUtils;
import com.jd.fastjson.JSON;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaoxiaoqing on 2018/5/7.
 */
@Service
public class PopConfigServiceImpl implements PopConfigService {

    private static final Logger logger = LoggerFactory.getLogger(PopConfigServiceImpl.class);

    private static final String APPOINT_STATUS_MAPPING = "appoint_status_mapping_";

    private static final String SERVER_TYPE_MAPPING = "server_type_mapping_";

    private static final String EXPORT_ORDER_SET = "export_order_set_";

    /**
     * 获取指定业务线所有映射
     *
     * @param businessCode
     * @return
     */
    @Override
    public List<SystemStatusInfo> queryStatusMappingList(String businessCode) {
        logger.info("获取指定业务线所有映射,businessCode = {}",businessCode);
        Preconditions.checkNotNull(businessCode);
        //获取状态信息
        List<SystemStatusInfo> systemStatusInfos =
                queryStatusConfig(generateKey(APPOINT_STATUS_MAPPING,businessCode));
        return systemStatusInfos;
    }

    /**
     * 获取相应状态的映射
     *
     * @param mappingStatusQuery
     * @return
     */
    @Override
    public String querySystemStatus(MappingStatusQuery mappingStatusQuery) {
        logger.info("获取相应状态的映射,入参 = {}", JSON.toJSONString(mappingStatusQuery));
        if (null == mappingStatusQuery || null == mappingStatusQuery.getBusinessCode()) {
            logger.error("获取状态映射为入参异常");
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_STATUS_MAPPING);
            return null;
        }
        List<SystemStatusInfo> systemStatusInfos =
                queryStatusConfig(generateKey(APPOINT_STATUS_MAPPING,mappingStatusQuery.getBusinessCode()));
        if(CollectionUtils.isEmpty(systemStatusInfos)){
            logger.error("统一配置映射状态为空，业务线={}", mappingStatusQuery.getBusinessCode());
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_STATUS_MAPPING);
            return null;
        }
        //获取指定类型状态名称
        String chStatus = getChStatus(mappingStatusQuery.getServerTypeEnum(), mappingStatusQuery.getAppointStatusEnum(), systemStatusInfos);
        return chStatus;
    }

    @Override
    public String getServerTypeName(String businessCode, int serverType) {
        logger.info("获取相应状态的映射,业务Code = {},服务类型 = {}", businessCode, serverType);
        Preconditions.checkNotNull(businessCode);
        ServerTypeInfo serverTypeInfo = getConfigServerInfo(businessCode);
        if(null == serverTypeInfo){
            return null;
        }
        if(ServerTypeEnum.DAODIAN.getIntValue() == serverType){
            return serverTypeInfo.getToShopName();
        }else {
            return serverTypeInfo.getToHomeName();
        }

    }

    @Override
    public ServerTypeInfo getServerInfo(String businessCode) {
        logger.info("获取业务服务信息,businessCode = {}",businessCode);
        Preconditions.checkNotNull(businessCode);
        return getConfigServerInfo(businessCode);
    }

    @Override
    public List<OrderFieldVO> getExportOrderSet(String businessCode) {
        PopConfigProxy<String> popConfigProxy = new PopConfigProxy<>(new String());
        String result = popConfigProxy.getConfig(EXPORT_ORDER_SET + businessCode);
        if(StringUtils.isEmpty(result)){
            logger.error("未配置列表导出信息");
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_EXPORT_SETTING);
            return null;
        }
        List<OrderFieldVO> orderFieldVOS = JSONArray.parseArray(result,OrderFieldVO.class);
        return orderFieldVOS;
    }

    /**
     * 获取预约服务信息
     * @param businessCode
     * @return
     */
    private ServerTypeInfo getConfigServerInfo(String businessCode){
        PopConfigProxy<AppointServerInfo> popConfigProxy = new PopConfigProxy<>(new AppointServerInfo());
        AppointServerInfo appointServerInfo = popConfigProxy.getConfig(generateKey(SERVER_TYPE_MAPPING,businessCode));
        if(null == appointServerInfo){
            logger.error("未配置业务服务信息，业务ID={}",businessCode);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_SERVER_INFO);
            return null;
        }
        return appointServerInfo.getServerTypeInfo();
    }

    private String getChStatus(ServerTypeEnum serverTypeEnum, AppointStatusEnum appointStatusEnum, List<SystemStatusInfo> systemStatusInfos) {
        for (SystemStatusInfo systemStatusInfo : systemStatusInfos) {
            if(appointStatusEnum.getIntValue() == systemStatusInfo.getSystemStatusCode().intValue()){
                switch (serverTypeEnum) {
                    case SHANGMEN:
                        return systemStatusInfo.getServiceTypeInfo().getToHomeStatusName();
                    case DAODIAN:
                        return systemStatusInfo.getServiceTypeInfo().getToShopStatusName();
                }
                return null;
            }
        }
        return null;
    }

    /**
     * 获取状态映射
     *
     * @param key
     * @return
     */
    private List<SystemStatusInfo> queryStatusConfig(String key) {
        //解析映射状态
        PopConfigProxy<AppointStatusInfo> popConfigProxy = new PopConfigProxy(new AppointStatusInfo());
        if(null == popConfigProxy.getConfig(key)){
            logger.error("统一配置映射状态为空");
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_STATUS_MAPPING);
            return null;
        }
        return popConfigProxy.getConfig(key).getSystemStatusInfoList();
    }

    /**
     * 生成key
     * @param prefix
     * @param businessCode
     * @return
     */
    private String generateKey(String prefix,String businessCode){
        return prefix + businessCode;
    }
}
