package com.jd.appoint.shopapi;

import com.jd.appoint.shopapi.vo.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.validate.ValidatorException;

import java.util.List;

/**
 * Created by yangyuan on 6/15/18.
 */
public interface ShopProductFacade {

    /**
     * 同步产品数据
     * @return
     */

    void syncProduct(@ValideGroup SoaRequest<SyncSkuRequest> requestSoaRequest);

    /**
     * 条件查询
     * @param productQuery
     * @return
     */
    SoaResponse<List<ProductVO>> search(@ValideGroup SoaRequest<ProductQueryVO> productQuery) ;

    /**
     * 条件查询获取总数
     * @param productQuery
     * @return
     */
    SoaResponse<Integer> totalCount(@ValideGroup SoaRequest<ProductQueryVO> productQuery) ;

    /**
     * 更新状态
     * @return
     */
    SoaResponse<Boolean> updateStatus(@ValideGroup SoaRequest<SkuOperateRequest> request);

    /**
     * 删除产品
     * @return
     */
    SoaResponse<Boolean> delete(@ValideGroup SoaRequest<SkuOperateRequest> request);

    /**
     * 更新产品数据
     * @param request
     * @return
     */
    SoaResponse<Boolean> update(@ValideGroup SoaRequest<ProductVO> request);

    /**
     * 获取产品详情，shopId和id一起校验合法性
     * @return
     */
    SoaResponse<ProductVO> getOne(@ValideGroup SoaRequest<SkuOperateRequest> request);
}
