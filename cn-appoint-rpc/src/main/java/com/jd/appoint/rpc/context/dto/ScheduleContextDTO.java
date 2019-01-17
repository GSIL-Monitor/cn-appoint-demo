package com.jd.appoint.rpc.context.dto;

import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.shop.ShopWorkTimePO;

import java.util.Date;

/**
 * Created by luqiang3 on 2018/6/21.
 */
public class ScheduleContextDTO {

    /**
     * 业务编码
     */
    private String businessCode;

    /**
     * 商家编号
     */
    private Long venderId;

    /**
     * 门店编码
     */
    private String storeCode;

    /**
     * sku编号
     */
    private Long skuId;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    private String contextId;

    private String[] dateList;

    @Override
    public String toString() {
        return "ScheduleContextDTO{" +
                "businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", storeCode='" + storeCode + '\'' +
                ", skuId=" + skuId +
                ", startDate='" + convertDate(startDate) + '\'' +
                ", endDate='" + convertDate(endDate) + '\'' +
                ", contextId='" + contextId + '\'' +
                '}';
    }

    /**
     * 初始化开始日期和结束日期
     * @param workTime
     */
    public void initStartAndEndDate(ShopWorkTimePO workTime){
        if(null != startDate && null != endDate){
            return;
        }
        Date currentDate = AppointDateUtils.getDate2Date("yyyy-MM-dd", new Date());//当前日期
        Date startDate = AppointDateUtils.addDays(currentDate, workTime.getStartDay());//配置的开始日期
        Date endDate = AppointDateUtils.addDays(currentDate, workTime.getEndDay());//配置的结束日期
        Date monthLastDay = AppointDateUtils.getMonthLastDay(startDate);//当月最后一天
        this.startDate = startDate;
        //设置结束日期
        if(TimeShowTypeEnum.RANGE == workTime.getTimeShowType()
                || TimeShowTypeEnum.POINT == workTime.getTimeShowType()){//时间槽模式，设置结束日期=开始日期
            this.endDate = startDate;
        }else if(AppointDateUtils.daysBetweenTwoDate(endDate, monthLastDay) >= 0){//配置的结束日期<=当月最后一天
            this.endDate = endDate;
        }else {//配置的结束日期>当月最后一天
            this.endDate = monthLastDay;
        }
    }

    /**
     * 初始化日期列表
     */
    public void initDateList(){
        int intervalDays = AppointDateUtils.daysBetweenTwoDate(startDate, endDate) + 1;
        this.dateList = new String[intervalDays];
        for(int i = 0; i < intervalDays; i++){
            String date = AppointDateUtils.getDate2Str("yyyy-MM-dd", AppointDateUtils.addDays(startDate, i));
            dateList[i] = date;
        }
    }

    //get set
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String[] getDateList() {
        return dateList;
    }

    public void setDateList(String[] dateList) {
        this.dateList = dateList;
    }

    private String convertDate(Date date){
        if(null == date){
            return null;
        }
        return AppointDateUtils.getDate2Str("yyyy-MM-dd", date);
    }
}
