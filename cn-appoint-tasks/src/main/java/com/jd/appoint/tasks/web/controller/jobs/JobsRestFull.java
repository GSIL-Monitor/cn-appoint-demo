package com.jd.appoint.tasks.web.controller.jobs;

import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobSettings;
import com.dangdang.ddframe.job.lite.lifecycle.domain.ShardingInfo;
import com.google.common.base.Optional;
import com.jd.appoint.tasks.web.service.JobApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author lijizhen1@jd.com
 * @date 2018/1/3 14:25
 */
@RestController
@RequestMapping("/jobs")
public class JobsRestFull {
    @Autowired
    private JobApiService jobAPIService;


    /**
     * 获取作业配置.
     *
     * @param jobName 作业名称
     * @return 作业配置
     */
    @RequestMapping(value = "/config/{jobName}", method = RequestMethod.GET)
    public JobSettings getJobSettings(@PathVariable("jobName") final String jobName) {
        return jobAPIService.getJobSettingsAPI().getJobSettings(jobName);
    }

    /**
     * 修改作业配置.
     *
     * @param jobSettings 作业配置
     */
    @RequestMapping(value = "/config", method = RequestMethod.PUT)
    @ResponseBody
    public Boolean updateJobSettings(@RequestBody final JobSettings jobSettings) {
        try {
            jobAPIService.getJobSettingsAPI().updateJobSettings(jobSettings);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除作业配置.
     *
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/config/{jobName}")
    public void removeJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobSettingsAPI().removeJobSettings(jobName);
    }


    /**
     * 获取作业详情.
     *
     * @return 作业详情集合
     */
    @RequestMapping(value = {""})
    @ResponseBody
    public Collection<JobBriefInfo> getAllJobsBriefInfo() {
        return jobAPIService.getJobStatisticsAPI().getAllJobsBriefInfo();
    }

    /**
     * 触发作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping("/{jobName}/trigger")
    public void triggerJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().trigger(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 禁用作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{jobName}/disable", method = RequestMethod.POST)
    @ResponseBody
    public void disableJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 启用作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{jobName}/enable", method = RequestMethod.DELETE)
    @ResponseBody
    public void enableJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 终止作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{jobName}/shutdown", method = RequestMethod.POST)
    @ResponseBody
    public void shutdownJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 获取分片信息.
     *
     * @param jobName 作业名称
     * @return 分片信息集合
     */
    @RequestMapping(value = "/{jobName}/sharding", method = RequestMethod.GET)
    @ResponseBody
    public Collection<ShardingInfo> getShardingInfo(@PathVariable("jobName") final String jobName) {
        return jobAPIService.getShardingStatisticsAPI().getShardingInfo(jobName);
    }

    @RequestMapping(value = "/{jobName}/sharding/{item}/disable", method = RequestMethod.POST)
    @ResponseBody
    public void disableSharding(@PathVariable("jobName") final String jobName, @PathVariable("item") final String item) {
        jobAPIService.getShardingOperateAPI().disable(jobName, item);
    }

    @RequestMapping(value = "/{jobName}/sharding/{item}/disable", method = RequestMethod.DELETE)
    @ResponseBody
    public void enableSharding(@PathVariable("jobName") final String jobName, @PathVariable("item") final String item) {
        jobAPIService.getShardingOperateAPI().enable(jobName, item);
    }

}
