package com.jd.appoint.shop.util;

import com.jd.pop.vender.passport.interceptor.core.context.LoginContext;
import com.jd.pop.vender.passport.interceptor.core.context.VenderLoginContext;
import org.springframework.util.StringUtils;

/**
 * Created by bjliuyong on 2018/5/25.
 */
public class UserpinGetter {

    /**
     * 获取userPin
     * @return
     */
    public static String get() {
        VenderLoginContext loginContext = (VenderLoginContext) LoginContext.getLoginContext();
        String userPin = loginContext.getPin();
        if(StringUtils.isEmpty(userPin)) {
            throw new IllegalStateException("can not find userPin") ;
        }
        return userPin;
    }
}
