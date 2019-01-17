package com.jd.appoint.tasks.worker;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jd.appoint.inner.tasks.TasksAppointFacade;
import com.jd.travel.base.soa.SoaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by luqiang3 on 2018/8/25.
 */
public class RouteSubscribeJob implements SimpleJob {

    private final Logger logger = LoggerFactory.getLogger(RouteSubscribeJob.class);

    @Resource
    private TasksAppointFacade tasksAppointFacade;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            tasksAppointFacade.routeSubscribe(new SoaRequest());
        } catch (Exception exc) {
            logger.error("cancelJob(),error:{}", exc);
        }
    }
}
