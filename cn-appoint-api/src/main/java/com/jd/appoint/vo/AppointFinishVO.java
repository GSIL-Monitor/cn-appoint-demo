package com.jd.appoint.vo;

import com.jd.appoint.vo.order.Attach;
import com.jd.appoint.vo.order.MailInformation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/2.
 * 预约完成信息
 */
public class AppointFinishVO implements Serializable {
    /**
     * 业务编码，由预约系统分配
     */
    @NotNull
    protected String businessCode;

    /**
     * 预约订单号
     */
    @NotNull
    private Long appointOrderId;
    /**
     * 附件信息
     */

    @Valid
    private Attach attach;
    /**
     * 物流信息,实物回填物流单信息
     */
    @Valid
    private MailInformation mailInformation;

    /**
     * 操作人
     */
    private String operateUser;

    /**
     * 尾单号
     */
    private Long endOrderId;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }


    public Attach getAttach() {
        return attach;
    }

    public void setAttach(Attach attach) {
        this.attach = attach;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public MailInformation getMailInformation() {
        return mailInformation;
    }

    public void setMailInformation(MailInformation mailInformation) {
        this.mailInformation = mailInformation;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public Long getEndOrderId() {
        return endOrderId;
    }

    public void setEndOrderId(Long endOrderId) {
        this.endOrderId = endOrderId;
    }
}
