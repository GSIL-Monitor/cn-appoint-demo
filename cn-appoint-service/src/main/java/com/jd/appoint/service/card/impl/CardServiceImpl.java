package com.jd.appoint.service.card.impl;

import com.jd.appoint.dao.card.AppointCardDao;
import com.jd.appoint.domain.card.AppointCardPO;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.dto.LockCardDTO;
import com.jd.appoint.rpc.card.dto.UnLockCardDTO;
import com.jd.appoint.rpc.card.dto.VerifyCards;
import com.jd.appoint.service.card.CardService;
import com.jd.appoint.service.card.dto.SkuRespDto;
import com.jd.appoint.domain.enums.CheckCardResultEnum;
import com.jd.appoint.service.card.operate.CardOperateService;
import com.jd.appoint.service.card.rount.CardSourceEnum;
import com.jd.appoint.service.sms.SmsPointEnum;
import com.jd.appoint.service.sms.templets.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作卡密相关的接口
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/14 17:40
 */
@Service
public class CardServiceImpl implements CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);
    @Resource
    private CardOperateService cardOperateService;
    @Resource
    private AppointCardDao appointCardDao;
    @Resource
    private SendMessageService sendMessageService;

    /**
     * 目前远程只会调用VSC
     *
     * @param cardDTO
     * @return
     */
    @Override
    public CheckCardResultEnum checkCard(CardDTO cardDTO) {
        return cardOperateService.execute(cardDTO, CardSourceEnum.CHECK_CARD);
    }

    @Override
    public SkuRespDto getCardSkuInfos(CardDTO cardDTO) {
        return cardOperateService.execute(cardDTO, CardSourceEnum.GET_CARD_SKU);
    }

    @Override
    public Boolean lockCard(LockCardDTO lockCardDTO) {
        return cardOperateService.execute(lockCardDTO, CardSourceEnum.CARD_LOCK);
    }

    @Override
    public Boolean unLockCard(UnLockCardDTO unLockCardDTO) {
        return cardOperateService.execute(unLockCardDTO, CardSourceEnum.CARD_UNLOCK);
    }

    @Override
    public Boolean writeOffCard(VerifyCards verifyCards) {
        return cardOperateService.execute(verifyCards, CardSourceEnum.WRITEOFF_CARD);
    }

    @Override
    public int inputCard(List<AppointCardPO> appointCardPos) {
        int count = 0;
        logger.info("开始导入卡密");
        for (AppointCardPO appointCardPO : appointCardPos) {
            appointCardDao.insert(appointCardPO);
            //发送短信
            sendMessageService.sendMsg(appointCardPO.getId(), SmsPointEnum.INPUT_CARD);
            count++;
        }
        return count;
    }


}
