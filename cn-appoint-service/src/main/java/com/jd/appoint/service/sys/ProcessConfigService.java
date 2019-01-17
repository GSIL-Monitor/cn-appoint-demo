package com.jd.appoint.service.sys;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.api.vo.ProcessConfigRequest;
import com.jd.appoint.api.vo.ProcessConfigVO;
import com.jd.appoint.service.config.ConfigData;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/15.
 */
public interface ProcessConfigService {
    ProcessConfigVO getProcessConfig(String businessCode, String pageNo, long venderId);
}
