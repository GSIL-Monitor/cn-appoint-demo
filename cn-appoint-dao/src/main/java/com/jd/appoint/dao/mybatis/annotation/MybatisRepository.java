package com.jd.appoint.dao.mybatis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by lijianzhen1 on 2017/3/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MybatisRepository {
    /**
     * 声明一个仓库的名称
     *
     * @return
     */
    String value() default "";
}
