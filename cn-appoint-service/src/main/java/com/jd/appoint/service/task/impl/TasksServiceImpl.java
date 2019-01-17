package com.jd.appoint.service.task.impl;

import com.jd.appoint.dao.tasks.TasksDao;
import com.jd.appoint.domain.enums.TasksStatusEnum;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import com.jd.appoint.service.task.TasksService;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/5 14:20
 */
@Service
public class TasksServiceImpl implements TasksService {
    @Autowired
    private TasksDao tasksDao;

    @Override
    public int addTask(TaskInfoPo taskInfoPo) {
        return tasksDao.insert(taskInfoPo);
    }

    @Override
    public List<TaskInfoPo> findByFunctionId(String functionId) {
        return tasksDao.findByFunctionId(functionId);
    }

    @Override
    public List<TaskInfoPo> findTaskByTypeAndStatus(String functionId,Integer status) {
        TaskInfoPo taskInfoPo = new TaskInfoPo();
        taskInfoPo.setFunctionId(functionId);
        taskInfoPo.setTaskStatus(TasksStatusEnum.getFromCode(status));
        return tasksDao.findTaskByTypeAndStatus(taskInfoPo);
    }
    /**
     * 任务消费成功
     *
     * @param taskId
     */
    @Override
    public void consumerTask(Long taskId) {
        tasksDao.consumerTask(taskId);
    }

    /**
     * 任务消费失败
     *
     * @param appointId
     */
    @Override
    public void failConsumerTask(Long appointId) {
        tasksDao.failConsumerTask(appointId);
    }

    @UmpMonitor(logCollector =
    @LogCollector(description = "添加任务", classify = TasksServiceImpl.class))
    @Override
    public int addTask(String functionId, String content) {
        TaskInfoPo taskInfoPo = new TaskInfoPo();
        taskInfoPo.setFunctionId(functionId);
        taskInfoPo.setRetryTimes(0);
        taskInfoPo.setMaxRetry(3);
        taskInfoPo.setContent(content);
        taskInfoPo.setTaskStatus(TasksStatusEnum.DEAL_DOING);
        return this.addTask(taskInfoPo);
    }

    @Override
    public Long insertAndGetId(TaskInfoPo taskInfoPo) {
        return tasksDao.insertAndGetId(taskInfoPo);
    }


}
