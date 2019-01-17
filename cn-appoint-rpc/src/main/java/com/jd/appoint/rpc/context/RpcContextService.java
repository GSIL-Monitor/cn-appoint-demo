package com.jd.appoint.rpc.context;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.rpc.context.dto.ScheduleContextDTO;
import com.jd.common.util.StringUtils;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jd.virtual.appoint.AppointContextService;
import com.jd.virtual.appoint.ContextKeys;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luqiang3 on 2018/6/21.
 */
@Service
public class RpcContextService {

    private static Logger logger = LoggerFactory.getLogger(RpcContextService.class);

    @Resource
    private AppointContextService appointContextService;

    /**
     * RPC查询Context信息
     * @param contextId
     * @return
     */
    public Map<String, String> getContext(String contextId) {
        CallerInfo info = null;
        try {
            info = Profiler.registerInfo("com.jd.appoint.rpc.context.RpcContextService.getContext", false, true);
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setContextId(contextId);
            logger.info("查询Context信息-CommonRequest={}", JSON.toJSONString(commonRequest));
            CommonResponse<Map<String, String>> context = appointContextService.getContext(commonRequest);
            logger.info("查询Context信息-CommonResponse={}", JSON.toJSONString(context));
            return context.getResult();
        }catch (Exception e){
            logger.error("查询Context信息异常：{}", e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return Maps.newHashMap();
    }

    /**
     * RPC删除Context信息
     * @param contextId
     * @return
     */
    public boolean dropContext(String contextId) {
        CallerInfo info = null;
        try {
            info = Profiler.registerInfo("com.jd.appoint.rpc.context.RpcContextService.dropContext", false, true);
            logger.info("RPC删除Context信息request={}", JSON.toJSONString(contextId));
            CommonResponse<Boolean> context = appointContextService.delContext(contextId);
            logger.info("RPC删除Context信息response={}", JSON.toJSONString(context));
            return context.getResult();
        } catch (Exception e) {
            logger.error("RPC删除Context信息 error", e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return false;
    }

    /**
     * 获得产能日历Context信息
     *
     * @param contextId
     * @return
     */
    public ScheduleContextDTO getScheduleContextDTO(String contextId) {
        Map<String, String> contextMap = getContext(contextId);//mock();
        ScheduleContextDTO dto = new ScheduleContextDTO();
        dto.setBusinessCode(contextMap.get(ContextKeys.BUSINESS_CODE));
        String venderId = contextMap.get(ContextKeys.VENDER_ID);
        dto.setVenderId(StringUtils.isNotBlank(venderId) ? Long.parseLong(venderId) : null);
        dto.setStoreCode(contextMap.get(ContextKeys.STORE_CODE));
        String skuId = contextMap.get(ContextKeys.SKU_ID);
        dto.setSkuId(StringUtils.isNotBlank(skuId) ? Long.parseLong(skuId) : null);
        String startDate = contextMap.get(ContextKeys.START_DATE);
        if (StringUtils.isNotBlank(startDate)) {
            dto.setStartDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", startDate));
        }
        String endDate = contextMap.get(ContextKeys.END_DATE);
        if (StringUtils.isNotBlank(endDate)) {
            dto.setEndDate(AppointDateUtils.getStrToDate("yyyy-MM-dd", endDate));
        }
        dto.setContextId(contextId);
        return dto;
    }

    private Map<String, String> mock() {
        Map<String, String> map = new HashMap<>();
        map.put(ContextKeys.BUSINESS_CODE, "101");
        map.put(ContextKeys.VENDER_ID, "1115");
        map.put(ContextKeys.SKU_ID, "6442961");
        map.put(ContextKeys.START_DATE, "2018-08-01");
        map.put(ContextKeys.END_DATE, "2018-08-31");
        return map;
    }
}
