package com.jd.appoint.vo.order;

import java.util.List;

public class OrderDetailGroupVO {
    private String groupName;
    private List<OrderDetailItemVO> orderDetailItemVOList;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public List<OrderDetailItemVO> getOrderDetailItemVOList() {
        return orderDetailItemVOList;
    }

    public void setOrderDetailItemVOList(List<OrderDetailItemVO> orderDetailItemVOList) {
        this.orderDetailItemVOList = orderDetailItemVOList;
    }
}
