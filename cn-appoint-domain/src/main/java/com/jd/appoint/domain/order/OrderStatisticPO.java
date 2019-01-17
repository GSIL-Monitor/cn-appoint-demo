package com.jd.appoint.domain.order;

/**
 * Created by yangyuan on 5/24/18.
 */
public class OrderStatisticPO {
    /**
     * 预约数  created  字段计数
     */
    private int appointed;

    /**
     * 待服务数  appoint_start_time 计数
     */
    private int forService;

    /**
     * 待派单数 appoint_start_time 计数
     */
    private int forDispatch;

    public int getAppointed() {
        return appointed;
    }

    public void setAppointed(int appointed) {
        this.appointed = appointed;
    }

    public int getForDispatch() {
        return forDispatch;
    }

    public void setForDispatch(int forDispatch) {
        this.forDispatch = forDispatch;
    }

    public int getForService() {
        return forService;
    }

    public void setForService(int forService) {
        this.forService = forService;
    }

    @Override
    public String toString() {
        return "OrderStatisticVO{" +
                "appointed=" + appointed +
                ", forService=" + forService +
                ", forDispatch=" + forDispatch +
                '}';
    }
}
