package com.jd.appoint.service.man;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.jd.appoint.domain.sys.VenderConfigPO;
import com.jd.appoint.inner.man.ManVenderConfigFacade;
import com.jd.appoint.inner.man.dto.InvokeMethod;
import com.jd.appoint.inner.man.dto.VenderConfigDTO;
import com.jd.appoint.inner.man.dto.VenderConfigFormDTO;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 18:13
 */
@Service
public class ManVenderConfigFacadeImpl implements ManVenderConfigFacade {
    private static Logger logger = LoggerFactory.getLogger(ManVenderConfigFacadeImpl.class);
    @Resource
    private VenderConfigService venderConfigService;


    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "man端配置查询", classify = ManVenderConfigFacadeImpl.class))
    public SoaResponse<List<VenderConfigDTO>> getVenderConfig(SoaRequest<VenderConfigFormDTO> request) {
        VenderConfigFormDTO venderConfigFormDTO = request.getData();
        List<VenderConfigPO> venderConfigPOs =
                venderConfigService.findAll(venderConfigFormDTO.getSearchMaps());
        List<VenderConfigDTO> list = Lists.transform(venderConfigPOs, new Function<VenderConfigPO, VenderConfigDTO>() {
            @Nullable
            @Override
            public VenderConfigDTO apply(@Nullable VenderConfigPO venderConfigPO) {
                VenderConfigDTO venderConfigDTO = new VenderConfigDTO();
                venderConfigDTO.setBusinessCode(venderConfigPO.getBusinessCode());
                venderConfigDTO.setValue(venderConfigPO.getValue());
                venderConfigDTO.setVenderId(venderConfigPO.getVenderId());
                venderConfigDTO.setCfgKey(venderConfigPO.getCfgKey());
                venderConfigDTO.setId(venderConfigPO.getId());
                return venderConfigDTO;
            }
        });
        return new SoaResponse<>(list);
    }

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "man端操作对应的配置操作", classify = ManVenderConfigFacadeImpl.class))
    public SoaResponse invoke(@ValideGroup SoaRequest<VenderConfigFormDTO> request, String method) {
        VenderConfigFormDTO venderConfigDTO = request.getData();
        VenderConfigPO venderConfigPO = new VenderConfigPO();
        venderConfigPO.setVenderId(venderConfigDTO.getVenderId());
        venderConfigPO.setBusinessCode(venderConfigDTO.getBusinessCode());
        venderConfigPO.setCfgKey(venderConfigDTO.getKey());
        venderConfigPO.setValue(venderConfigDTO.getValue());
        SoaResponse soaResponse = null;
        try {
            switch (method) {
                case InvokeMethod.INVOKE_UPDATE:
                    if (null == venderConfigDTO.getId()) {
                        soaResponse = new SoaResponse(SoaError.PARAMS_EXCEPTION, "更新参数必须传递配置的ID");
                        break;
                    }
                    venderConfigPO.setId(venderConfigDTO.getId());
                    venderConfigService.updateConfig(venderConfigPO);
                    soaResponse = new SoaResponse();
                    break;
                case InvokeMethod.INVOKE_ADD:
                    venderConfigService.insertConfig(venderConfigPO);
                    soaResponse = new SoaResponse();
                    break;
                case InvokeMethod.INVOKE_SELECT:
                    VenderConfigPO venderConfigPO1 = venderConfigService.findVenderConfigByID(venderConfigDTO.getId());
                    if (null != venderConfigPO1) {
                        VenderConfigFormDTO vcDto = new VenderConfigFormDTO();
                        vcDto.setBusinessCode(venderConfigPO1.getBusinessCode());
                        vcDto.setVenderId(venderConfigPO1.getVenderId());
                        vcDto.setId(venderConfigPO1.getId());
                        vcDto.setValue(venderConfigPO1.getValue());
                        vcDto.setKey(venderConfigPO1.getCfgKey());
                        return new SoaResponse(vcDto);
                    }
                    soaResponse = new SoaResponse(SoaError.RETRY_EXCEPTION, "没有找到对应的数据");
                    break;
                default:
                    logger.warn("请检查是否支持对应的操作");
                    break;
            }
        } catch (Exception e) {
            logger.warn("执行配置更新时候出现异常，具体的异常信息为e={}", e);
        }
        return null != soaResponse ? soaResponse : new SoaResponse(SoaError.RETRY_EXCEPTION);
    }

}
