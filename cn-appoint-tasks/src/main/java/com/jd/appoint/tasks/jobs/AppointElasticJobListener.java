package com.jd.appoint.tasks.jobs;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by luqiang on 2018/5/16.
 */
public class AppointElasticJobListener implements ElasticJobListener {
    private Logger logger = LoggerFactory.getLogger(AppointElasticJobListener.class);

    private Long time = 0L;

    /**
     * 作业执行前的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        time = System.currentTimeMillis();
        logger.info(shardingContexts.getJobName() + "start," + new Date());
    }
    /**
     * 作业执行后的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long end = System.currentTimeMillis();
        long t = end - time;
        logger.info(shardingContexts.getJobName() + "end," + new Date() + ";execute time:" + t + "ms");
    }
}
