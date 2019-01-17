package com.jd.appoint.vo.order;

import com.jd.appoint.vo.CommonRequest;

import javax.validation.constraints.NotNull;

/**
 * 更新附件信息
 * Created by gaoxiaoqing on 2018/6/18.
 */
public class UpdateAttachVO extends CommonRequest{

    /**
     * 预约单ID
     */
    @NotNull
    private Long appointOrderId;

    /**
     * 商家ID
     */
    @NotNull
    private Long venderId;
    /**
     * 附件信息
     */
    private AttachVO attachVO;
    /**
     * 物流信息
     */
    private LogisticVO logisticVO;

    /**
     * 登录用户Pin
     */
    @NotNull
    private String loginUserPin;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }
    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public AttachVO getAttachVO() {
        return attachVO;
    }

    public void setAttachVO(AttachVO attachVO) {
        this.attachVO = attachVO;
    }

    public LogisticVO getLogisticVO() {
        return logisticVO;
    }

    public void setLogisticVO(LogisticVO logisticVO) {
        this.logisticVO = logisticVO;
    }

    public String getLoginUserPin() {
        return loginUserPin;
    }

    public void setLoginUserPin(String loginUserPin) {
        this.loginUserPin = loginUserPin;
    }
}
