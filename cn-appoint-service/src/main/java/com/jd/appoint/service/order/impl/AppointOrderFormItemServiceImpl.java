package com.jd.appoint.service.order.impl;

import com.jd.appoint.dao.order.AppointOrderFormItemDao;
import com.jd.appoint.dao.order.AppointOrderServiceItemDao;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.order.AppointOrderFormItemPO;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.service.order.AppointOrderFormItemService;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.AppointOrderServiceItemService;
import com.jd.appoint.service.shop.ShopStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lishuaiwei
 * @date 2018/5/15 10:11
 */
@Service
public class AppointOrderFormItemServiceImpl implements AppointOrderFormItemService {
    @Autowired
    private AppointOrderFormItemDao appointOrderFormItemDao;


    @Override
    public List<AppointOrderFormItemPO> getFormItemListByAppointOrderId(Long appointOrderId) {
        return appointOrderFormItemDao.selectFormItemListByAppointOrderId(appointOrderId);
    }

    @Override
    public int insert(AppointOrderFormItemPO appointOrderFormItemPO) {
        return appointOrderFormItemDao.insert(appointOrderFormItemPO);
    }

    @Override
    public List<AppointOrderFormItemPO> getAppointFormItems(List<Long> appointOrderIds) {
        return appointOrderFormItemDao.getAppointFormItems(appointOrderIds);
    }
}
