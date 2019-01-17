package com.jd.appoint.service.card.operate;

/**
 * 对应执行卡密的操作
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/25 16:58
 */
public interface CardExcecuteOperate<T, S> {
    /**
     * 执行的结果
     *
     * @param s
     * @return
     */
    T execute(S s);
}
