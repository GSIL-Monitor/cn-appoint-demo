package com.jd.appoint.vo.order;

import com.jd.appoint.shopapi.vo.LsnInputVO;
import org.w3c.dom.ls.LSInput;

import java.util.Date;
import java.util.List;

public class DynamicOrderDetailVO {
    private Long skuId;
    private Date appointStartTime;
    /**
     * 物流公司
     */
    private String logisticsSource;

    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 物流公司站点id
     */
    private Integer logisticsSiteId;
    private List<OrderDetailGroupVO> orderDetailGroupVOList;

    private String venderMemo;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public List<OrderDetailGroupVO> getOrderDetailGroupVOList() {
        return orderDetailGroupVOList;
    }

    public void setOrderDetailGroupVOList(List<OrderDetailGroupVO> orderDetailGroupVOList) {
        this.orderDetailGroupVOList = orderDetailGroupVOList;
    }

    public Date getAppointStartTime() {
        return appointStartTime;
    }

    public void setAppointStartTime(Date appointStartTime) {
        this.appointStartTime = appointStartTime;
    }

    public String getLogisticsSource() {
        return logisticsSource;
    }

    public void setLogisticsSource(String logisticsSource) {
        this.logisticsSource = logisticsSource;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Integer getLogisticsSiteId() {
        return logisticsSiteId;
    }

    public void setLogisticsSiteId(Integer logisticsSiteId) {
        this.logisticsSiteId = logisticsSiteId;
    }

    public String getVenderMemo() {
        return venderMemo;
    }

    public void setVenderMemo(String venderMemo) {
        this.venderMemo = venderMemo;
    }
}
