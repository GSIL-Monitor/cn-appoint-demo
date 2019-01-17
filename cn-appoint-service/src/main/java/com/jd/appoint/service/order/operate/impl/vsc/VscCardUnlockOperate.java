package com.jd.appoint.service.order.operate.impl.vsc;

import com.google.common.collect.Lists;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.dto.UnLockCardDTO;
import com.jd.appoint.rpc.card.vsc.RpcVscCardService;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * VSC卡密解锁
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/19 20:41
 */
@Service(value = "vscCardUnlockOperate")
public class VscCardUnlockOperate implements BaseOperate {
    @Resource(name = "rpcVscCardService")
    private RpcVscCardService rpcVscCardService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        SoaRequest<UnLockCardDTO> request = new SoaRequest<>();
        UnLockCardDTO unLockCardDTO = new UnLockCardDTO();
        unLockCardDTO.setTransactionId(detailVO.getId() + "");
        unLockCardDTO.setBusinessCode(detailVO.getBusinessCode());
        unLockCardDTO.setVenderId(detailVO.getVenderId());
        List<CardDTO> cardDTOList = Lists.newArrayList();
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(detailVO.getCardNo());
        cardDTO.setCardPwd(detailVO.getCardPassword());
        cardDTOList.add(cardDTO);
        unLockCardDTO.setUnlockCards(cardDTOList);
        request.setData(unLockCardDTO);
        //执行卡密解锁
        SoaResponse<Boolean> soaResponse = rpcVscCardService.unLockCard(request);
        if (soaResponse.isSuccess()) {
            return soaResponse.getResult() ? OperateResultEnum.SUCCESS : OperateResultEnum.FAIL;
        }
        return OperateResultEnum.RETRY;
    }
}
