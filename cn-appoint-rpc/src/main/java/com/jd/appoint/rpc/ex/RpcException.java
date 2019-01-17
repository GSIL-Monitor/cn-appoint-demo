package com.jd.appoint.rpc.ex;

/**
 * Created by shaohongsen on 2018/5/21.
 * rpc调用失败异常
 */
public class RpcException extends RuntimeException {
    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(String message) {
        super(message);
    }
}
