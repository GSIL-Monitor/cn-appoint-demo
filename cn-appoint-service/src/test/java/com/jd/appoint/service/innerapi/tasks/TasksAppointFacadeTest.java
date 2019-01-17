package com.jd.appoint.service.innerapi.tasks;

import com.jd.appoint.inner.tasks.TasksAppointFacade;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/8/21.
 */
public class TasksAppointFacadeTest extends JUnit4SpringContextTests {

    @Autowired
    private TasksAppointFacade tasksAppointFacade;

    @Test
    public void testRouteSubscribe(){
        tasksAppointFacade.routeSubscribe(new SoaRequest());
    }

    @Test
    public void testCancel(){
        tasksAppointFacade.cancelJob(new SoaRequest());
    }
}
