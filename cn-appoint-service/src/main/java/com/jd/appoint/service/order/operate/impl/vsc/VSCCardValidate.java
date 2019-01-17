package com.jd.appoint.service.order.operate.impl.vsc;

import com.jd.appoint.domain.enums.CheckCardResultEnum;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.vsc.RpcVscCardService;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by shaohongsen on 2018/6/13.
 * VSC卡密校验基本操作
 */
@Service("vscCardValidate")
public class VSCCardValidate implements BaseOperate {
    @Resource(name = "rpcVscCardService")
    private RpcVscCardService rpcVscCardService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        SoaRequest<CardDTO> request = new SoaRequest<>();
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(detailVO.getCardNo());
        cardDTO.setCardPwd(detailVO.getCardPassword());
        cardDTO.setBusinessCode(detailVO.getBusinessCode());
        cardDTO.setVenderId(detailVO.getVenderId());
        request.setData(cardDTO);
        //执行卡密解锁
        SoaResponse<CheckCardResultEnum> soaResponse = rpcVscCardService.checkCard(request);
        if (soaResponse.isSuccess()) {
            return CheckCardResultEnum.SUCCESS == soaResponse.getResult() ? OperateResultEnum.SUCCESS : OperateResultEnum.FAIL;
        }
        return OperateResultEnum.RETRY;
    }
}
