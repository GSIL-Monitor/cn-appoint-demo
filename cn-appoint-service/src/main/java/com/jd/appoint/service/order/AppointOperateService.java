package com.jd.appoint.service.order;

import com.jd.appoint.vo.dynamic.OperateItemVo;

import java.util.List;
import java.util.Map;

/**
 * 操作配置项
 * Created by gaoxiaoqing on 2018/6/28.
 */
public interface AppointOperateService {
    /**
     * 操作项配置
     * @param businessCode
     * @return
     */
    Map<Integer, List<OperateItemVo>> operateList(String businessCode, Integer platform);
}
