package com.jd.appoint.tasks.web.controller.jobs;

import com.jd.appoint.tasks.web.service.RegistryCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author lijizhen1@jd.com
 * @date 2018/1/3 15:45
 */
@RestController
@RequestMapping("/registry-center")
public class RegistryCenterRestFull {
    public static final String REG_CENTER_CONFIG_KEY = "reg_center_config_key";
    @Autowired
    private RegistryCenterService registryCenterService;

    /**
     * 判断是否存在已连接的注册中心配置.
     *
     * @param request HTTP请求
     * @return 是否存在已连接的注册中心配置
     */
    @RequestMapping("/activated")
    public boolean activated(final @Context HttpServletRequest request) {
        return registryCenterService.loadActivated().isPresent();
    }


}
