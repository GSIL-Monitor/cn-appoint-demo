package com.jd.appoint.service.config;

import com.jd.pop.configcenter.client.ConfigCenterClient;
import com.jd.pop.configcenter.client.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据配置
 * Created by gaoxiaoqing on 2018/5/7.
 */
public class ConfigData {

    private static final Logger logger = LoggerFactory.getLogger(ConfigData.class);

    private static ConfigCenterClient configCenterClient;

    public static  void init(ConfigCenterClient configCenterClient) {
        ConfigData.configCenterClient = configCenterClient;
    }

    /**
     * 配置项
     * @param key
     * @return 字符串/json/xml
     */
    public static String getConfigValue(String key) {
        if(StringUtils.isEmpty(key)){
            logger.error("获取动态配置key为空");
            return null;
        }
        return configCenterClient.get(key);
    }
    /**
     * 开关配置
     * @param key
     * @return（true-开 false-关）
     */
    public static Boolean getSwitch(String key) {
        if(StringUtils.isEmpty(key)){
            logger.error("获取配置开关key为空");
            return false;
        }
        if(StringUtils.isEmpty(configCenterClient.get(key))){
            logger.error("开关配置为空");
            return false;
        }
        return Boolean.valueOf(configCenterClient.get(key));
    }
}
