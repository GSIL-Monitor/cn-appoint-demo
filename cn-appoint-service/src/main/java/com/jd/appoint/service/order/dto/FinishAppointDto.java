package com.jd.appoint.service.order.dto;

import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.order.Attach;
import com.jd.appoint.vo.order.MailInformation;

import java.util.Date;

/**
 * @author lijizhen1@jd.com
 * @date 2018/7/2 17:29
 */
public class FinishAppointDto {
    /**
     * 前台传递的请求参数
     */
    private AppointFinishVO appointFinishVO;

    public FinishAppointDto(AppointFinishVO appointFinishVO) {
        this.appointFinishVO = appointFinishVO;
    }

    public FinishAppointDto(AppointOrderPO appointOrderPO) {
        this.appointOrderPO = appointOrderPO;
    }

    /**
     * 需要解析出的需要持久到数据库的预约单
     */
    private AppointOrderPO appointOrderPO;
    /**
     * 瞬时状态是否覆盖
     */
    private transient Boolean overwrite;
    /**
     * 附件
     */
    private Attach attach;

    /**
     * 物流信息
     */
    private MailInformation mailInformation;

    /**
     * 需要解析出的需要持久到数据库的预约单
     *
     * @return
     */
    public AppointOrderPO getAppointOrderPO() {
        if (null != appointOrderPO) {
            return appointOrderPO;
        }
        if (null != appointFinishVO) {
            //对象装换
            AppointOrderPO appointOrderPO = new AppointOrderPO();
            appointOrderPO.setId(appointFinishVO.getAppointOrderId());
            appointOrderPO.setBusinessCode(appointFinishVO.getBusinessCode());
            appointOrderPO.setAppointFinishTime(new Date());
            appointOrderPO.setOperateUser(appointFinishVO.getOperateUser());
            appointOrderPO.setEndOrderId(appointFinishVO.getEndOrderId());
            if(null != appointFinishVO.getAttach()){
                appointOrderPO.setAttrUrls(appointFinishVO.getAttach().getUrls());
            }
            this.appointOrderPO = appointOrderPO;
            return this.appointOrderPO;
        }
        return this.appointOrderPO;
    }

    /**
     * 是否覆盖附件
     *
     * @return
     */
    public Boolean getOverwrite() {
        if (null != overwrite) {
            return overwrite;
        }
        Attach attach1 = getAttach();
        if (null != attach1) {
            //获得覆盖的方式
            overwrite = attach1.getOverwrite();
        } else {
            overwrite = null;
        }
        return overwrite;
    }

    /**
     * 附件信息
     *
     * @return
     */
    public Attach getAttach() {
        if (null != attach) {
            return attach;
        }
        if (null != appointFinishVO) {
            this.attach = appointFinishVO.getAttach();
        }
        return attach;
    }

    /**
     * 物流信息
     *
     * @return
     */
    public MailInformation getMailInformation() {
        if (null != mailInformation) {
            return mailInformation;
        }
        if (null != appointFinishVO) {
            this.mailInformation = appointFinishVO.getMailInformation();
        }
        return mailInformation;
    }

    public AppointFinishVO getAppointFinishVO() {
        return appointFinishVO;
    }

    public void setAppointFinishVO(AppointFinishVO appointFinishVO) {
        this.appointFinishVO = appointFinishVO;
    }

    public void setAppointOrderPO(AppointOrderPO appointOrderPO) {
        this.appointOrderPO = appointOrderPO;
    }

    public void setAttach(Attach attach) {
        this.attach = attach;
    }

    public void setMailInformation(MailInformation mailInformation) {
        this.mailInformation = mailInformation;
    }
}
