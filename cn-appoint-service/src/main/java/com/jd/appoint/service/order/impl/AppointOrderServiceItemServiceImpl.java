package com.jd.appoint.service.order.impl;

import com.jd.appoint.dao.order.AppointOrderServiceItemDao;
import com.jd.appoint.dao.tasks.TasksDao;
import com.jd.appoint.domain.dto.AppointDispatcherDTO;
import com.jd.appoint.domain.dto.OrderShipsDto;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.ProcessTypeEnum;
import com.jd.appoint.domain.enums.TasksStatusEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.AppointOrderServiceItemPO;
import com.jd.appoint.domain.shop.ShopStaffPO;
import com.jd.appoint.domain.tasks.TaskInfoPo;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.AppointOrderServiceItemService;
import com.jd.appoint.service.record.BuriedPointService;
import com.jd.appoint.service.shop.ShopStaffService;
import com.jd.appoint.service.sms.templets.SendMessageService;
import com.jd.appoint.service.task.constants.TaskConstants;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/15 10:11
 */
@Service
public class AppointOrderServiceItemServiceImpl implements AppointOrderServiceItemService {
    @Resource
    private AppointOrderServiceItemDao appointOrderServiceItemDao;

    @Resource
    private SendMessageService sendMessageService;
    @Resource
    private AppointOrderService appointOrderService;
    @Resource
    private BuriedPointService buriedPointService;

    @Resource
    private ShopStaffService shopStaffService;
    @Autowired
    private TasksDao tasksDao;
    private static final Logger logger = LoggerFactory.getLogger(AppointOrderServiceItemServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void dispatchOrder(AppointDispatcherDTO appointDispatcherDTO) {
        //服务人员信息
        ShopStaffPO shopStaffPO = shopStaffService.getStaffById(appointDispatcherDTO.getStaffId());
        if (null == shopStaffPO) {
            throw new IllegalArgumentException("不存在该员工，请核查！");
        }
        //执行派单操作
        AppointOrderServiceItemPO appointOrderServiceItemPO = getServiceItemByAppointOrderId(appointDispatcherDTO.getAppointOrderId());
        appointOrderServiceItemPO.setStaffName(shopStaffPO.getServerName());
        appointOrderServiceItemPO.setAppointOrderId(appointDispatcherDTO.getAppointOrderId());
        appointOrderServiceItemPO.setStaffUserPin(shopStaffPO.getUserPin());
        appointOrderServiceItemPO.setStaffCode(shopStaffPO.getId().toString());
        appointOrderServiceItemPO.setStaffPhone(shopStaffPO.getServerPhone());
        appointOrderServiceItemDao.update(appointOrderServiceItemPO);
        //修改主订单的状态
        AppointOrderPO appointOrderPo = new AppointOrderPO();
        appointOrderPo.setAppointStatus(AppointStatusEnum.WAIT_SERVICE);
        appointOrderPo.setId(appointDispatcherDTO.getAppointOrderId());
        appointOrderService.dispatchOrder(appointOrderPo);
        //埋点
        burryDispatcher(appointDispatcherDTO, shopStaffPO);
    }

    @Override
    public List<AppointOrderServiceItemPO> getAppointServiceItems(List<Long> appointOrderIds) {
        return appointOrderServiceItemDao.getAppointServiceItems(appointOrderIds);
    }

    @Override
    public int inputLsns(AppointOrderDetailVO v) {
        AppointOrderServiceItemPO appointOrderServiceItemPO = new AppointOrderServiceItemPO();
        //更新物流单号
        appointOrderServiceItemPO.setLogisticsNo(v.getLogisticsNo());
        appointOrderServiceItemPO.setLogisticsSource(v.getLogisticsSource());
        appointOrderServiceItemPO.setLogisticsSiteId(v.getLogisticsSiteId());
        appointOrderServiceItemPO.setAppointOrderId(v.getId());
        int inputCount = appointOrderServiceItemDao.updateLsns(appointOrderServiceItemPO);
        //插入订阅物流信息任务，包括发送订单完成短信
        if (inputCount > 0) {
            OrderShipsDto dto = new OrderShipsDto();
            dto.setAppointOrderId(v.getId());
            dto.setLogisticsNo(v.getLogisticsNo());
            dto.setLogisticsSiteId(v.getLogisticsSiteId());
            TaskInfoPo taskInfoPo = new TaskInfoPo();
            taskInfoPo.setContent(JSONArray.toJSONString(dto));
            taskInfoPo.setFunctionId(TaskConstants.INPUT_LSNS);
            taskInfoPo.setTaskStatus(TasksStatusEnum.DEAL_DOING);
            taskInfoPo.setMaxRetry(3);
            tasksDao.insert(taskInfoPo);
        }
        return inputCount;
    }

    /**
     * 派单埋点
     *
     * @param appointDispatcherDTO
     * @param shopStaffPO
     */
    private void burryDispatcher(AppointDispatcherDTO appointDispatcherDTO, ShopStaffPO shopStaffPO) {
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put("staffName", shopStaffPO.getServerName());
        mapParam.put("operateUser", appointDispatcherDTO.getOperateUserPin());
        //服务人员变更
        buriedPointService.processBuried(appointDispatcherDTO.getAppointOrderId().toString(), ProcessTypeEnum.CHANGE_SERVICE_STAFF.getCode(), mapParam);
    }

    /**
     * 根据预约单号查询预约服务item
     *
     * @param appointOrderId
     * @return
     */
    @Override
    public AppointOrderServiceItemPO getServiceItemByAppointOrderId(Long appointOrderId) {
        return appointOrderServiceItemDao.selectByAppointOrderId(appointOrderId);
    }

    @Override
    public int insert(AppointOrderServiceItemPO appointOrderServiceItemPO) {
        return appointOrderServiceItemDao.insert(appointOrderServiceItemPO);
    }

    @Override
    public void updateServiceItem(AppointOrderServiceItemPO appointOrderServiceItemPO) {
        appointOrderServiceItemDao.updateServiceItem(appointOrderServiceItemPO);
    }
}
