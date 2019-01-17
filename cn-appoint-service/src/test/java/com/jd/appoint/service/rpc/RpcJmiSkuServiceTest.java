package com.jd.appoint.service.rpc;

import com.jd.appoint.rpc.jmi.sku.RpcJmiSkuService;
import com.jd.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.fast.FastJUnit4Tests;

public class RpcJmiSkuServiceTest extends FastJUnit4Tests {
    @Autowired
    private RpcJmiSkuService jmiSkuService;

    @Test
    public void test() {
        System.out.println(JSON.toJSONString(jmiSkuService.getSku(200100633944l)));
    }
}
