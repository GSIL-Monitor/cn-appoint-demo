package com.jd.appoint.tasks.web.service.impl;

import com.dangdang.ddframe.job.lite.lifecycle.api.*;
import com.google.common.base.Optional;
import com.jd.appoint.domain.conf.ConfigurationCenter;
import com.jd.appoint.tasks.web.service.JobApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by yangyuan on 9/21/17.
 */
@Service("jobAPIService")
public class JobApiServiceImpl implements JobApiService {

    @Value("#{configProperties['zkRegistryName']}")
    private String zkRegistryName;

    @Value("#{configProperties['zkNameSpace']}")
    private String zkNameSpace;

    @Value("#{configProperties['zkAddress']}")
    private String zkAddress;


    @Override
    public JobSettingsAPI getJobSettingsAPI() {
        ConfigurationCenter regCenterConfig = getRegistry();
        return JobAPIFactory.createJobSettingsAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }

    @Override
    public JobStatisticsAPI getJobStatisticsAPI() {
        ConfigurationCenter regCenterConfig = getRegistry();
        return JobAPIFactory.createJobStatisticsAPI(zkAddress, zkNameSpace,  Optional.fromNullable(regCenterConfig.getDigest()));
    }

    @Override
    public ServerStatisticsAPI getServerStatisticsAPI() {
        ConfigurationCenter regCenterConfig = getRegistry();
        return JobAPIFactory.createServerStatisticsAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }

    @Override
    public ShardingStatisticsAPI getShardingStatisticsAPI() {
        ConfigurationCenter regCenterConfig = getRegistry();
        return JobAPIFactory.createShardingStatisticsAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }

    @Override
    public JobOperateAPI getJobOperatorAPI() {
        ConfigurationCenter regCenterConfig = getRegistry();
        return JobAPIFactory.createJobOperateAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }

    @Override
    public ShardingOperateAPI getShardingOperateAPI() {
        ConfigurationCenter regCenterConfig = getRegistry();
        return JobAPIFactory.createShardingOperateAPI(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
    }

    @Override
    public ConfigurationCenter getRegistry() {
        ConfigurationCenter configurationCenter = new ConfigurationCenter(zkRegistryName);
        configurationCenter.setNamespace(zkNameSpace);
        configurationCenter.setZkAddressList(zkAddress);
        configurationCenter.setDigest("11");
        configurationCenter.setActivated(true);
        return configurationCenter;
    }
}
