package com.jd.appoint.rpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by yangyuan on 6/29/18.
 */
@Component
public class RpcConfig {

    public static String VSC_APPCODE;

    public static String VSC_TOKEN;

    public static Integer VSC_SOURCE;

    public static String VSC_AESKEY;

    @Value("${app.name}")
    public void setAppCode(String appCode) {//不要声明为static方法
        RpcConfig.VSC_APPCODE = appCode;
    }

    @Value("${vsc.token}")
    public void setToken(String token) {
        RpcConfig.VSC_TOKEN = token;
    }

    @Value("${vsc.source}")
    public void setSource(Integer source) {
        RpcConfig.VSC_SOURCE = source;
    }

    @Value("${vsc.aeskey}")
    public void setAeskey(String aesKey){
        RpcConfig.VSC_AESKEY = aesKey;
    }
}
