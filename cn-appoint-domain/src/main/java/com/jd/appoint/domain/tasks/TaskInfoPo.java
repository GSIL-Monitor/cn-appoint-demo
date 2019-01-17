package com.jd.appoint.domain.tasks;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.TasksStatusEnum;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/4 15:31
 */
public class TaskInfoPo extends BaseEntity {
    /**
     * 功能id
     */
    private String functionId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 任务状态
     */
    private TasksStatusEnum taskStatus;
    /**
     * 重试次数 +1
     */
    private Integer retryTimes;
    /**
     * 最大重试次数
     */
    private Integer maxRetry;
    /**
     * 版本控制
     */
    private Integer dataVersion;


    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public TasksStatusEnum getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TasksStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
    }


    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }
}
