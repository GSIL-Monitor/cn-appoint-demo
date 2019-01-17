/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.jd.appoint.tasks.web.controller.manager;


import com.jd.uim.annotation.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/worker")
public class DashboardController {
    /**
     * jobs管理
     *
     * @return
     */
    @RequestMapping(value = "jobs", method = RequestMethod.GET)
    @Authorization(value = "WORKER_OVERVIEW")
    public String registryCenterPage() {
        return "jobs/job_brief_infos";
    }

    /**
     * jobs配置信息管理
     *
     * @return
     */
    @RequestMapping(value = "/jobs/config", method = RequestMethod.GET)
    @Authorization(value = "WORKER_OVERVIEW")
    public String getJobSettings() {
        return "jobs/job_config";
    }

    /**
     * jobs详情管理
     * @param jobName job名称
     * @param model
     * @return
     */
    @RequestMapping(value = "/jobs/detail", method = RequestMethod.GET)
    @Authorization(value = "WORKER_OVERVIEW")
    public String getJobDetail(@RequestParam final String jobName,final ModelMap model) {
        model.put("jobName", jobName);
        return "jobs/job_status_detail";
    }

    /**
     * servers管理
     *
     * @return
     */
    @Authorization(value = "WORKER_OVERVIEW")
    @RequestMapping(value = "/servers", method = RequestMethod.GET)
    public String serversManager() {
        return "servers/servers_status_overview";
    }

    /**
     * server详情管理
     *
     * @return
     */
    @RequestMapping(value = "/server/detail")
    @Authorization(value = "WORKER_OVERVIEW")
    public String serverDetail(@RequestParam final String serverIp, final ModelMap model) {
        model.put("serverIp", serverIp);
        return "servers/server_status_detail";
    }
}
