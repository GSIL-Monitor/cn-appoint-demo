package com.jd.appoint.tasks.web.service;


import com.dangdang.ddframe.job.lite.lifecycle.api.*;
import com.jd.appoint.domain.conf.ConfigurationCenter;

/**
 * Created by yangyuan on 9/21/17.
 */
public interface JobApiService {


    ConfigurationCenter getRegistry();

    JobSettingsAPI getJobSettingsAPI();

    JobOperateAPI getJobOperatorAPI();

    ShardingOperateAPI getShardingOperateAPI();

    JobStatisticsAPI getJobStatisticsAPI();

    ServerStatisticsAPI getServerStatisticsAPI();

    ShardingStatisticsAPI getShardingStatisticsAPI();
}
