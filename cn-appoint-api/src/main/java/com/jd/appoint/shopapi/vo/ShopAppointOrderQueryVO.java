package com.jd.appoint.shopapi.vo;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/3.
 * 订单信息查询
 */
public class ShopAppointOrderQueryVO extends CommonRequest implements Serializable {

    /**
     * 商家id
     */
    @NotNull
    private Long venderId;
    /**
     * 预约单号
     */
    @NotNull
    private Long appointOrderId;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    //get set
    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

}
