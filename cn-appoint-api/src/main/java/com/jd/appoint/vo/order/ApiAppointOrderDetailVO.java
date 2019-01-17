package com.jd.appoint.vo.order;


import com.jd.appoint.vo.dynamic.FieldVo;
import com.jd.appoint.vo.dynamic.OperateItemVo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 预约单列表
 * Created by gaoxiaoqing on 2018/6/11.
 */
public class ApiAppointOrderDetailVO {

    /**
     * 预约ID
     */
    @NotNull
    private Long appointOrderId;

    /**
     * 预约状态名称
     */
    @NotNull
    private String appointStatusName;

    /**
     * 列表展示项
     */
    private List<FieldVo> fieldVos;

    /**
     * 操作项列表
     */
    private List<OperateItemVo> operateItemVos;

    /**
     * 标题名称
     */
    private String titleName;

    public Long getAppointOrderId() {
        return appointOrderId;
    }

    public void setAppointOrderId(Long appointOrderId) {
        this.appointOrderId = appointOrderId;
    }

    public String getAppointStatusName() {
        return appointStatusName;
    }

    public void setAppointStatusName(String appointStatusName) {
        this.appointStatusName = appointStatusName;
    }

    public List<FieldVo> getFieldVos() {
        return fieldVos;
    }

    public void setFieldVos(List<FieldVo> fieldVos) {
        this.fieldVos = fieldVos;
    }

    public List<OperateItemVo> getOperateItemVos() {
        return operateItemVos;
    }

    public void setOperateItemVos(List<OperateItemVo> operateItemVos) {
        this.operateItemVos = operateItemVos;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
