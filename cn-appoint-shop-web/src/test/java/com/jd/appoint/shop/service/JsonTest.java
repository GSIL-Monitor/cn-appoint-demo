package com.jd.appoint.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.jmi.jicc.client.domain.JICCCertImgParam;
import com.jd.jmi.jicc.client.domain.JICCSaveCertInfoRequest;
import com.jd.jmi.jicc.client.domain.JICCSaveCertInfoResult;
import com.jd.jmi.jicc.client.domain.JICCUserIdentityInfoParam;
import com.jd.jmi.jicc.client.domain.img.JICCImgUploadParam;
import com.jd.jmi.jicc.client.domain.img.JICCImgUploadResult;
import com.jd.jmi.jicc.client.enums.JICCAuditStatusEnum;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;
import com.jd.jmi.jicc.client.service.JICCImgInfoService;
import com.jd.jmi.jicc.client.service.impl.JICCIdentityInfoJSFService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bjliuyong on 2018/6/6.
 */

public class JsonTest {



    @Test
    public void getJson(){
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        LinkedHashMap<String, Object> map1 = new LinkedHashMap<>();
        map1.put("key","2");
        map1.put("value","待派单");
        list.add(map1);
        LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
        map2.put("key","3");
        map2.put("value","待服务");
        list.add(map2);
        LinkedHashMap<String, Object> map3 = new LinkedHashMap<>();
        map3.put("key","5");
        map3.put("value","预约失败");
        list.add(map3);
        LinkedHashMap<String, Object> map4 = new LinkedHashMap<>();
        map4.put("key","6");
        map4.put("value","预约完成");
        list.add(map4);


        result.put("data",list);
        //result.put("url","http://appoint.shop.jd.net/api/storeList");
        String jsonString = JSON.toJSONString(result);

        System.out.println(jsonString);
        /*Map<String, Object> jsonObject =JSON.parseObject(jsonString);
        for(Map.Entry<String, Object> entry:jsonObject.entrySet()){
            System.out.println((String)entry.getValue());
        }*/

    }

    @Test
    public void getDateJson() throws ParseException {
        String str = "2018-07-13";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        Map map = new HashMap();
        map.put("date",date);
        System.out.println(JSON.toJSONString(map));
    }
}