package com.jd.appoint.service.api.shop.convert;

import com.jd.appoint.domain.order.query.AppointUpdateQuery;
import com.jd.appoint.shopapi.vo.ShopAppointUpdateVO;

/**
 * Created by gaoxiaoqing on 2018/5/28.
 */
public class AppointUpdateConvert {


    /**
     * Shop端转换预约更新
     * @param shopAppointUpdateVO
     * @return
     */
    public static AppointUpdateQuery convertShopAppointUpdate(ShopAppointUpdateVO shopAppointUpdateVO){
        AppointUpdateQuery appointUpdateQuery = new AppointUpdateQuery();
        appointUpdateQuery.setAppointOrderId(shopAppointUpdateVO.getAppointOrderId());  //预约单ID
        appointUpdateQuery.setAppointStartTime(shopAppointUpdateVO.getAppointStartTime()); //预约开始时间
        appointUpdateQuery.setAppointEndTime(shopAppointUpdateVO.getAppointEndTime()); //预约结束时间
        appointUpdateQuery.setCustomerName(shopAppointUpdateVO.getCustomerName()); //消费者姓名
        appointUpdateQuery.setCustomerPhone(shopAppointUpdateVO.getCustomerPhone()); //消费者手机号
        appointUpdateQuery.setServerType(shopAppointUpdateVO.getServerType()); //服务类型
        appointUpdateQuery.setStaffId(shopAppointUpdateVO.getStaffId());  //服务人员ID
        appointUpdateQuery.setFormItems(shopAppointUpdateVO.getFormItems()); //服务项
        appointUpdateQuery.setVenderId(shopAppointUpdateVO.getVenderId()); //商家ID
        appointUpdateQuery.setBusinessCode(shopAppointUpdateVO.getBusinessCode());
        appointUpdateQuery.setOperateUserPin(shopAppointUpdateVO.getOperateUserPin()); //操作人员
        if(null == appointUpdateQuery.getOperateUserPin()){
            appointUpdateQuery.setOperateUserPin("admin");
        }
        return appointUpdateQuery;
    }
}
