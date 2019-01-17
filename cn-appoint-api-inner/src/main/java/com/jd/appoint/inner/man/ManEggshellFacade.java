package com.jd.appoint.inner.man;

/**
 * 执行彩蛋
 */
public interface ManEggshellFacade {

    /**
     * 查询数量
     *
     * @param sql
     * @return
     */
    Integer excuteSelect(String sql);

    /**
     * 执行操作
     *
     * @param sql
     */
    Integer excuteUpdate(String sql, String userPin);
}
