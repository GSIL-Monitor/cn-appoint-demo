package com.jd.appoint.tasks.worker;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jd.appoint.inner.tasks.TasksAppointFacade;
import com.jd.travel.base.soa.SoaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 改期定时任务
 * Created by gaoxiaoqing on 2018/7/17.
 */
@Component
public class ReschduleJob implements SimpleJob{

    private final Logger logger = LoggerFactory.getLogger(ReschduleJob.class);
    @Resource
    private TasksAppointFacade tasksAppointFacade;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            tasksAppointFacade.reschduling(new SoaRequest());
        } catch (Exception e) {
            logger.error("改期任务异常，请查看！");
        }
    }
}
