package com.jd.appoint.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 9:37
 */

public abstract class BaseController  {
    /**
     * 获得日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
