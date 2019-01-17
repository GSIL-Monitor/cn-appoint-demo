package com.jd.appoint.service.order.convert;

import com.google.common.collect.Maps;
import com.jd.appoint.domain.dto.AppointDispatcherDTO;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.order.AppointOrderFormItemPO;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import com.jd.appoint.domain.order.query.AppointUpdateQuery;
import com.jd.appoint.domain.rpc.ServiceTypeInfo;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.appoint.service.order.vo.ValueFromControlItem;
import com.jd.appoint.stfapi.vo.StaffAppointOrderListRequest;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.virtual.appoint.appointment.AppointmentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 预约单信息转换
 * Created by gaoxiaoqing on 2018/5/14.
 */
public class AppointOrderConvert {
    private static final Logger logger = LoggerFactory.getLogger(AppointOrderConvert.class);

    /**
     * 附属信息转换
     *
     * @param updateAttachVO
     * @return
     */
    public static AppointOrderPO convertAttachInfo(UpdateAttachVO updateAttachVO) {
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setId(updateAttachVO.getAppointOrderId()); //预约单号
        appointOrderPO.setAttrUrls(updateAttachVO.getAttachVO().getAttrUrls()); //附件URL
        return appointOrderPO;
    }

    /**
     * 新网关更新预约单信息
     *
     * @return
     */
    public static AppointmentRequest updateAppointGwConvert(ReschduleVO reschduleVO, AppointOrderServiceItemPO appointOrderServiceItemPO) {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setCode(appointOrderServiceItemPO.getCardNo());
        appointmentRequest.setPassword(appointOrderServiceItemPO.getCardPassword());
        appointmentRequest.setStartTime(reschduleVO.getAppointStartTime());
        appointmentRequest.setEndTime(reschduleVO.getAppointEndTime());
        return appointmentRequest;
    }

    public static AppointOrderPO convertToAppointOrder(AppointOrderDetailVO data) {
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setSkuId(data.getSkuId());
        appointOrderPO.setServerType(data.getServerType());
        appointOrderPO.setBusinessCode(data.getBusinessCode());
        appointOrderPO.setVenderId(data.getVenderId());
        appointOrderPO.setCustomerName(data.getCustomerName());
        appointOrderPO.setCustomerPhone(data.getCustomerPhone());
        appointOrderPO.setCustomerUserPin(data.getCustomerUserPin());
        appointOrderPO.setErpOrderId(data.getErpOrderId());
        appointOrderPO.setEndOrderId(data.getEndOrderId());
        appointOrderPO.setAppointStartTime(data.getAppointStartTime());
        appointOrderPO.setAppointEndTime(data.getAppointEndTime());
        appointOrderPO.setAttrUrls(data.getAttrUrls());
        return appointOrderPO;
    }

    public static AppointOrderFormItemPO convertToOrderFormItem(ValueFromControlItem item, long appointOrderId) {
        AppointOrderFormItemPO orderFormItemPO = new AppointOrderFormItemPO();
        orderFormItemPO.setAppointOrderId(appointOrderId);
        orderFormItemPO.setFormControlId(item.getFormControlId());
        orderFormItemPO.setAttrNameAlias(item.getAttrNameAlias());
        orderFormItemPO.setAttrValue(item.getAttrValue());
        orderFormItemPO.setStatus(StatusEnum.ENABLE);
        return orderFormItemPO;
    }

    public static AppointOrderServiceItemPO convertToOrderServiceItem(AppointOrderDetailVO data, long appointOrderId) {
        AppointOrderServiceItemPO serviceItemPO = new AppointOrderServiceItemPO();
        serviceItemPO.setAppointOrderId(appointOrderId);
        serviceItemPO.setCityId(data.getCityId());
        serviceItemPO.setCityName(data.getCityName());
        serviceItemPO.setCardNo(data.getCardNo());
        serviceItemPO.setCardPassword(data.getCardPassword());
        serviceItemPO.setPackageName(data.getPackageName());
        serviceItemPO.setPackageCode(data.getPackageCode());
        serviceItemPO.setStoreName(data.getStoreName());
        serviceItemPO.setStoreCode(data.getStoreCode());
        serviceItemPO.setStoreAddress(data.getStoreAddress());
        serviceItemPO.setStorePhone(data.getStorePhone());
        serviceItemPO.setStaffName(data.getStaffName());
        serviceItemPO.setStaffCode(data.getStaffCode());
        serviceItemPO.setStorePhone(data.getStorePhone());
        serviceItemPO.setStaffUserPin(data.getStaffUserPin());
        return serviceItemPO;
    }

    /**
     * 批量映射中文状态
     *
     * @param details
     * @param statusInfoList
     */
    public static void mapChStatus(List<AppointOrderDetailVO> details, List<SystemStatusInfo> statusInfoList) {
        try {
            if (details != null && !details.isEmpty()) {//如果有内容，获取状态映射
                Map<Integer, ServiceTypeInfo> statusMap = getStatusMap(statusInfoList);
                details.forEach(detail -> {
                    Integer appointStatus = detail.getAppointStatus();
                    if (appointStatus != null && statusMap.containsKey(appointStatus)) {
                        ServiceTypeInfo serviceTypeInfo = statusMap.get(appointStatus);
                        if (detail.getServerType() == 1) {
                            detail.setChAppointStatus(serviceTypeInfo.getToHomeStatusName());
                        } else {
                            detail.setChAppointStatus(serviceTypeInfo.getToShopStatusName());
                        }
                    }
                });
            }
        } catch (Exception e) {
            logger.error("mapChStatus error", e);
        }
    }

    private static Map<Integer, ServiceTypeInfo> getStatusMap(List<SystemStatusInfo> statusInfoList) {
        if (statusInfoList == null || statusInfoList.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        Map<Integer, ServiceTypeInfo> result = statusInfoList.stream()
                .collect(Collectors.toMap(SystemStatusInfo::getSystemStatusCode,
                        SystemStatusInfo::getServiceTypeInfo));
        return result;
    }

    /**
     * 获取下单成功的订单初始状态
     * 服务类型：上门(1),到店(2)
     *
     * @param serverType
     * @return
     */
    public static AppointStatusEnum getSuccessInitStatus(int serverType) {
        if (serverType == 1) {
            return AppointStatusEnum.WAIT_ORDER;
        }
        return AppointStatusEnum.WAIT_SERVICE;
    }

    public static Page convertToPage(StaffAppointOrderListRequest data) {
        Page page = new Page();
        page.setPageNumber(data.getPageNumber());
        page.setPageSize(data.getPageSize());
        page.setSorts(data.getSortList());
        Map<String, String> searchMap = null;
        if (data.getFilterItemVOS() != null && !data.getFilterItemVOS().isEmpty()) {
            searchMap = data.getFilterItemVOS().stream().collect(Collectors.toMap(filterItem -> filterItem.getFieldName() + "." + filterItem.getFilterOperator(),
                    filterItem -> filterItem.getValue()));
        }
        if (searchMap == null) {
            searchMap = Maps.newHashMap();
        }
        searchMap.put("businessCode.EQ", data.getBusinessCode());
        page.setSearchMap(searchMap);
        return page;
    }

    /**
     * 转换服务条目信息
     *
     * @return
     */
    public static AppointOrderServiceItemPO convertServiceAttachItem(UpdateAttachVO updateAttachVO,String logisticsSource) {
        AppointOrderServiceItemPO appointOrderServiceItemPO = new AppointOrderServiceItemPO();
        appointOrderServiceItemPO.setAppointOrderId(updateAttachVO.getAppointOrderId());
        appointOrderServiceItemPO.setLogisticsNo(updateAttachVO.getLogisticVO().getLogisticsNo());
        appointOrderServiceItemPO.setLogisticsSiteId(updateAttachVO.getLogisticVO().getLogisticsSiteId());
        appointOrderServiceItemPO.setLogisticsSource(logisticsSource);
        return appointOrderServiceItemPO;
    }

    /**
     * 转换服务条目信息
     *
     * @return
     */
    public static AppointOrderServiceItemPO convertServiceItem(UpdateAppointVO updateAppointVO) {
        AppointOrderServiceItemPO appointOrderServiceItemPO = new AppointOrderServiceItemPO();
        appointOrderServiceItemPO.setVenderMemo(updateAppointVO.getVenderMemo());
        appointOrderServiceItemPO.setAppointOrderId(updateAppointVO.getAppointOrderId());
        return appointOrderServiceItemPO;
    }

    /**
     * 预约单转换
     *
     * @param appointUpdateQuery
     * @return
     */
    public static AppointOrderPO convertAppointOrder(AppointUpdateQuery appointUpdateQuery) {
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setId(appointUpdateQuery.getAppointOrderId()); //预约单号
        appointOrderPO.setCustomerName(appointUpdateQuery.getCustomerName());     //客户姓名
        appointOrderPO.setAppointStartTime(appointUpdateQuery.getAppointStartTime()); //预约开始时间
        appointOrderPO.setAppointEndTime(appointUpdateQuery.getAppointEndTime()); //预约结束时间
        appointOrderPO.setCustomerPhone(appointUpdateQuery.getCustomerPhone()); //客户电话
        appointOrderPO.setServerType(appointUpdateQuery.getServerType()); //服务类型
        return appointOrderPO;
    }




    /**
     * 转换派单DTO
     *
     * @param appointUpdateQuery
     * @return
     */
    public static AppointDispatcherDTO convertAppointDispatcher(AppointUpdateQuery appointUpdateQuery, Integer appointStatus) {
        AppointDispatcherDTO appointDispatcherDTO = new AppointDispatcherDTO();
        appointDispatcherDTO.setAppointOrderId(appointUpdateQuery.getAppointOrderId());
        appointDispatcherDTO.setAppointStartTime(appointUpdateQuery.getAppointStartTime());
        appointDispatcherDTO.setAppointEndTime(appointUpdateQuery.getAppointEndTime());
        appointDispatcherDTO.setStaffId(appointUpdateQuery.getStaffId());
        appointDispatcherDTO.setAppointStatus(appointStatus);
        appointDispatcherDTO.setOperateUserPin(appointUpdateQuery.getOperateUserPin());
        return appointDispatcherDTO;
    }

    /**
     * 预约单详情转换
     *
     * @return
     */
    public static AppointOrderDetailVO convertAppointOrder(AppointOrderPO appointOrderPO, AppointOrderServiceItemPO appointOrderServiceItemPO) {
        AppointOrderDetailVO appointOrderDetailVO = new AppointOrderDetailVO();
        BeanUtils.copyProperties(appointOrderPO, appointOrderDetailVO);
        BeanUtils.copyProperties(appointOrderServiceItemPO, appointOrderDetailVO);
        appointOrderDetailVO.setAppointStatus(appointOrderPO.getAppointStatus().getIntValue());
        return appointOrderDetailVO;
    }
}
