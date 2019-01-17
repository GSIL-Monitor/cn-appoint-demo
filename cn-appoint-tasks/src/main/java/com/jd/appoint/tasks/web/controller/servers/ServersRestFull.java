package com.jd.appoint.tasks.web.controller.servers;

import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.ServerBriefInfo;
import com.google.common.base.Optional;
import com.jd.appoint.tasks.web.service.JobApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by luqiang3 on 2018/1/3.
 */
@RestController
@RequestMapping("/servers")
public class ServersRestFull {

    @Autowired
    private JobApiService jobAPIService;

    /**
     * 获得服务器列表信息
     *
     * @return
     */
    @RequestMapping(value = {""})
    @ResponseBody
    public Collection<ServerBriefInfo> getAllServersBriefInfo() {
        return jobAPIService.getServerStatisticsAPI().getAllServersBriefInfo();
    }

    /**
     * 通过服务器IP查询作业列表
     *
     * @return
     */
    @RequestMapping(value = "/jobs")
    @ResponseBody
    public Collection<JobBriefInfo> getJobs(@RequestParam final String serverIp, final ModelMap model) {
        model.put("serverIp", serverIp);
        return jobAPIService.getJobStatisticsAPI().getJobsBriefInfo(serverIp);
    }

    /**
     * 禁用服务器.
     *
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/{serverIp}/disable", method = RequestMethod.POST)
    @ResponseBody
    public void disableServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().disable(Optional.<String>absent(), Optional.of(serverIp));
    }

    /**
     * 启用服务器.
     *
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/{serverIp}/enable", method = RequestMethod.DELETE)
    @ResponseBody
    public void enableServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().enable(Optional.<String>absent(), Optional.of(serverIp));
    }

    /**
     * 终止服务器.
     *
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/{serverIp}/shutdown", method = RequestMethod.POST)
    @ResponseBody
    public void shutdownServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.<String>absent(), Optional.of(serverIp));
    }

    /**
     * 删除服务器.
     *
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/config/{serverIp}")
    public void removeServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().remove(Optional.<String>absent(), Optional.of(serverIp));
    }

    /**
     * 禁用单个服务器的单个作业.
     *
     * @param jobName  作业名称
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/{serverIp}/jobs/{jobName}/disable", method = RequestMethod.POST)
    @ResponseBody
    public void disableServer(@PathVariable("serverIp") final String serverIp,
                              @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(Optional.of(jobName), Optional.of(serverIp));
    }

    /**
     * 启用单个服务器的单个作业.
     *
     * @param jobName  作业名称
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/{serverIp}/jobs/{jobName}/enable", method = RequestMethod.DELETE)
    @ResponseBody
    public void enableServer(@PathVariable("serverIp") final String serverIp,
                             @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(Optional.of(jobName), Optional.of(serverIp));
    }

    /**
     * 终止单个服务器的单个作业.
     *
     * @param jobName  作业名称
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/{serverIp}/jobs/{jobName}/shutdown", method = RequestMethod.POST)
    @ResponseBody
    public void shutdownServer(@PathVariable("serverIp") final String serverIp,
                               @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(jobName), Optional.of(serverIp));
    }

    /**
     * 删除单个服务器的单个作业.
     *
     * @param jobName  作业名称
     * @param serverIp 服务器IP
     */
    @RequestMapping(value = "/config/{serverIp}/jobs/{jobName}")
    public void removeServer(@PathVariable("serverIp") final String serverIp,
                             @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().remove(Optional.of(jobName), Optional.of(serverIp));
    }

}
