package com.jd.appoint.service.sys;

import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.common.enums.ScheduleModelEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.sys.VenderConfigPO;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/15 17:47
 */
public interface VenderConfigService {
    /**
     * 获取路由配置信息
     *
     * @param venderConfigPO
     * @return
     */
    VenderConfigVO getVenderConfig(VenderConfigPO venderConfigPO);


    /**
     * 通过条件查询所有数据
     *
     * @return
     */
    List<VenderConfigPO> findAll(Map<String, String> query);

    /**
     * 更新配置
     *
     * @param venderConfigPO
     */
    void updateConfig(VenderConfigPO venderConfigPO);


    /**
     * 添加配置
     *
     * @param venderConfigPO
     */
    void insertConfig(VenderConfigPO venderConfigPO);

    /**
     * 返回当前商家是不是shop端，不是shop端需要调用底层商家网关
     *
     * @param businessCode
     * @param venderId
     * @return
     */
    boolean isShop(String businessCode, long venderId);

    /**
     * 通过配置找到对应的值
     *
     * @param id
     * @return
     */
    VenderConfigPO findVenderConfigByID(Long id);

    /**
     * 查询配置
     *
     * @param businessCode
     * @param venderId
     * @param key
     * @return
     */
    VenderConfigVO getConfig(String businessCode, long venderId, String key);

    /**
     * 获得产能日历模式
     * @param businessCode
     * @return
     */
    ScheduleModelEnum getScheduleModel(String businessCode);

    /**
     * 获得库存设置
     * @param businessCode
     * @return true【需要设置库存】 false【不需要设置库存】
     */
    Boolean getStockConfig(String businessCode);

    /**
     * 获取时间显示模式
     * @param businessCode
     * @return
     */
    TimeShowTypeEnum getTimeShowType(String businessCode);
}
