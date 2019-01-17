package com.jd.appoint.service.order;

import com.jd.appoint.domain.dto.ReschuleDTO;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.OrderStatisticPO;
import com.jd.appoint.domain.order.query.AppointUpdateQuery;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import com.jd.appoint.service.order.exceptions.InputLnsException;
import com.jd.appoint.shopapi.vo.LsnInputVO;
import com.jd.appoint.vo.order.*;
import com.jd.travel.base.soa.SoaResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by shaohongsen on 2018/5/10.
 */
public interface AppointOrderService {

    /**
     * 获取订单详情的接口
     *
     * @param id
     * @return
     */
    AppointOrderDetailVO detail(long id);

    /**
     * 快速获得订单详情接口
     * @param id
     * @return
     */
    AppointOrderDetailVO fastDetail(long id);

    /**
     * 获取订单详情的接口
     *
     * @param id
     * @return
     */
    SoaResponse<DynamicOrderDetailVO> dynamicDetail(long id, String businessCode, Integer platform, Integer serverType, Long venderId, Long storeId);

    /**
     * 批量预约单详情
     * @return
     */
    List<AppointOrderDetailVO> appointDetailList(List<AppointOrderPO> appointOrderPOList);

    /**
     * 提交附件
     *
     * @param appointOrderPo
     * @return
     */
    int submitAttach(AppointOrderPO appointOrderPo, Boolean overwrite);

    /**
     * 取消预约接口
     *
     * @param appointOrderPO
     */
    void cancel(AppointOrderPO appointOrderPO);

    /**
     * 预约完成更新接口
     *
     * @param finishVO
     */
    void finishAppoint(FinishAppointDto finishVO,int platform);

    /**
     * 派单方法
     *
     * @param appointOrderPo
     */
    void dispatchOrder(AppointOrderPO appointOrderPo);

    /**
     * 动态更新预约单信息
     *
     * @param updateAppointVO
     */
    void dynamicUpdateAppoint(UpdateAppointVO updateAppointVO);

    /**
     * 更新预约单信息
     *
     * @param appointUpdateQuery
     */
    void editAppointOrder(AppointUpdateQuery appointUpdateQuery);

    /**
     * 根据预约单号查询预约信息
     *
     * @param appointOrderId
     * @return
     */
    AppointOrderPO getAppointOrder(Long appointOrderId);

    /**
     * 提交预约
     *
     * @param detailVO
     * @return
     */
    AppointOrderResult submitAppoint(AppointOrderDetailVO detailVO);

    /**
     * 提交商家订单
     *
     * @param detail
     * @return
     */
    public AppointStatusEnum submitVenderOrder(AppointOrderDetailVO detail);

    /**
     * 统计订单数据
     *
     * @param startDate
     * @param endDate
     * @param venderId
     * @return
     */
    OrderStatisticPO statisticByDate(LocalDate startDate, LocalDate endDate, Long venderId);


    /**
     * 改期接口
     *
     * @param reschuleDTO
     */
    AppointOrderResult reschdule(ReschuleDTO reschuleDTO);

    /**
     * 商家改期
     * @return
     */
    OperateResultEnum venderReschdule(AppointOrderDetailVO appointOrderDetailVO,Long taskId);

    /**
     * 派单
     *
     * @param dispatchOrderVO
     */
    void dispatchOrder(DispatchOrderVO dispatchOrderVO);

    /**
     * 更新附件信息
     *
     * @param updateAttachVO
     */
    void updateAttachInfo(UpdateAttachVO updateAttachVO);

    /**
     * 审核预约单
     *
     * @param checkOrderVO
     */
    List<Long> checkAppointOrder(CheckOrderVO checkOrderVO);

    /**
     * 根据状态查预约单
     *
     * @param appointStatus
     * @return
     */
    List<AppointOrderPO> getAppointByStatus(Integer appointStatus);

    /**
     * 更新预约单
     */
    int updateAppointStatus(AppointOrderPO appointOrderPO, Integer appointStatus);

    /**
     * 根据userPin获取预约单
     *
     * @param userPin
     * @param businessCode
     * @return
     */
    List<AppointOrderPO> getAppointOrderByUserPin(String userPin, String businessCode);

    /**
     * 导入物流单号
     *
     * @param data
     */
    void inputLsns(LsnInputVO data) throws InputLnsException;

    AppointOrderDetailVO fillDetailByCode(String contextId,AppointOrderDetailVO detailVO);

    public List<Long> allNewOrderIds();

    AppointOrderPO findOne(long id);

    /**
     * 查询取消中的预约单
     * @return
     */
    List<AppointOrderPO> findCancelingOrder();

}
