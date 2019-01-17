package com.jd.appoint.tasks.worker;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jd.appoint.inner.tasks.TasksAppointFacade;

import javax.annotation.Resource;

/**
 * Created by luqiang3 on 2018/7/20.
 */
public class InitStockJob implements SimpleJob {

    @Resource
    private TasksAppointFacade tasksAppointFacade;

    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        tasksAppointFacade.initStock();
    }
}
