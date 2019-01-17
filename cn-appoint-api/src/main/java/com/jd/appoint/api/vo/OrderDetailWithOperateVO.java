package com.jd.appoint.api.vo;

import com.jd.appoint.vo.dynamic.OperateItemVo;
import com.jd.appoint.vo.order.AppointOrderDetailVO;

import java.util.List;

/**
 * Created by shaohongsen on 2018/7/3.
 */
public class OrderDetailWithOperateVO {
    private AppointOrderDetailVO appointOrderDetailVO;
    private List<OperateItemVo> operateItemList;

    public AppointOrderDetailVO getAppointOrderDetailVO() {
        return appointOrderDetailVO;
    }

    public void setAppointOrderDetailVO(AppointOrderDetailVO appointOrderDetailVO) {
        this.appointOrderDetailVO = appointOrderDetailVO;
    }

    public List<OperateItemVo> getOperateItemList() {
        return operateItemList;
    }

    public void setOperateItemList(List<OperateItemVo> operateItemList) {
        this.operateItemList = operateItemList;
    }
}
