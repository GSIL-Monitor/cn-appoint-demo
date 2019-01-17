package com.jd.appoint.service.order.operate.finish;

import com.google.common.collect.Lists;
import com.jd.appoint.dao.order.AppointOrderDao;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.dto.VerifyCardUnit;
import com.jd.appoint.rpc.card.dto.VerifyCards;
import com.jd.appoint.service.card.CardService;
import com.jd.appoint.service.order.AppointOrderServiceItemService;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;

/**
 * 大闸蟹业务相关的代码操作
 * 【大闸蟹】
 *
 * @author lijizhen1@jd.com
 * @date 2018/7/9 18:11
 */
@Service(value = "finishDzxAppointExcutor")
public class FinishDzxAppointExcutor extends AbstractFinishAppointExcutor {
    private static final Logger logger = LoggerFactory.getLogger(FinishDzxAppointExcutor.class);
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Resource
    private AppointOrderServiceItemService appointOrderServiceItemService;
    @Resource
    private CardService cardService;
    @Resource
    private AppointOrderDao appointOrderDao;


    /**
     * 执行大闸蟹的完成服务
     *
     * @param finishAppointDto
     * @return
     */
    @Override
    public OperateResultEnum excute(FinishAppointDto finishAppointDto) {
        //获得需要持久到数据库的参数
        AppointOrderPO appointOrderPO = finishAppointDto.getAppointOrderPO();
        //绑定物流信息 放在完成之前，预约完成不需要走核销以及别的操作，不然调用核销接口后会导致不执行
        AppointOrderDetailVO mailInfo = bindMailInformation(finishAppointDto);

        //防止重复发短信
        if (AppointStatusEnum.APPOINT_FINISH.equals(appointOrderPO.getAppointStatus())) {
            //导入物流信息
            inputLsns(mailInfo);
            return OperateResultEnum.SUCCESS;
        }
        //如果不是待服务状态不能执行
        if (!AppointStatusEnum.WAIT_SERVICE.equals(appointOrderPO.getAppointStatus())) {
            logger.error("执行预约完成的时候执行失败appointOrderId={}，节点前置状态不能为appointStatus={}", appointOrderPO.getId(), appointOrderPO.getAppointStatus());
            throw new RuntimeException("当前节点状态不能处理为预约完成");
        }
        /**
         * 有相关的操作
         * 1.有物流单号的需要补充物流信息【大闸蟹】
         * 2.需要的实物核销库存 【大闸蟹】
         * 3.有附件的上传附件【大闸蟹】
         */
        //@2:调用vsc
        Boolean writeOffRespDto = cardService.writeOffCard(genVerifyCards(finishAppointDto.getAppointOrderPO().getId()));
        TransactionStatus transaction = null;
        //如果核销成功修改状态
        if (writeOffRespDto) {
            try {
                transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
                //生成调用成功设置为成功
                appointOrderPO.setAppointStatus(AppointStatusEnum.APPOINT_FINISH);
                //@3.有附件的上传附件【大闸蟹】 修改预约单的状态
                if (appointOrderDao.finishAppoint(appointOrderPO, finishAppointDto.getOverwrite(), FINISH_APPOINT_BEFORE_STATUS.get()) < 1) {
                    throw new RuntimeException("完成预约失败");
                }
                //导入物流信息
                inputLsns(mailInfo);
                //发送消息
                sendMq(appointOrderPO.getId());
                //写入埋点数据
                footPrint(appointOrderPO);
                transactionManager.commit(transaction);
                return OperateResultEnum.SUCCESS;
            } catch (RuntimeException e) {
                logger.error("执行完成大闸蟹预约失败：e={}", e);
                transactionManager.rollback(transaction);
                return OperateResultEnum.FAIL;
            }
        } else {
            logger.error("执行完成大闸蟹业务预约卡密核销失败：writeOffRespDto={}", writeOffRespDto);
            return OperateResultEnum.FAIL;
        }
    }

    /**
     * 绑定物流信息 放在完成后操作，
     * 1.首次预约完成只修改预约单
     * 2.多次执行预约完成只修改预约单号
     *
     * @param mailInfo
     */
    private void inputLsns(AppointOrderDetailVO mailInfo) {
        if (null == mailInfo) {
            return;
        }
        //@1:有物流单的添加物流信息
        //导入物流单
        appointOrderServiceItemService.inputLsns(mailInfo);
    }

    /**
     * 生成对应的卡密核销的参数
     *
     * @return
     */
    private VerifyCards genVerifyCards(Long appointOrderId) {
        AppointOrderDetailVO appointOrderDetailVO = getAppointOrderVo(appointOrderId);
        List<VerifyCardUnit> verifyCardUnits = Lists.newArrayList();
        //卡密信息
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(appointOrderDetailVO.getCardNo());
        cardDTO.setCardPwd(appointOrderDetailVO.getCardPassword());

        //添加到卡密列表
        List<CardDTO> cardDTOS = Lists.newArrayList();
        cardDTOS.add(cardDTO);
        //核销卡密单元
        VerifyCardUnit verifyCardUnit = new VerifyCardUnit();
        verifyCardUnit.setVerifyCards(cardDTOS);
        verifyCardUnit.setVerifyTimes(1);
        verifyCardUnit.setTransactionId(appointOrderId + "");
        verifyCardUnits.add(verifyCardUnit);
        //核销参数
        VerifyCards verifyCards = new VerifyCards();
        verifyCards.setVerifyCards(verifyCardUnits);
        verifyCards.setBusinessCode(appointOrderDetailVO.getBusinessCode());
        verifyCards.setVenderId(appointOrderDetailVO.getVenderId());
        return verifyCards;
    }


    /**
     * 绑定物流信息
     *
     * @param finishAppointDto
     */
    private AppointOrderDetailVO bindMailInformation(FinishAppointDto finishAppointDto) {
        if (null == finishAppointDto.getMailInformation()) {
            return null;
        }
        AppointOrderDetailVO appointOrderDetailVO = new AppointOrderDetailVO();
        appointOrderDetailVO.setLogisticsNo(finishAppointDto.getMailInformation().getLogisticsNo());
        appointOrderDetailVO.setLogisticsSource(finishAppointDto.getMailInformation().getLogisticsSource());
        appointOrderDetailVO.setLogisticsSiteId(finishAppointDto.getMailInformation().getLogisticsSiteId());
        appointOrderDetailVO.setId(finishAppointDto.getAppointOrderPO().getId());
        return appointOrderDetailVO;
    }
}
