package com.jd.appoint.web;

import com.jd.adminlte4j.web.springmvc.ApiAdminController;
import com.jd.common.web.LoginContext;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 15:35
 */
@Controller
public class IndexController extends ApiAdminController {
    @Override
    public String getUserName(HttpServletRequest request) {
        return LoginContext.getLoginContext().getNick();
    }
}
