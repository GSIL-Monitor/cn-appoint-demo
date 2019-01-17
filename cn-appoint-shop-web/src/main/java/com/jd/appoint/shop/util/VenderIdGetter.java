package com.jd.appoint.shop.util;

import com.jd.pop.vender.passport.interceptor.core.context.LoginContext;
import com.jd.pop.vender.passport.interceptor.core.context.VenderLoginContext;

/**
 * Created by bjliuyong on 2018/5/25.
 */
public class VenderIdGetter {

    /**
     * 获取venderId
     * @return
     */
    public static Long get() {
        VenderLoginContext loginContext = (VenderLoginContext) LoginContext.getLoginContext();
        long venderId = loginContext.getVenderId() ;
        if(venderId <= 0 ) {
            throw  new IllegalStateException("can not find venderId") ;
        }
        return  venderId ;
    }
}
