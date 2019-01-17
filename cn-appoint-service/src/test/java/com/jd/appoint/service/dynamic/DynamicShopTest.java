package com.jd.appoint.service.dynamic;

import com.jd.appoint.shopapi.ShopDynamicConfigFacade;
import com.jd.appoint.vo.dynamic.OperateItemVo;
import com.jd.appoint.vo.dynamic.ServerTypeVO;
import com.jd.fastjson.JSON;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.fast.FastJUnit4Tests;

import java.util.List;

/**
 * Created by shaohongsen on 2018/7/4.
 */
public class DynamicShopTest extends FastJUnit4Tests {
    @Autowired
    private ShopDynamicConfigFacade facade;

    @Test
    public void test() {
        SoaResponse<List<OperateItemVo>> listSoaResponse = facade.detailOperateList(new SoaRequest<Long>(285l));
        System.out.println(JSON.toJSONString(listSoaResponse));
    }
}
