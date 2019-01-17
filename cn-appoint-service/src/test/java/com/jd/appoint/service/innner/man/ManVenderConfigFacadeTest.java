package com.jd.appoint.service.innner.man;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.inner.man.ManVenderConfigFacade;
import com.jd.appoint.inner.man.dto.VenderConfigDTO;
import com.jd.appoint.inner.man.dto.VenderConfigFormDTO;
import com.jd.appoint.service.shopapi.ShopStaffFacadeTest;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import webJunit.JUnit4SpringContextTests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManVenderConfigFacadeTest extends JUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ShopStaffFacadeTest.class);

    @Autowired
    private ManVenderConfigFacade manVenderConfigFacade;

    @Test
    public void testPage() {
        SoaRequest<VenderConfigFormDTO> soaRequest = new SoaRequest<>();
        VenderConfigFormDTO venderConfigFormDTO = new VenderConfigFormDTO();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("key", "route");
        searchMap.put("value", null);
        searchMap.put("venderId", null);
        searchMap.put("businessCode", null);
        soaRequest.setData(venderConfigFormDTO);
        SoaResponse<List<VenderConfigDTO>> soaResponse = manVenderConfigFacade.getVenderConfig(soaRequest);
        logger.info("=======================" + JSONArray.toJSON(soaResponse));
    }


    @Test
    @Rollback(value = false)
    public void Testinvoke() {
        /*SoaRequest<VenderConfigDTO> soaRequest = new SoaRequest<>();
        VenderConfigDTO venderConfigDTO = new VenderConfigDTO();
        venderConfigDTO.setBusinessCode("CHONGWU");
        venderConfigDTO.setVenderId(8787L);
        venderConfigDTO.setKey("route");
        venderConfigDTO.setValue("API");soaRequest.setData(venderConfigDTO);
        //SoaResponse soaResponse = manVenderConfigFacade.invoke(soaRequest, InvokeMethod.INVOKE_ADD);
        System.out.println(JSONArray.toJSONString(soaRequest));
        System.out.println("============================== test invoke add end ");*/


      /*  SoaRequest<VenderConfigDTO> soaRequest1 = new SoaRequest<>();
        VenderConfigDTO uvenderConfigDTO = new VenderConfigDTO();
        uvenderConfigDTO.setBusinessCode("TIJIAN");
        uvenderConfigDTO.setId(2L);
        uvenderConfigDTO.setVenderId(1L);
        uvenderConfigDTO.setKey("route");
        uvenderConfigDTO.setValue("api_ciming");
        soaRequest1.setData(uvenderConfigDTO);
        SoaResponse soaResponse1 = manVenderConfigFacade.invoke(soaRequest1, InvokeMethod.INVOKE_UPDATE);
        System.out.println(JSONArray.toJSONString(soaResponse1));
        System.out.println("============================== test invoke update end ");*/
    }
}
