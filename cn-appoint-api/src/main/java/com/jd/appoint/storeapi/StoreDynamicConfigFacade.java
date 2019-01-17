package com.jd.appoint.storeapi;

import com.jd.appoint.vo.dynamic.FilterItemVO;
import com.jd.appoint.vo.dynamic.OperateItemVo;
import com.jd.appoint.vo.dynamic.ServerTypeRequest;
import com.jd.appoint.vo.dynamic.ServerTypeVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public interface StoreDynamicConfigFacade {
    /**
     * 获取服务类型
     *
     * @param businessCode
     * @return
     */
    SoaResponse<List<ServerTypeVO>> serverTypeList(SoaRequest<String> businessCode);

    /**
     * 获取筛选项
     *
     * @param serverTypeRequest
     * @return
     */
    SoaResponse<List<FilterItemVO>> filterItemList(SoaRequest<ServerTypeRequest> serverTypeRequest);

    /**
     * 获取批量操作
     *
     * @param serverTypeRequest
     * @return
     */
    SoaResponse<List<OperateItemVo>> batchOperateList(SoaRequest<ServerTypeRequest> serverTypeRequest);

    /**
     * 获取详情操作
     *
     * @param request 预约单id
     * @return
     */
    SoaResponse<List<OperateItemVo>> detailOperateList(SoaRequest<Long> request);
}
