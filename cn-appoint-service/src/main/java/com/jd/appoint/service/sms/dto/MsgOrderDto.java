package com.jd.appoint.service.sms.dto;

/**
 * 常量值是所有字段的数据
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/20 11:24
 */
public class MsgOrderDto extends AbstractMsgDto {
    /**
     * 预约单号
     */
    public static final String APPOINT_ORDER_ID = "appointOrderId";
    /**
     * 寄出时间
     */
    public static final String SEND_TIME = "sendTime";
    /**
     * 门店商家名称
     */
    public static final String STORE_NAME = "storeName";
    /**
     * 店铺商家名称 用于发送短信的尾处
     */
    public static final String SHOP_NAME = "shopName";
    /**
     * 商家名称
     */
    public static final String STORE_ADDRESS = "storeAddress";
    /**
     * 服务电话
     */
    public static final String SERVER_PHONE = "serverPhone";
    /**
     * 物流公司名称
     */
    public static final String LSN_NAME = "lsnName";
    /**
     * 物流单号
     */
    public static final String LSN = "lsn";
    /**
     * 订单号
     */
    public static final String ORDER_ID = "orderId";
    /**
     * 预约有效期开始时间
     */
    public static final String EFFECT_TIME = "effectTime";
    /**
     * 预约有效期截止时间
     */
    public static final String END_TIME = "endTime";
    /**
     * 预约电子码
     */
    public static final String CARD_NUMBER = "cardNumber";
    /**
     * 预约电子码卡密
     */
    public static final String CARD_PWD = "cardPwd";
    /**
     * 商品名称
     */
    public static final String SKU_NAME = "skuName";
}
