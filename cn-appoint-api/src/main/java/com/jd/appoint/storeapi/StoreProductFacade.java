package com.jd.appoint.storeapi;

import com.jd.appoint.shopapi.vo.ProductQueryVO;
import com.jd.appoint.shopapi.vo.ProductVO;
import com.jd.appoint.shopapi.vo.SkuOperateRequest;
import com.jd.appoint.shopapi.vo.SyncSkuRequest;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.ValideGroup;

import java.util.List;

/**
 * Created by yangyuan on 7/2/18.
 */
public interface StoreProductFacade {

    /**
     * 同步店铺产品数据
     * @return
     */
    void syncStoreProduct(@ValideGroup SoaRequest<SyncSkuRequest> requestSoaRequest);

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
