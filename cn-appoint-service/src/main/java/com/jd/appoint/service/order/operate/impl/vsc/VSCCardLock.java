package com.jd.appoint.service.order.operate.impl.vsc;

import com.google.common.collect.Lists;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.dto.LockCardDTO;
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
 * Created by shaohongsen on 2018/6/13.
 * VSC卡密锁定接口
 */
@Service("vscCardLock")
public class VSCCardLock implements BaseOperate {
    @Resource(name = "rpcVscCardService")
    private RpcVscCardService rpcVscCardService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        LockCardDTO lockCardDTO = new LockCardDTO();
        lockCardDTO.setTransactionId(detailVO.getId() + "");
        lockCardDTO.setBusinessCode(detailVO.getBusinessCode());
        lockCardDTO.setVenderId(detailVO.getVenderId());
        List<CardDTO> cardDTOList = Lists.newArrayList();
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(detailVO.getCardNo());
        cardDTO.setCardPwd(detailVO.getCardPassword());
        cardDTOList.add(cardDTO);
        lockCardDTO.setLockCards(cardDTOList);
        SoaResponse<Boolean> soaResponse = rpcVscCardService.lockCard(new SoaRequest<>(lockCardDTO));
        if (soaResponse.isSuccess()) {
            return soaResponse.getResult() ? OperateResultEnum.SUCCESS : OperateResultEnum.FAIL;
        }
        return OperateResultEnum.RETRY;
    }
}
