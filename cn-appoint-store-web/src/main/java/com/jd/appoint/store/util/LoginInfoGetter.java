package com.jd.appoint.store.util;

import com.jd.pop.store.passport.interceptor.core.context.LoginContext;
import com.jd.pop.store.passport.interceptor.core.context.StoreLoginContext;
import org.springframework.util.StringUtils;

/**
 * Created by bjliuyong on 2018/5/25.
 */
public class LoginInfoGetter {

    /**
     * 获取venderId
     * @return
     */
    public static Long getVenderId() {
        StoreLoginContext loginContext = (StoreLoginContext) LoginContext.getLoginContext();
        long venderId = loginContext.getVenderId() ;
        if(venderId <= 0 ) {
            throw  new IllegalStateException("can not find venderId") ;
        }
        return  venderId ;
    }

    /**
     * 获取userPin
     * @return
     */
    public static String getUserPin() {
        StoreLoginContext loginContext = (StoreLoginContext) LoginContext.getLoginContext();
        String userPin = loginContext.getPin();
        if(StringUtils.isEmpty(userPin)) {
            throw new IllegalStateException("can not find userPin") ;
        }
        return userPin;
    }

    public static Long getStoreId() {
        StoreLoginContext loginContext = (StoreLoginContext) LoginContext.getLoginContext();
        long storeId = loginContext.getStoreId();
        if(storeId <= 0 ) {
            throw  new IllegalStateException("can not find storeId") ;
        }
        return  storeId ;
    }
}
