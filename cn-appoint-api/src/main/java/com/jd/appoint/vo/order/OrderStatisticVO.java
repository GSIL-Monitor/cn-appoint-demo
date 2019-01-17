package com.jd.appoint.vo.order;

/**
 * Created by yangyuan on 5/24/18.
 */
public class OrderStatisticVO {
    /**
     * 预约数
     */
    private int appointed;

    /**
     * 待服务数
     */
    private int forService;

    /**
     * 待派单数
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
