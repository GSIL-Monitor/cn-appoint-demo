package com.jd.appoint.vo.staff;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StaffIdVenderIdStoreIdVO implements Serializable {

    /**
     * 服务人员ID
     */
    @NotNull
    private Long id;
    /**
     * 商家ID
     */
    @NotNull
    private Long venderId;

    /**
     * 门店ID
     */
    @NotNull
    private Long storeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
