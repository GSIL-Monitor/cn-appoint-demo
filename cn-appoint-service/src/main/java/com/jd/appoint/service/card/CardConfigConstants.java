package com.jd.appoint.service.card;

/**
 * 该数据需要配置到card的相关路由表中
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/25 17:25
 */
public class CardConfigConstants {

    /* ===========================vsc start=================*/
    /**
     * vsc卡密校验的实现
     */
    public static final String VSC_CHECK_CARD_EXECUTOR = "vscCheckCardExecutor";
    /**
     * vsc获得sku信息的实现
     */
    public static final String VSC_GET_SKUINFO_EXECUTOR = "vscGetSkuInfoExecutor";
    /**
     * vsc锁定卡密
     */
    public static final String VSC_LOCK_EXECUTOR = "vscLockCardExecutor";
    /**
     * vsc解锁卡密
     */
    public static final String VSC_UN_LOCK_EXECUTOR = "vscUnLockCardExecutor";
    /**
     * vsc核销卡密数据
     */
    public static final String VSC_WRITEOFF_CARD_EXECUTOR = "vscWriteOffCardExecutor";


}
