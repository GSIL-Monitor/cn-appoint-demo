package com.jd.appoint.service.api.shop;

import com.google.common.base.Converter;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import com.jd.appoint.common.utils.Copier;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.domain.product.ProductQuery;
import com.jd.appoint.rpc.sku.VscSkuService;
import com.jd.appoint.service.product.ProductService;
import com.jd.appoint.service.api.shop.convert.ProductConverter;
import com.jd.appoint.service.api.shop.convert.ProductPOConverter;
import com.jd.appoint.service.api.shop.convert.VscRequestWrapConverter;
import com.jd.appoint.shopapi.vo.*;
import com.jd.appoint.storeapi.StoreProductFacade;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.vsc.soa.domain.bizconfig.SkuResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 店铺商品管理服务实现
 * Created by yangyuan on 6/15/18.
 */
@Service(value = "storeProductFacade")
public class StoreProductFacadeImpl implements StoreProductFacade {



    @Autowired
    private ProductService productService;

    @Autowired
    private  VscSkuService vscSkuService;


    /**
     * 同步店铺sku数据
     *
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "同步店铺产品数据 ", classify = StoreProductFacadeImpl.class))
    @Override
    public void syncStoreProduct(@ValideGroup SoaRequest<SyncSkuRequest> requestSoaRequest) {
        checkNotNull(requestSoaRequest.getData().getStoreId());
        List<SkuResult> skuResultList = vscSkuService.getSkuList(new VscRequestWrapConverter().convert(requestSoaRequest));
        skuResultList
                .stream()
                .filter(skuResult -> !productService.exist(requestSoaRequest.getData().getVenderId(),
                        requestSoaRequest.getData().getStoreId(),
                        skuResult.getSkuId()))
                .forEach(skuResult -> {
                    ProductPO productPO = new ProductPOConverter().convert(skuResult);
                    productPO.setStoreId(requestSoaRequest.getData().getStoreId());
                    productService.insert(productPO);
                });
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "查询店铺产品数据 ", classify = StoreProductFacadeImpl.class))
    @Override
    public SoaResponse<List<ProductVO>> search(@ValideGroup SoaRequest<ProductQueryVO> productQuery) {
        return new SoaResponse<>(productService
                .queryConditional(new ProductQueryConverter().convert(productQuery.getData()))
                .stream()
                .map(t -> new ProductConverter().convert(t))
                .collect(Collectors.toList()));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "店铺产品计数 ", classify = StoreProductFacadeImpl.class))
    @Override
    public SoaResponse<Integer> totalCount(@ValideGroup SoaRequest<ProductQueryVO> productQuery) {
        return new SoaResponse<>(
                productService.totalCount(
                        new ProductQueryConverter().convert(productQuery.getData())
                )
        );
    }
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "更新店铺sku状态", classify  = StoreProductFacadeImpl.class))
    @Override
    public SoaResponse<Boolean> updateStatus(@ValideGroup SoaRequest<SkuOperateRequest> request) {
        return new SoaResponse<>(productService.update(new ProductPO.Builder(request.getData().getId())
                .storeId(request.getData().getShopId())
                .status(Copier.enumCopy(request.getData().getStatus(), com.jd.appoint.domain.enums.StatusEnum.class))
                .build()));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "删除店铺sku", classify  = StoreProductFacadeImpl.class))
    @Override
    public SoaResponse<Boolean> delete(@ValideGroup SoaRequest<SkuOperateRequest> request) {
        return new SoaResponse<>(productService.update(new ProductPO.Builder(request.getData().getId())
                .storeId(request.getData().getShopId())
                .status(com.jd.appoint.domain.enums.StatusEnum.DELETE)
                .build()));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "获取店铺sku详情", classify  = StoreProductFacadeImpl.class))
    @Override
    public SoaResponse<ProductVO> getOne(@ValideGroup SoaRequest<SkuOperateRequest> request) {
        Optional<ProductPO> optional = Optional.ofNullable(
                productService.queryChecked(request.getData().getId(), request.getData().getShopId(), null));
        return new SoaResponse<>(optional.map(t -> new ProductConverter().convert(t)).orElse(null));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "更新店铺sku", classify  = StoreProductFacadeImpl.class))
    @Override
    public SoaResponse<Boolean> update(@ValideGroup SoaRequest<ProductVO> request){
        return new SoaResponse<>(productService.update(new ProductConverter().reverse().convert(request.getData())));
    }

    private static class ProductQueryConverter extends Converter<ProductQueryVO, ProductQuery> {

        @Override
        protected ProductQuery doForward(ProductQueryVO productQueryVO) {
            ProductQuery productQuery = new ProductQuery();
            if(productQueryVO.getPageNum() == null || productQueryVO.getPageNum() < 1){
                productQueryVO.setPageNum(1);
            }
            if(productQueryVO.getPageSize() == null ||productQueryVO.getPageSize() < 1){
                productQueryVO.setPageSize(20);
            }
            BeanUtils.copyProperties(productQueryVO, productQuery);
            productQuery.setStoreId(productQueryVO.getShopId());
            productQuery.setOffset((productQueryVO.getPageNum() - 1)*productQueryVO.getPageSize());
            return productQuery;
        }

        @Override
        protected ProductQueryVO doBackward(ProductQuery productQuery) {
            return null;
        }
    }


}
