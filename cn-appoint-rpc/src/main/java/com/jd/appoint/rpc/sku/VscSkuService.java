package com.jd.appoint.rpc.sku;

import com.jd.vsc.soa.domain.RequestWrap;
import com.jd.vsc.soa.domain.ResponseWrap;
import com.jd.vsc.soa.domain.bizconfig.SkuResult;
import com.jd.vsc.soa.service.VscBizConfigSoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by yangyuan on 6/20/18.
 */
@Service
public class VscSkuService {


    @Autowired
    private VscBizConfigSoaService vscBizConfigSoaService;

    public List<SkuResult> getSkuList(RequestWrap requestWrap) {
        return new ListSkuCommand(requestWrap,vscBizConfigSoaService).execute();
    }


}
