package com.jd.appoint.dao.order;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.domain.order.OrderStatisticPO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@MybatisRepository
public interface AppointOrderDao extends MybatisDao<AppointOrderPO> {
    /**
     * 附件修改
     *
     * @param appointOrderPo
     * @param overwrite
     * @return
     */
    int submitAttach(@Param(value = "appointOrderPo") AppointOrderPO appointOrderPo,
                     @Param(value = "overwrite") Boolean overwrite);


    /**
     * 取消预约
     *
     * @param appointOrderPo
     * @return
     */
    int cancel(@Param(value = "appointOrderPo") AppointOrderPO appointOrderPo,
               @Param(value = "appointStatus") List<Integer> appointStatus);

    /**
     * 完成预约
     *
     * @param appointOrderPO
     * @param overwrite
     */
    int finishAppoint(@Param(value = "appointOrderPo") AppointOrderPO appointOrderPO,
                      @Param(value = "overwrite") Boolean overwrite,
                      @Param(value = "appointStatus") List<Integer> appointStatus);


    /**
     * 执行派单
     *
     * @param appointOrderPo
     */
    void dispatchOrder(AppointOrderPO appointOrderPo);

    /**
     * 更新预约单信息
     *
     * @param appointOrderPO
     */
    void updateAppointOrder(AppointOrderPO appointOrderPO);

    /**
     * 统计某天订单数据 包括：总订单，待派单，待服务
     *
     * @param startDate
     * @param endDate
     * @param venderId
     * @return
     */
    OrderStatisticPO statisticByDate(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate,
                                     @Param("venderId") Long venderId);


    /**
     * @return
     */
    AppointOrderPO findOrderByCondition(@Param("id") Long id,
                                        @Param("businessCode") String businessCode,
                                        @Param("venderId") Long venderId);

    int updateSelective(AppointOrderPO appointOrderPO);

    /**
     * 更新预约时间
     *
     * @param appointOrderPO
     */
    void updateAppointTime(AppointOrderPO appointOrderPO);


    /**
     * 更新预约单状态
     *
     * @param appointOrderPO
     * @param preStatus
     * @return
     */
    int updateAppointStatus(@Param("appointOrderPO") AppointOrderPO appointOrderPO,
                            @Param("preStatus") Integer preStatus);

    /**
     * 批量获取预约单信息
     *
     * @return
     */
    List<AppointOrderPO> getAppointOrders(@Param("appointOrders") List<Long> appointOrders);

    /**
     * 预约单状态
     *
     * @param appointStatus
     * @return
     */
    List<AppointOrderPO> getAppointByStatus(@Param("appointStatus") Integer appointStatus);

    /**
     * 根据用户姓名获取预约单
     *
     * @return
     */
    List<AppointOrderPO> getAppointByUserPin(@Param("customerUserPin") String customerUserPin,
                                             @Param("businessCode") String businessCode);

    List<Long> allNewOrderIds();

    AppointOrderPO findByUniqueKey(String uniqueKey);

    /**
     * 查询取消中的预约单
     * @return
     */
    List<AppointOrderPO> findCancelingOrder();

    int rollbackStatus(Long id);
}