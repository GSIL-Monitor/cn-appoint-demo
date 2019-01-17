package com.jd.appoint.service.api;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.jd.appoint.api.AppointCardFacade;
import com.jd.appoint.api.vo.CardVO;
import com.jd.appoint.api.vo.InputCartVO;
import com.jd.appoint.domain.card.AppointCardPO;
import com.jd.appoint.service.card.CardService;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;

/**
 * 卡密相关的操作API
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/15 17:45
 */
@Service
public class AppointCardFacadeImpl implements AppointCardFacade {
    private static final Logger logger = LoggerFactory.getLogger(AppointCardFacadeImpl.class);

    @Resource
    private CardService cardService;

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA,
            logCollector = @LogCollector(description = "导入卡密信息", classify = AppointCardFacade.class))
    public SoaResponse<Boolean> batchInputCards(@ValideGroup SoaRequest<InputCartVO> soaRequest) {
        InputCartVO inputCartVO = soaRequest.getData();
        //TODO 验证加密的密码签名是否正常
        List<CardVO> cardVOList = inputCartVO.getCardVOList();
        List<AppointCardPO> appointCardPOs = Lists.transform(cardVOList, new Function<CardVO, AppointCardPO>() {
            @Nullable
            @Override
            public AppointCardPO apply(@Nullable CardVO input) {
                AppointCardPO appointCardPO = new AppointCardPO();
                BeanUtils.copyProperties(input, appointCardPO);
                return appointCardPO;
            }
        });
        int count = cardService.inputCard(appointCardPOs);
        logger.info("导入卡密信息条数count={}", count);
        return new SoaResponse<>();
    }
}
