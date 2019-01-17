package com.jd.appoint.service.card.convent;

import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.service.card.dto.SkuRequestDto;

/**
 * 数据转换
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/25 15:49
 */
public class CardSkuConverter {
    public static CardDTO convert(SkuRequestDto source) {
        return doConvert(source);
    }

    private static CardDTO doConvert(SkuRequestDto source) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setBusinessCode(source.getBusinessCode());
        cardDTO.setCardNumber(source.getCardNo());
        cardDTO.setCardPwd(source.getCardPassword());
        cardDTO.setVenderId(source.getVenderId());
        return cardDTO;
    }
}
