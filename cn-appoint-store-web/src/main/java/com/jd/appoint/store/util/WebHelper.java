package com.jd.appoint.store.util;

import com.jd.pop.store.passport.interceptor.core.context.LoginContext;
import com.jd.pop.store.passport.interceptor.core.context.StoreLoginContext;

/**
 * Created by wangshangyu on 2017/3/21.
 */
public class WebHelper {

    public static final Long getVendorId() {
        return StoreLoginContext.getLoginContext().getVenderId();
    }

    public static final String getPin() {
        return StoreLoginContext.getLoginContext().getPin();
    }

    public static final Long getShopId() {
        return ((StoreLoginContext) LoginContext.getLoginContext()).getShopId();
    }

    public static final Long getStoreId() {
        return ((StoreLoginContext) LoginContext.getLoginContext()).getStoreId();
    }
}
