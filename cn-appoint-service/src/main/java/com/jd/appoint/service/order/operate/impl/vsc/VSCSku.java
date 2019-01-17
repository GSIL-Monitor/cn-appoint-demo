package com.jd.appoint.service.order.operate.impl.vsc;

import com.jd.appoint.dao.order.AppointOrderDao;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.service.card.CardService;
import com.jd.appoint.service.card.dto.SkuRespDto;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/7/5.
 */
@Service("vscSku")
public class VSCSku implements BaseOperate {
    private Logger logger = LoggerFactory.getLogger(VSCSku.class);
    @Autowired
    private CardService cardService;
    @Autowired
    private AppointOrderDao appointOrderDao;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(detailVO.getCardNo());
        cardDTO.setCardPwd(detailVO.getCardPassword());
        cardDTO.setVenderId(detailVO.getVenderId());
        cardDTO.setBusinessCode(detailVO.getBusinessCode());
        SkuRespDto sku = cardService.getCardSkuInfos(cardDTO);
        if (sku == null || sku.getSkuId() == null) {
            logger.error("订单号:{}获取不到sku信息，无法成功提交订单", detailVO.getId());
            return OperateResultEnum.RETRY;
        }
        AppointOrderPO orderPO = appointOrderDao.findById(detailVO.getId());
        orderPO.setSkuId(sku.getSkuId());
        orderPO.setSkuName(sku.getSkuName());
        appointOrderDao.updateSelective(orderPO);
        detailVO.setSkuId(sku.getSkuId());
        detailVO.setSkuName(sku.getSkuName());
        return OperateResultEnum.SUCCESS;
    }
}
