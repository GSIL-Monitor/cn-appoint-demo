package com.jd.appoint.tasks.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lijianzhen1 on 2017/3/15.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    /**
     * 返回主页面
     *
     * @return
     */
    @RequestMapping(value = "")
    public String index() {
        return "index";
    }

}
