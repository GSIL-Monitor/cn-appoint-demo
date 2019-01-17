package com.jd.appoint.shopapi.vo;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 导入物流单信息数据
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/21 9:57
 */
public class LsnInputVO implements Serializable {
    /**
     * 商家Id
     */
    @NotNull
    private Long venderId;

    @Valid
    @NotNull
    private List<LsnVO> lsnVos;

    private String operateUser;

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public List<LsnVO> getLsnVos() {
        return lsnVos;
    }

    public void setLsnVos(List<LsnVO> lsnVos) {
        this.lsnVos = lsnVos;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }
}
