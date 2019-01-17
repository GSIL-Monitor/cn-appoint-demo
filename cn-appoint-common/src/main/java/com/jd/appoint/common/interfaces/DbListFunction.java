package com.jd.appoint.common.interfaces;

import java.util.List;
/*
created by lishuaiwei
2018/05/19
 */
/**
 * 数据库查询功能函数
 * 返回值为List类型
 * @param <T>
 */

public interface DbListFunction<T> {
    List<T> dbQueryList();
}
