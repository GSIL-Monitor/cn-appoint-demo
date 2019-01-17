package com.jd.appoint.common.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * 加载对应的本地properties文件
 * Created by lijianzhen1 on 2017/3/8.
 */
public class PropertiesLoader {
    private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
    private final Properties properties;

    public PropertiesLoader(String resourcesPath) {
        properties = loadProperties(resourcesPath);
    }

    /**
     * 加载对应的properties文件
     *
     * @param resourcesPath
     * @return
     */
    private Properties loadProperties(String resourcesPath) {
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadAllProperties(resourcesPath);
        } catch (IOException e) {
            logger.error("加载[{}].properties文件时候出现异常[{}]", resourcesPath, e);
        }
        return props;
    }

    /**
     * 返回当前加载的properties文件内容.
     *
     * @return
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * 获得当前文件的property
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    /**
     * 通过key获得value值
     *
     * @param key
     * @return
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return "";
    }
}
