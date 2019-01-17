package com.jd.appoint.service.card.executes.vsc;

import com.jd.appoint.rpc.card.dto.LockCardDTO;
import com.jd.appoint.rpc.card.vsc.RpcVscCardService;
import com.jd.appoint.service.card.CardConfigConstants;
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
@Service(CardConfigConstants.VSC_LOCK_EXECUTOR)
public class VscLockCardExecutor implements CardExcecuteOperate<Boolean, LockCardDTO> {
    @Resource
    private RpcVscCardService rpcVscCardService;

    @Override
    public Boolean execute(LockCardDTO lockCardDTO) {
        //VSC卡密信息
        SoaRequest<LockCardDTO> request = new SoaRequest<>();
        request.setData(lockCardDTO);
        SoaResponse<Boolean> soaResponse = rpcVscCardService.lockCard(request);
        if (500 == soaResponse.getCode()) {
            throw new RuntimeException("rpcVscCardService.lockCard 异常");
        }
        return null != soaResponse.getResult() ? soaResponse.getResult() : false;
    }
}
