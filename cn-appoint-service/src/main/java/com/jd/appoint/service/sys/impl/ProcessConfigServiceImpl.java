package com.jd.appoint.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.api.vo.ProcessConfigRequest;
import com.jd.appoint.api.vo.ProcessConfigVO;
import com.jd.appoint.service.config.ConfigData;
import com.jd.appoint.service.sys.ProcessConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/15.
 */
@Service
public class ProcessConfigServiceImpl implements ProcessConfigService {

    private static final String CONFIG_PREFIX = "PROCESS_CONFIG_";


    /**
     * 获取流程配置
     *
     * @param businessCode
     * @param pageNo
     * @param venderId
     * @return
     */
    public ProcessConfigVO getProcessConfig(String businessCode, String pageNo, long venderId) {
        String configStr = getConfigStr(businessCode);
        if (configStr == null) {
            return null;
        }
        List<ProcessConfigVO> processConfigVOList = JSON.parseArray(configStr, ProcessConfigVO.class);
        int index = 0;
        ProcessConfigVO config = null;
        for (ProcessConfigVO configVO : processConfigVOList) {
            //找到当前页，
            if (pageNo == null || configVO.getCurrentPageNo().equals(pageNo)) {
                config = configVO;
                if (processConfigVOList.size() > index + 1) {
                    config.setNextPageNo(processConfigVOList.get(index + 1).getCurrentPageNo());
                }
                break;
            }
            index++;
        }
        if (config == null) {
            throw new IllegalArgumentException("页码参数校验失败，当前pageNo=" + pageNo);
        }
        //链接中加入business_code
        if (config.getFinishButtons() != null) {
            config.getFinishButtons().forEach(finishButton -> finishButton.setUrl(finishButton.getUrl() + businessCode + "?venderId=" + venderId));
        }
        return config;
    }

    private String getConfigStr(String businessCode) {
        return ConfigData.getConfigValue(CONFIG_PREFIX + businessCode);
    }
}
