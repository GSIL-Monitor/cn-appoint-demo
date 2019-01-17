package com.jd.appoint.service.outerapi;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.virtual.appoint.StoreGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.PageRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by yangyuan on 7/2/18.
 */
public class StoreGwServiceTest extends JUnit4SpringContextTests {


    @Autowired
    @InjectMocks
    private StoreGwService storeGwService;

    @Mock
    private RpcContextService rpcContextService;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        Map<String, String> map = new HashMap<>();
       // map.put("skuId","6442961");
        map.put("venderId","1115");
        when(rpcContextService.getContext("123")).thenReturn(map);
    }

    @Test
    public void test(){
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setContextId("123");
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(1);
        pageRequest.setPageSize(10);
        System.out.println(JSON.toJSONString(storeGwService.getStoreList(commonRequest, pageRequest)));
    }
}

