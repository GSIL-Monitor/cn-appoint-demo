package com.jd.appoint.service.util;

import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.service.config.ConfigData;
import com.jd.fastjson.JSONArray;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gaoxiaoqing on 2018/7/3.
 */
public class PopConfigProxy<T> {

    private static final Logger logger = LoggerFactory.getLogger(PopConfigProxy.class);

    //返回类型
    private T t;

    public PopConfigProxy(T t) {
        this.t = t;
    }
    public T getConfig(String key){
        T result = null;
        try {
            logger.info("开始获取配置，key={}",key);
            String configResult = ConfigData.getConfigValue(key);
            logger.info("完成获取配置，result={}",configResult);
            result = JSONArray.parseObject(configResult,(Class<T>)t.getClass());
        }catch (Exception e){
            logger.error("获取统一配置映射状态异常，key={}", key);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_STATUS_MAPPING);
            return null;
        }
        if(null == result){
            logger.error("统一配置映射状态为空，key={}", key);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_STATUS_MAPPING);
            return null;
        }
        return result;
    }
}
