package com.jd.appoint.common.interfaces;

/*
created by lishuaiwei
2018/05/19
 */
/**
 * 数据库查询功能函数
 * 返回值为普通类型
 * @param <T>
 */
public interface DbObjectFunction<T> {
    T dbQueryObject();
}
