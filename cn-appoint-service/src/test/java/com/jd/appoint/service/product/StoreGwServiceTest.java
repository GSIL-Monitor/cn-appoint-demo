package com.jd.appoint.service.product;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jd.appoint.rpc.pop.impl.JDStoreService;
import com.jd.virtual.appoint.StoreGwService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by yangyuan on 6/28/18.
 */

public class StoreGwServiceTest  extends JUnit4SpringContextTests {

    @Autowired
    private StoreGwService storeGwService;

    @Autowired
    private JDStoreService storeService;

    @Test
    public void testStoreList(){
        System.out.println(JSON.toJSONString(storeService.getAllStore(20160613l)));
    }

    @Test
    public void testStoreInfo(){
        System.out.println(JSON.toJSONString(storeService.getStoresInfo(Lists.newArrayList("129875","129874","129873","130001","129802","129801","129601","129602","129603","129401","129403","124602","124603","124807","124806"))));
    }
}
