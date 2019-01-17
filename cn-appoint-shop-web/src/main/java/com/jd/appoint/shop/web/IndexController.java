package com.jd.appoint.shop.web;

import com.jd.appoint.shop.service.BusinessCodeService;
import com.jd.appoint.shop.util.Constants;
import com.jd.appoint.shop.util.VenderIdGetter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/5/24.
 *
 * shop端登陆 测试用户：
 * 一期 test_piaowu_6161
 * 二期 test-xtlsj  360buy
 *
 * zhaodongming1 passport.jd.com
 *
 */
@Controller
public class IndexController {

    @Resource BusinessCodeService businessCodeService;

    @RequestMapping(value = "/fuzhuang" , method = RequestMethod.GET)
    public String fuzhuang() {
        return "fuzhuang";
    }
    @RequestMapping(value = "/common" , method = RequestMethod.GET)
    public String common(Map map) {
        String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());

        map.put("businessCode",businessCode);
        map.put("timestamp",System.currentTimeMillis());
        return "common" ;
    }

}
