package com.jd.appoint.service.order.operate.impl.vsc;

import com.google.common.collect.Lists;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.dto.VerifyCardUnit;
import com.jd.appoint.rpc.card.dto.VerifyCards;
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
 * 卡密核销的实现
 *
 * @author lijianzhen1
 * @date 2018/6/14
 */
@Service("vscCardVerifyCard")
public class VSCCardVerifyCard implements BaseOperate {

    @Resource(name = "rpcVscCardService")
    private RpcVscCardService rpcVscCardService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        VerifyCards verifyCards = new VerifyCards();
        List<VerifyCardUnit> verifyCardUnitList = Lists.newArrayList();
        VerifyCardUnit verifyCardUnit = new VerifyCardUnit();
        verifyCardUnit.setMobile(detailVO.getCustomerPhone());
        verifyCardUnit.setTransactionId(detailVO.getId() + "");
        verifyCardUnit.setVerifyTimes(1);

        List<CardDTO> cardDTOList = Lists.newArrayList();
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(detailVO.getCardNo());
        cardDTO.setCardPwd(detailVO.getCardPassword());
        cardDTOList.add(cardDTO);
        verifyCardUnit.setVerifyCards(cardDTOList);
        verifyCardUnitList.add(verifyCardUnit);
        verifyCards.setVerifyCards(verifyCardUnitList);
        SoaResponse<Boolean> soaResponse = rpcVscCardService.verifyCard(new SoaRequest<>(verifyCards));
        if (soaResponse.isSuccess()) {
            return soaResponse.getResult() ? OperateResultEnum.SUCCESS : OperateResultEnum.FAIL;
        }
        return OperateResultEnum.RETRY;
    }
}
