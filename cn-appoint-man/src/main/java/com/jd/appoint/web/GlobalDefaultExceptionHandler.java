package com.jd.appoint.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by lijianzhen1 on 2017/3/4.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler extends BaseController {
    /**
     * 全局异常的控制拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public void exception(Exception e) {
        // 添加自己的异常处理逻辑，如日志记录　　　
        logger.error("捕获Controller以下层级中的没有处理的运行时异常[{}]", e);
    }
}
