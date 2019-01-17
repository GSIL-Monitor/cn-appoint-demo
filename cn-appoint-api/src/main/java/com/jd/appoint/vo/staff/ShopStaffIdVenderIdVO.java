package com.jd.appoint.vo.staff;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ShopStaffIdVenderIdVO implements Serializable {

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
}
