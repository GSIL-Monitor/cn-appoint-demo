package com.jd.appoint.dao.mybatis;


/**
 * 基础的dao接口类
 * Created by lijianzhen1 on 2017/3/9.
 */
public interface MybatisDao<T> {
    /**
     * 获取单条数据通过对象的id
     *
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     * 插入单条数据数据
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 更新对象数据，主要是依据主键id
     *
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return
     */
    void delete(Long id);

}
