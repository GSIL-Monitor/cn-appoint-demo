package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by lishuaiwei on 2018/6/20.
 * 动态订单详情查询
 */
public class DynamicStoreAppointOrderQuery extends CommonRequest {

    /**
     * 门店Id
     */
    @NotNull
    private Long storeId;

    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;
    /**
     * 服务类型
     */
    @NotNull
    private Integer serverType;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

}
