package com.jd.appoint.service.card.executes.vsc;

import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.vsc.RpcVscCardService;
import com.jd.appoint.service.card.CardConfigConstants;
import com.jd.appoint.domain.enums.CheckCardResultEnum;
import com.jd.appoint.service.card.operate.CardExcecuteOperate;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 校验VSC的卡密信息
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/25 17:24
 */
@Service(CardConfigConstants.VSC_CHECK_CARD_EXECUTOR)
public class VscCheckCardExecutor implements CardExcecuteOperate<CheckCardResultEnum, CardDTO> {
    @Resource
    private RpcVscCardService rpcVscCardService;

    @Override
    public CheckCardResultEnum execute(CardDTO cardDTO) {
        //VSC卡密信息
        SoaRequest<CardDTO> request = new SoaRequest<>();
        request.setData(cardDTO);
        SoaResponse<CheckCardResultEnum> soaResponse = rpcVscCardService.checkCard(request);
        if (500 == soaResponse.getCode()) {
            throw new RuntimeException("rpcVscCardService.checkCard 异常");
        }
        return null != soaResponse.getResult() ? soaResponse.getResult() : CheckCardResultEnum.FAIL;
    }
}
