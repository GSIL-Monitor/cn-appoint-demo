package com.jd.appoint.store.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index(Map map) {
        map.put("timestamp",System.currentTimeMillis());
        return "index";
    }
}
