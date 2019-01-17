package com.jd.appoint.service.innerapi.man;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.inner.man.ManAppointOrderFacade;
import com.jd.appoint.inner.man.dto.OrderDetailDTO;
import com.jd.appoint.inner.man.dto.OrderListDTO;
import com.jd.appoint.page.Page;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luqiang3 on 2018/5/17.
 */
public class ManAppointOrderFacadeTest extends JUnit4SpringContextTests {

    @Autowired
    private ManAppointOrderFacade manAppointOrderFacade;

    private Logger logger = LoggerFactory.getLogger(ManAppointOrderFacadeTest.class);

    @Test
    public void testList(){
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(20);
        page.setSearchMap(mockSearchMap());
        Page<OrderListDTO> list = manAppointOrderFacade.list(page);
        logger.info("ManAppointOrderFacade.list.......response={}", JSON.toJSONString(list));
    }

    @Test
    public void testDetail(){
        OrderDetailDTO detail = manAppointOrderFacade.detail(1L);
        logger.info("ManAppointOrderFacade.detail...........response={}", JSON.toJSONString(detail));
    }

    private Map<String, String> mockSearchMap(){
        Map<String, String> map = new HashMap<>();
        map.put("id.EQ", null);
        map.put("businessCode.EQ", "618618");
        map.put("venderId.EQ", null);
        map.put("appointStatus.EQ", null);
        map.put("customerName.EQ", null);
        map.put("customerUserPin.EQ", null);
        map.put("serverType.EQ", null);
        return map;
    }
}
