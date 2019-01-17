package com.jd.appoint.service.outerapi;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.api.FormControlFacade;
import com.jd.appoint.api.vo.FormControlQuery;
import com.jd.appoint.api.vo.FormItemCheckRequest;
import com.jd.appoint.vo.enums.ServerTypeEnum;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by yangyuan on 5/14/18.
 */
public class FormControlFacadeTest extends JUnit4SpringContextTests {

    @Autowired
    private FormControlFacade formControlFacade;



    @Test
    public void test(){
        SoaRequest<FormControlQuery> request = new SoaRequest<>();
        FormControlQuery query = new FormControlQuery();
        query.setBusinessCode("618618");
        query.setVenderId(48442l);
        query.setPageNo("1");
        request.setData(query);
        System.out.println(JSON.toJSONString(formControlFacade.getFormControl(request)));
    }

    @Test
    public void testCheck(){
        SoaRequest<FormItemCheckRequest> requestSoaRequest = new SoaRequest<>();
        FormItemCheckRequest formItemCheckRequest = new FormItemCheckRequest();
        //formItemCheckRequest.setServerType(ServerTypeEnum.GO);
        FormControlQuery formControlQuery = new FormControlQuery();
        formControlQuery.setBusinessCode("1001");
        //formItemCheckRequest.setFormControlQuery(formControlQuery);
        formItemCheckRequest.setContextId("39f39502-13dd-4a97-b189-31a9c6c3db0d");
//        Map<String, String> data = new HashMap<>();
//        data.put("name","yy");
//        formItemCheckRequest.setItemData(data);
        requestSoaRequest.setData(formItemCheckRequest);
        formControlFacade.checkFormItems(requestSoaRequest);
    }

}
