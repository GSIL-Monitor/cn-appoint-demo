package com.jd.appoint.service.card.rount;

/**
 * 卡密校验 --》 卡密锁定  --》卡密解锁  --》 卡密核销
 * |             |             |              |
 * 预约单前     下单成功后     预约单取消     预约完成
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/25 16:24
 */
public enum CardSourceEnum implements ICardRouter {
    CHECK_CARD("CHECK", "校验卡密"),
    GET_CARD_SKU("SKU_INFO", "获取卡密SKU信息"),
    CARD_LOCK("LOCK", "卡密锁定"),
    CARD_UNLOCK("UNLOCK", "卡密解锁"),
    WRITEOFF_CARD("WRITEOFF_CARD", "卡密核销");
    private String rountKey;

    CardSourceEnum(String rountKey,
                   String description) {
        this.rountKey = rountKey;
    }


    @Override
    public String getOperateKey() {
        return this.rountKey;
    }
}
