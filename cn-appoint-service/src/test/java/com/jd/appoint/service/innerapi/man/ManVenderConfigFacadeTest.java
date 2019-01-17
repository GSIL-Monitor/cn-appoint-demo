package com.jd.appoint.service.innerapi.man;

import com.jd.appoint.inner.man.ManVenderConfigFacade;
import com.jd.appoint.inner.man.dto.InvokeMethod;
import com.jd.appoint.inner.man.dto.VenderConfigFormDTO;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/6/29.
 */
public class ManVenderConfigFacadeTest extends JUnit4SpringContextTests {

    @Autowired
    private ManVenderConfigFacade manVenderConfigFacade;

    @Test
    public void testInvoke(){
        SoaRequest<VenderConfigFormDTO> request = new SoaRequest<>();
        VenderConfigFormDTO dto = new VenderConfigFormDTO();
        dto.setId(23L);
        dto.setBusinessCode("101");
        dto.setKey("SCHEDULE_MODEL");
        dto.setValue("sku");
        dto.setVenderId(-1L);
        request.setData(dto);
        manVenderConfigFacade.invoke(request, InvokeMethod.INVOKE_UPDATE);
    }
}
