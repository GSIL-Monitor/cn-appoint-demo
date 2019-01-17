package com.jd.appoint.service.order.gw;

import com.jd.appoint.dao.order.AppointOrderDao;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.rpc.search.RpcAppointSearchGwService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.virtual.appoint.appointment.AppointmentRequest;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/6/6.
 */
@Service
public class GwAppointService {
    private Logger logger = LoggerFactory.getLogger(GwAppointService.class);
    private RpcAppointSearchGwService rpcAppointSearchGwService;
    @Autowired
    private AppointOrderDao appointOrderDao;

    /**
     * 提交网关预约，除非网关返回预约单号码，否则都直接
     *
     * @param detail
     * @return 商家调用成功返回对应的状态，调用失败返回NULL
     */
    public boolean submitGwAppoint(AppointOrderDetailVO detail) {
        AppointmentRequest appointmentRequest = this.convertRequest(detail);
        String outOrderId = rpcAppointSearchGwService.submit(appointmentRequest);
        if (Strings.isNullOrEmpty(outOrderId)) {
            AppointOrderPO appointOrderPO = new AppointOrderPO();
            appointOrderPO.setOuterOrderId(outOrderId);
            appointOrderDao.updateSelective(appointOrderPO);
            return true;
        }
        return false;
    }

    private AppointmentRequest convertRequest(AppointOrderDetailVO detail) {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setName(detail.getCustomerName());
        appointmentRequest.setContact(detail.getCustomerPhone());
        //appointmentRequest.setAddress()
        //appointmentRequest.setBusinessCode(detail.getBusinessCode());
        appointmentRequest.setStaffCode(detail.getStaffCode());
        appointmentRequest.setStoreCode(detail.getStoreCode());
        appointmentRequest.setPackageCode(detail.getPackageCode());
        appointmentRequest.setStartTime(detail.getAppointStartTime());
        appointmentRequest.setEndTime(detail.getAppointEndTime());
        appointmentRequest.setCode(detail.getCardNo());
        appointmentRequest.setPassword(detail.getCardPassword());
        appointmentRequest.setFormItems(detail.getFormItems());
        return appointmentRequest;
    }
}
