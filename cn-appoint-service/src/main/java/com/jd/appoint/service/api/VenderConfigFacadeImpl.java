package com.jd.appoint.service.api;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.jd.appoint.api.VenderConfigFacade;
import com.jd.appoint.api.vo.*;
import com.jd.appoint.common.enums.SoaCodeEnum;
import com.jd.appoint.common.utils.Copier;
import com.jd.appoint.domain.shop.ShopBusinessPO;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.sys.VenderConfigPO;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.ControlItemType;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.sun.tools.javac.util.Convert;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对应路由配置的信息
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/15 17:45
 */
@Service("venderConfigFacade")
public class VenderConfigFacadeImpl implements VenderConfigFacade {
    private static Logger logger = LoggerFactory.getLogger(VenderConfigFacadeImpl.class);
    @Resource
    private VenderConfigService venderConfigService;

    @Override
    @UmpMonitor(logCollector =
    @LogCollector(description = "获得动态路由接口", classify = VenderConfigFacadeImpl.class))
    public SoaResponse<VenderConfigVO> getVenderConfig(@ValideGroup SoaRequest<QueryConfigVO> request) {
        QueryConfigVO queryConfigVO = request.getData();
        VenderConfigPO venderConfigPO = new VenderConfigPO();
        venderConfigPO.setCfgKey(queryConfigVO.getKey());
        venderConfigPO.setBusinessCode(queryConfigVO.getBusinessCode());
        venderConfigPO.setVenderId(queryConfigVO.getVenderId());
        //转换数据
        VenderConfigVO venderConfigVO = venderConfigService.getVenderConfig(venderConfigPO);
        if (null != venderConfigVO) {
            return new SoaResponse(venderConfigVO);
        }
        logger.error("获得动态路由接口调用未找到数据：检查请求的配置是否已经配置");
        return new SoaResponse(SoaCodeEnum.CONFIG_NOT_FOUND);
    }

}
