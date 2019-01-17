package com.jd.appoint.service.task;

import com.jd.appoint.domain.tasks.TaskInfoPo;

import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/5 14:19
 */
public interface TasksService {
    /**
     * 插入数据
     *
     * @param taskInfoPo
     * @return
     */
    int addTask(TaskInfoPo taskInfoPo);

    /**
     * 通过功能ID获得任务
     *
     * @param functionId
     * @return
     */
    List<TaskInfoPo> findByFunctionId(String functionId);

    /**
     * 获取未处理的任务
     *
     * @param functionId
     * @return
     */
    List<TaskInfoPo> findTaskByTypeAndStatus(String functionId,Integer status);

    /**
     * 消费任务
     *
     * @param taskId
     */
    void consumerTask(Long taskId);

    /**
     * 消费失败
     *
     * @param taskId
     */
    void failConsumerTask(Long taskId);


    int addTask(String functionId, String content);

    /**
     * 插入并返回主键ID
     * @param taskInfoPo
     * @return
     */
    Long insertAndGetId(TaskInfoPo taskInfoPo);

}
