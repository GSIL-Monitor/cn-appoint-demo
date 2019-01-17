package com.jd.appoint.service.sys.impl;

import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.common.constants.CacheConstant;
import com.jd.appoint.common.enums.ScheduleModelEnum;
import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.dao.sys.VenderConfigDao;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.TimeShowTypeEnum;
import com.jd.appoint.domain.sys.VenderConfigPO;
import com.jd.appoint.service.sys.VenderConfigConstant;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.jim.cli.Cluster;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/15 17:48
 */
@Service
public class VenderConfigServiceImpl implements VenderConfigService {
    private static Logger logger = LoggerFactory.getLogger(VenderConfigServiceImpl.class);
    @Resource
    private VenderConfigDao venderConfigDao;
    @Resource(name = "jimClient")
    private Cluster jimClient;

    @Override
    public VenderConfigVO getVenderConfig(VenderConfigPO venderConfigPO) {
        VenderConfigVO venderConfigVO = new VenderConfigVO();
        //整张表做成map结构的缓存
        venderConfigVO.setKey(venderConfigPO.getCfgKey());
        //获取商家特有配置
        String venderConfigKey = CacheUtils.getVenderConfigKey();
        String venderField = CacheUtils.getVenderConfigTripleField(venderConfigPO.getCfgKey(), venderConfigPO.getBusinessCode(), venderConfigPO.getVenderId());

        String value = CacheUtils.getObjectFromCacheOrDBWithDefaultValue(String.class, () -> {
            return jimClient.hGet(venderConfigKey,venderField);
        }, () -> {
            return venderConfigDao.findByBusinessCodeAndVenderIdAndKey(venderConfigPO);
        }, (v) -> {
            jimClient.hSet(venderConfigKey,venderField, v);
        },CacheConstant.DEFAULT_NULL_VALUE);
        //拿到不是空值和默认值直接返回
        if (StringUtils.isNotEmpty(value) && !CacheConstant.DEFAULT_NULL_VALUE.equals(value)) {
            venderConfigVO.setValue(value);
            return venderConfigVO;
        }


        //获取业务线默认配置
        venderConfigPO.setVenderId(-1L);
        String businessField = CacheUtils.getVenderConfigTripleField(venderConfigPO.getCfgKey(), venderConfigPO.getBusinessCode(), venderConfigPO.getVenderId());
        value = CacheUtils.getObjectFromCacheOrDBWithDefaultValue(String.class, () -> {
            return jimClient.hGet(venderConfigKey,businessField);
        }, () -> {
            return venderConfigDao.findByBusinessCodeAndVenderIdAndKey(venderConfigPO);
        }, (v) -> {
            jimClient.hSet(venderConfigKey,businessField, v);
        },CacheConstant.DEFAULT_NULL_VALUE);
        //返回
        if(CacheConstant.DEFAULT_NULL_VALUE.equals(value)){
            value = null;
        }
        venderConfigVO.setValue(value);
        return venderConfigVO;

    }

    @Override
    public List<VenderConfigPO> findAll(Map<String, String> query) {
        return venderConfigDao.findAll(query);
    }

    @Override
    public void updateConfig(VenderConfigPO venderConfigPO) {
        venderConfigDao.update(venderConfigPO);

        //删除缓存
        String field = CacheUtils.getVenderConfigTripleField(venderConfigPO.getCfgKey(), venderConfigPO.getBusinessCode(), venderConfigPO.getVenderId());
        String venderConfigKey = CacheUtils.getVenderConfigKey();
        jimClient.hDel(venderConfigKey,field);
    }

    @Override
    public void insertConfig(VenderConfigPO venderConfigPO) {
        venderConfigDao.insert(venderConfigPO);
        //删除缓存
        String field = CacheUtils.getVenderConfigTripleField(venderConfigPO.getCfgKey(), venderConfigPO.getBusinessCode(), venderConfigPO.getVenderId());
        String venderConfigKey = CacheUtils.getVenderConfigKey();
        jimClient.hDel(venderConfigKey,field);
    }


    @Override
    public boolean isShop(String businessCode, long venderId) {
        VenderConfigVO venderConfigVo = this.getConfig(businessCode, venderId, VenderConfigConstant.ROUTE_KEY);
        if (venderConfigVo == null || null == venderConfigVo.getValue()) {
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_ORDER_SOURCE_TYPE);
            return true;
        }
        if (venderConfigVo.getValue().startsWith("shop")) {
            return true;
        }
        return false;
    }

    @Override
    public VenderConfigPO findVenderConfigByID(Long id) {
        return venderConfigDao.findById(id);
    }

    @Override
    public VenderConfigVO getConfig(String businessCode, long venderId, String key) {
        VenderConfigPO venderConfigPO = new VenderConfigPO();
        venderConfigPO.setCfgKey(key);
        venderConfigPO.setBusinessCode(businessCode);
        venderConfigPO.setVenderId(venderId);
        return this.getVenderConfig(venderConfigPO);
    }

    /**
     * 获得产能日历模式
     *
     * @param businessCode
     * @return
     */
    @Override
    public ScheduleModelEnum getScheduleModel(String businessCode) {
        VenderConfigVO venderConfigVo = this.getConfig(businessCode, -1L, VenderConfigConstant.SCHEDULE_MODEL);
        if(null == venderConfigVo || StringUtils.isBlank(venderConfigVo.getValue())){
            logger.error("查询产能日历模式失败：businessCode={}", businessCode);
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.SCHEDULE_MODEL);
            return ScheduleModelEnum.VENDER;
        }
        if(venderConfigVo.getValue().equals("vender")){
            return ScheduleModelEnum.VENDER;
        }
        if(venderConfigVo.getValue().equals("sku")){
            return ScheduleModelEnum.SKU;
        }
        return ScheduleModelEnum.VENDER;
    }

    /**
     * 获得库存设置
     *
     * @param businessCode
     * @return true【需要设置库存】 false【不需要设置库存】
     */
    @Override
    public Boolean getStockConfig(String businessCode) {
        VenderConfigVO venderConfigVo = this.getConfig(businessCode, -1L, VenderConfigConstant.STOCK_SET);
        if(null == venderConfigVo || StringUtils.isBlank(venderConfigVo.getValue())){
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.STOCK_SET);
            throw new RuntimeException("未配置库存设置：businessCode="+businessCode);
        }
        return Boolean.parseBoolean(venderConfigVo.getValue());
    }

    /**
     * 获取时间显示模式
     *
     * @param businessCode
     * @return
     */
    @Override
    public TimeShowTypeEnum getTimeShowType(String businessCode) {
        VenderConfigVO venderConfigVo = this.getConfig(businessCode, -1L, VenderConfigConstant.TIME_SHOW_TYPE);
        if(null == venderConfigVo || StringUtils.isBlank(venderConfigVo.getValue())){
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.TIME_SHOW_TYPE);
            throw new RuntimeException("未配置时间显示模式：businessCode="+businessCode);
        }
        return TimeShowTypeEnum.valueOf(venderConfigVo.getValue());
    }
}
