package com.jd.appoint.shop.util;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shop.exception.RpcException;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jd.appoint.shop.util.Constants.APP_NAME;
import static com.jd.appoint.shop.util.Constants.UMP_PREFIX;
import static com.jd.travel.base.soa.SoaError.PARAMS_EXCEPTION;

/**
 * Created by bjliuyong on 2018/5/17.
 */
public class Utils {

    private static Logger LOG = LoggerFactory.getLogger(Utils.class) ;

    public static <V> V  call(Callable<V> callable ) {
        V result  ;
        try {
            result = callable.call() ;
            LOG.info("result {}" , JSON.toJSONString(result));
        } catch (Exception e) {
            LOG.error("Utils.call()出现异常："+e.getMessage());
            return (V)exceptionHandler(e , null) ;
        }
        return  result ;
    }


    public static <V> V  call(Callable<V> callable , String umpKey ,Object ... args) {
        V result  ;
        CallerInfo callerInfo = Profiler.registerInfo(UMP_PREFIX + umpKey ,APP_NAME ,
            false ,true) ;
        try {
            result = callable.call() ;
            log(umpKey , false , args) ;
            LOG.info(umpKey + ".result {}" , JSON.toJSONString(result));
        } catch (Exception e) {
            log(umpKey , true , args) ;
            LOG.error(umpKey + ".exception " , e);
            return (V)exceptionHandler(e , callerInfo) ;
        } finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return  result ;
    }


    private static void log(String prefix ,boolean isError , Object ... args ) {
        if(args != null && args.length > 0) {
            for(int i = 0 ; i < args.length ; i++ ) {
                if(isError)
                    LOG.error(prefix + " param {}" , args[i]);
                else
                    LOG.debug(prefix + " param {}" , args[i]);
            }
        }
    }

    private static SoaResponse exceptionHandler(Exception e ,CallerInfo callerInfo) {

        if(e instanceof IllegalArgumentException)
            return new SoaResponse(PARAMS_EXCEPTION , e.getMessage());

        if(callerInfo != null )
            Profiler.functionError(callerInfo);
        String msg = "server handler exception";
        if(e instanceof RpcException)
            msg = e.getMessage() ;

        return new SoaResponse(SoaError.SERVER_EXCEPTION , msg) ;
    }

    public static void validateArgsBlank(String s , String msg) {
        if(StringUtils.isBlank(s))
            throw new IllegalArgumentException(msg) ;
    }

    public static void validateNull(Object o , String msg) {
        if(o == null)
            throw new IllegalArgumentException(msg) ;
    }

    public static void validate(boolean exp , String msg) {
        if(!exp)
            throw new IllegalArgumentException(msg) ;
    }

}
