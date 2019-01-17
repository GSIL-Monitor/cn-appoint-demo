package com.jd.appoint.service.sms.templets;

import com.google.common.collect.Maps;
import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.config.BusinessVenderMapPO;
import com.jd.appoint.service.config.BusinessVenderMapService;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.sms.AbstractSmsTemplet;
import com.jd.appoint.service.sms.SmsPointEnum;
import com.jd.appoint.service.sms.dto.AbstractMsgDto;
import com.jd.appoint.service.sms.dto.MsgOrderDto;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 预约完成节点发送短信模板配置
 * http://sms.jd.com/integration/newSmsTemplateList.html?sid=1132
 * @author lijizhen1@jd.com
 * @date 2018/6/20 12:26
 */
@Service
public class SendMessageService extends AbstractSmsTemplet {
    private static final Logger logger = LoggerFactory.getLogger(SendMessageService.class);
    @Resource
    private AppointOrderService appointOrderService;
    @Resource
    private BusinessVenderMapService businessVenderMapService;

    /**
     * 这里可以不需要写
     *
     * @param appointOrderDetailVO
     */
    public void sendMsg(AppointOrderDetailVO appointOrderDetailVO,
                        SmsPointEnum smsPointEnum) {
        MsgOrderDto msgDto = new MsgOrderDto();
        checkNotNull(appointOrderDetailVO.getCustomerPhone());
        checkNotNull(appointOrderDetailVO.getVenderId());
        checkNotNull(appointOrderDetailVO.getBusinessCode());
        //设置信息
        msgDto.setMobileNum(appointOrderDetailVO.getCustomerPhone());
        msgDto.setBusinessCode(appointOrderDetailVO.getBusinessCode());
        msgDto.setKey(smsPointEnum.getKey());
        msgDto.setVenderId(appointOrderDetailVO.getVenderId());
        Map<String, String> msgInfoDto = null != msgDto.getMsgInfoDto() ? msgDto.getMsgInfoDto() : Maps.newHashMap();
        //添加预约单号
        msgInfoDto.put(MsgOrderDto.APPOINT_ORDER_ID, null != appointOrderDetailVO.getId() ? String.valueOf(appointOrderDetailVO.getId()) : "");
        msgInfoDto.put(MsgOrderDto.LSN, null != appointOrderDetailVO.getLogisticsNo() ? appointOrderDetailVO.getLogisticsNo() : "");
        msgInfoDto.put(MsgOrderDto.LSN_NAME, null != appointOrderDetailVO.getLogisticsSource() ? appointOrderDetailVO.getLogisticsSource() : "");
        msgInfoDto.put(MsgOrderDto.SEND_TIME, null != appointOrderDetailVO.getAppointStartTime() ? AppointDateUtils.getDate2Str("yyyy-MM-dd", appointOrderDetailVO.getAppointStartTime()) : "");
        msgInfoDto.put(MsgOrderDto.SERVER_PHONE, null != appointOrderDetailVO.getStorePhone() ? appointOrderDetailVO.getStorePhone() : "");
        msgInfoDto.put(MsgOrderDto.STORE_NAME, null != appointOrderDetailVO.getStoreName() ? appointOrderDetailVO.getStoreName() : "");
        msgInfoDto.put(MsgOrderDto.STORE_ADDRESS, null != appointOrderDetailVO.getStoreAddress() ? appointOrderDetailVO.getStoreAddress() : "");
        msgInfoDto.put(MsgOrderDto.ORDER_ID, null != appointOrderDetailVO.getErpOrderId() ? appointOrderDetailVO.getErpOrderId() + "" : "");
        msgInfoDto.put(MsgOrderDto.EFFECT_TIME, null != appointOrderDetailVO.getAppointStartTime() ? AppointDateUtils.getDate2Str("yyyy-MM-dd", appointOrderDetailVO.getAppointStartTime()) : "");
        msgInfoDto.put(MsgOrderDto.END_TIME, null != appointOrderDetailVO.getAppointEndTime() ? AppointDateUtils.getDate2Str("yyyy-MM-dd", appointOrderDetailVO.getAppointEndTime()) : "");
        msgInfoDto.put(MsgOrderDto.CARD_NUMBER, null != appointOrderDetailVO.getCardNo() ? appointOrderDetailVO.getCardNo() : "");
        msgInfoDto.put(MsgOrderDto.CARD_PWD, null != appointOrderDetailVO.getCardPassword() ? appointOrderDetailVO.getCardPassword() : "");
        msgInfoDto.put(MsgOrderDto.SKU_NAME, null != appointOrderDetailVO.getSkuName() ? appointOrderDetailVO.getSkuName() : "");
        BusinessVenderMapPO businessVenderMapPO = null;
        if (null != appointOrderDetailVO.getVenderId()) {
            businessVenderMapPO = businessVenderMapService.queryByVenderId(appointOrderDetailVO.getVenderId());
            //获得商家名称
            msgInfoDto.put(MsgOrderDto.SHOP_NAME, null != businessVenderMapPO ? businessVenderMapPO.getVenderName() : "");
        }
        msgDto.setMsgInfoDto(msgInfoDto);
        //映射对应的属性
        sendMsg(msgDto);
    }


    /**
     * 预约单号
     *
     * @param appointOrderId
     */
    public void sendMsg(Long appointOrderId,
                        SmsPointEnum smsPointEnum) {
        AppointOrderDetailVO appointOrderDetailVO
                = appointOrderService.detail(appointOrderId);
        sendMsg(appointOrderDetailVO, smsPointEnum);
    }

    /**
     * 自己来重写短信发送的内容
     *
     * @param msgDto
     */
    public void sendMsg(AbstractMsgDto msgDto,
                        SmsPointEnum smsPointEnum) {
        //设置配置的key
        msgDto.setKey(smsPointEnum.getKey());
        super.sendMsg(msgDto);
    }
}
