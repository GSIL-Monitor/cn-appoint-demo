package com.jd.appoint.service.card;

import com.google.common.collect.Lists;
import com.jd.appoint.domain.card.AppointCardPO;
import com.jd.appoint.domain.enums.CheckCardResultEnum;
import com.jd.appoint.rpc.card.dto.*;
import com.jd.appoint.service.card.dto.SkuRespDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.List;

/**
 * TODO  需要测试环境测试VSC
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/26 13:01
 */
public class TestCardService extends JUnit4SpringContextTests {
    @Autowired
    private CardService cardService;

    private CardDTO cardDTO;

    private LockCardDTO lockCardDTO;

    private UnLockCardDTO unLockCardDTO;

    private VerifyCards verifyCards;

    private static final Long verderId=20160613L;
    private static final String businessCode="101";

    @Before
    public void init() {
        //卡密 Tested
        cardDTO = new CardDTO();
        cardDTO.setCardPwd("513255");
        cardDTO.setCardNumber("109621519000");
        cardDTO.setVenderId(verderId);
        cardDTO.setBusinessCode(businessCode);

        //锁定卡密 ToTest
        lockCardDTO = new LockCardDTO();


        //解锁卡密 ToTest
        unLockCardDTO = new UnLockCardDTO();
        unLockCardDTO.setVenderId(verderId);
        unLockCardDTO.setBusinessCode(businessCode);

        //卡密核销 ToTest
        verifyCards = new VerifyCards();
    }

    @Test
    //@Ignore
    public void TestcheckCard() {
        CheckCardResultEnum checkCard = cardService.checkCard(cardDTO);
        Assert.assertEquals(Boolean.TRUE, CheckCardResultEnum.SUCCESS == checkCard);
    }


    @Test
    @Ignore
    public void TestgetCardSkuInfos() {
        SkuRespDto skuRespDto = cardService.getCardSkuInfos(cardDTO);
        Assert.assertNotNull(skuRespDto.getSkuName());
    }


    @Test
    public void TestlockCard() {
        lockCardDTO.setVenderId(verderId);
        lockCardDTO.setBusinessCode(businessCode);
        List<CardDTO> cardDTOS=Lists.newArrayList();
        cardDTO.setCardNumber("109874473200");
        cardDTO.setCardPwd("216849");
        cardDTOS.add(cardDTO);
        lockCardDTO.setLockCards(cardDTOS);
        Boolean lockCard = cardService.lockCard(lockCardDTO);
        Assert.assertEquals(Boolean.TRUE, lockCard);
    }

    @Test
    @Ignore
    public void TestunLockCard() {
        unLockCardDTO.setBusinessCode(businessCode);
        unLockCardDTO.setVenderId(verderId);
        List<CardDTO> cardDTOS=Lists.newArrayList();
        cardDTO.setCardNumber("108699128000");
        cardDTO.setCardPwd("232521");
        cardDTOS.add(cardDTO);
        unLockCardDTO.setUnlockCards(cardDTOS);
        unLockCardDTO.setTransactionId("");
        Boolean lockCard = cardService.unLockCard(unLockCardDTO);
        Assert.assertEquals(Boolean.TRUE, lockCard);
    }

    @Test
    public void TestwriteOffCard() {
        List<VerifyCardUnit> verifyCardUnits =Lists.newArrayList();
        VerifyCardUnit verifyCardUnit=new VerifyCardUnit();
        List<CardDTO> cardDTOS=Lists.newArrayList();
        cardDTO.setCardNumber("109621519000");
        cardDTO.setCardPwd("513255");
        cardDTOS.add(cardDTO);
        verifyCardUnit.setVerifyCards(cardDTOS);
        verifyCardUnit.setVerifyTimes(1);
        verifyCardUnit.setTransactionId("357");
        verifyCardUnits.add(verifyCardUnit);
        verifyCards.setVerifyCards(verifyCardUnits);
        verifyCards.setBusinessCode(businessCode);
        verifyCards.setVenderId(verderId);
        Boolean writeOffRespDto = cardService.writeOffCard(verifyCards);
        Assert.assertEquals(true, writeOffRespDto);
    }

    @Test
    public void TestinputCard() {
        List<AppointCardPO> appointCardPos = Lists.newArrayList();
        int count = cardService.inputCard(appointCardPos);
        Assert.assertEquals(1, count);
    }


}
