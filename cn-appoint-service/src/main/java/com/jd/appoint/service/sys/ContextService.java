package com.jd.appoint.service.sys;

import java.util.Map;

/**
 * Created by shaohongsen on 2018/6/28.
 */
public interface ContextService {

    <T> T getBean(String contextId, Class<T> tClass);

    <T> T getBean(Map<String, String> context, Class<T> tClass);

    Map<String, String> validateContext(Map<String, String> context);

}
