package com.jd.appoint.service.card;

import com.jd.appoint.domain.card.AppointCardPO;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.dto.LockCardDTO;
import com.jd.appoint.rpc.card.dto.UnLockCardDTO;
import com.jd.appoint.rpc.card.dto.VerifyCards;
import com.jd.appoint.service.card.dto.SkuRespDto;
import com.jd.appoint.domain.enums.CheckCardResultEnum;

import java.util.List;

/**
 * 卡密的操作
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/14 17:12
 */
public interface CardService {
    /**
     * 校验卡密
     *
     * @return
     */
    CheckCardResultEnum checkCard(CardDTO cardDTO);

    /**
     * 通过卡密信息获得sku的信息
     * 如果接口返回的数据为空是服务端异常引起
     *
     * @param cardDTO
     */
    SkuRespDto getCardSkuInfos(CardDTO cardDTO);

    /**
     * 锁定卡密
     *
     * @return
     */
    Boolean lockCard(LockCardDTO lockCardDTO);

    /**
     * 解除锁定
     *
     * @return
     */
    Boolean unLockCard(UnLockCardDTO unLockCardDTO);

    /**
     * 卡密核销
     *
     * @return
     */
    Boolean writeOffCard(VerifyCards verifyCards);

    /**
     * 导入卡密信息
     *
     * @param appointCardPos
     */
    int inputCard(List<AppointCardPO> appointCardPos);

}
