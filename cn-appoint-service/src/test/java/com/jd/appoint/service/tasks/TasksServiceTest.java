package com.jd.appoint.service.tasks;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.domain.enums.TasksStatusEnum;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import com.jd.appoint.inner.tasks.TasksAppointFacade;
import com.jd.appoint.service.task.TasksService;
import com.jd.appoint.service.task.constants.TaskConstants;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangyuan on 5/15/18.
 */
public class TasksServiceTest extends JUnit4SpringContextTests {

    @Autowired
    private TasksService tasksService;
    @Resource
    private TasksAppointFacade tasksAppointFacade;

    @Test
    public void TestAdd(){
        TaskInfoPo taskInfoPo=new TaskInfoPo();
        taskInfoPo.setRetryTimes(3);
        taskInfoPo.setTaskStatus(TasksStatusEnum.DEAL_DOING);
        taskInfoPo.setFunctionId(TaskConstants.RENOTICE_APPOINT_JMQ_NOTICE);
        taskInfoPo.setMaxRetry(3);
        taskInfoPo.setContent("");
        tasksService.addTask(taskInfoPo);
    }

    @Test
    public void TestfindByFunctionId(){
        List<TaskInfoPo> taskInfoPoList=tasksService.findByFunctionId(TaskConstants.RENOTICE_APPOINT_JMQ_NOTICE);
        System.out.println(JSONArray.toJSONString(taskInfoPoList));
    }


    @Test
    public void TestconsumerTask(){
        tasksService.consumerTask(2L);
    }


    @Test
    public void TestfailConsumerTask(){
        tasksService.failConsumerTask(2L);
    }

    @Test
    public void testReschduleTask(){
        tasksAppointFacade.reschduling(new SoaRequest());
    }
}
