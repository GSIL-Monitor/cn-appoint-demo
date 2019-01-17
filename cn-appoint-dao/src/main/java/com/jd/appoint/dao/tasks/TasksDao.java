package com.jd.appoint.dao.tasks;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisRepository
public interface TasksDao extends MybatisDao<TaskInfoPo> {

    /**
     * 通过功能查询信息
     *
     * @param functionId
     * @return
     */
    List<TaskInfoPo> findByFunctionId(@Param(value = "functionId") String functionId);

    /**
     * 通过功能查询信息
     *
     * @return
     */
    List<TaskInfoPo> findTaskByTypeAndStatus(@Param(value = "taskInfoPo") TaskInfoPo taskInfoPo);


    /**
     * 消费失败处理
     *
     * @param taskId
     */
    void failConsumerTask(@Param(value = "taskId") Long taskId);

    /**
     * 消费成功
     *
     * @param taskId
     */
    void consumerTask(@Param(value = "taskId") Long taskId);

    /**
     * 插入并获取主键
     * @return
     */
     Long insertAndGetId(TaskInfoPo taskInfoPo);
}