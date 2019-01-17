package com.jd.appoint.service.order;

import com.jd.appoint.domain.rpc.ServerTypeInfo;
import com.jd.appoint.domain.rpc.query.MappingStatusQuery;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.appoint.vo.order.OrderFieldVO;

import java.util.List;

/**
 * Created by gaoxiaoqing on 2018/5/4.
 */
public interface PopConfigService {

    /**
     * 根据业务获取所有映射
     * @param businessCode
     * @return
     */
     List<SystemStatusInfo> queryStatusMappingList(String businessCode);

    /**
     * 获取指定系统状态
     * @param mappingStatusQuery
     * @return
     */
    String querySystemStatus(MappingStatusQuery mappingStatusQuery);

    /**
     * 获取指定服务名称
     * @param businessCode
     * @param serverType
     * @return
     */
    String getServerTypeName(String businessCode,int serverType);

    /***
     * 根据业务获取服务类型
     * @param businessCode
     * @return
     */
    ServerTypeInfo getServerInfo(String businessCode);

    /***
     * 获取列表导出配置
     * @return
     */
    List<OrderFieldVO> getExportOrderSet(String businessCode);
}
