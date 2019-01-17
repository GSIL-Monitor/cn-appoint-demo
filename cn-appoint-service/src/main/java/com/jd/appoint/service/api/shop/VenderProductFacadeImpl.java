package com.jd.appoint.service.api.shop;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.jd.appoint.common.utils.Copier;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.domain.product.ProductQuery;
import com.jd.appoint.rpc.sku.VscSkuService;
import com.jd.appoint.service.product.ProductService;
import com.jd.appoint.service.api.shop.convert.ProductConverter;
import com.jd.appoint.service.api.shop.convert.ProductPOConverter;
import com.jd.appoint.service.api.shop.convert.VscRequestWrapConverter;
import com.jd.appoint.shopapi.ShopProductFacade;
import com.jd.appoint.shopapi.vo.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.travel.validate.BeanValidators;
import com.jd.vsc.soa.domain.bizconfig.SkuResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 商家商品服务实现
 * Created by yangyuan on 6/15/18.
 */
@Service(value = "venderProductFacade")
public class VenderProductFacadeImpl implements ShopProductFacade {

    @Autowired
    private ProductService productService;

    @Autowired
    private VscSkuService vscSkuService;

    @Resource
    private BeanValidators beanValidators;

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "商家端sku同步", classify = VenderProductFacadeImpl.class))
    @Override
    public void syncProduct(@ValideGroup  SoaRequest<SyncSkuRequest> requestSoaRequest) {
        List<SkuResult> skuResultList = vscSkuService.getSkuList(new VscRequestWrapConverter().convert(requestSoaRequest));
        skuResultList
                .stream()
                .filter(skuResult -> !productService.exist(requestSoaRequest.getData().getVenderId(), -1l, skuResult.getSkuId()))
                .forEach(skuResult -> productService.insert(new ProductPOConverter().convert(skuResult)));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "商家查询接口", classify = VenderProductFacadeImpl.class))
    @Override
    public SoaResponse<List<ProductVO>> search(@ValideGroup SoaRequest<ProductQueryVO> productQuery) {
        return new SoaResponse<>(productService
                .queryConditional(new ProductQueryConverter().convert(productQuery.getData()))
                .stream()
                .map(t -> new ProductConverter().convert(t))
                .collect(Collectors.toList()));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "商家查询计数接口", classify = VenderProductFacadeImpl.class))
    @Override
    public SoaResponse<Integer> totalCount(@ValideGroup SoaRequest<ProductQueryVO> productQuery) {
        return new SoaResponse<>(productService.totalCount(new ProductQueryConverter().convert(productQuery.getData())));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "更新商家sku状态", classify = VenderProductFacadeImpl.class))
    @Override
    public SoaResponse<Boolean> updateStatus(@ValideGroup SoaRequest<SkuOperateRequest> request) {//Long shopId, Long id, StatusEnum status
        return new SoaResponse<>(productService.update(new ProductPO.Builder(request.getData().getId())
                .venderId(request.getData().getShopId())
                .status(Copier.enumCopy(request.getData().getStatus(), com.jd.appoint.domain.enums.StatusEnum.class))
                .build()));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "删除商家sku", classify = VenderProductFacadeImpl.class))
    @Override
    public SoaResponse<Boolean> delete(@ValideGroup SoaRequest<SkuOperateRequest> request) {
        return new SoaResponse<>(productService.update(new ProductPO.Builder(request.getData().getId())
                .venderId(request.getData().getShopId())
                .status(com.jd.appoint.domain.enums.StatusEnum.DELETE)
                .build()));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "获取商家sku详情", classify = VenderProductFacadeImpl.class))
    @Override
    public SoaResponse<ProductVO> getOne(@ValideGroup SoaRequest<SkuOperateRequest> request) {
        Optional<ProductPO> optional = Optional.ofNullable(productService
                .queryChecked(request.getData().getId(), null, request.getData().getShopId()));
        return new SoaResponse<>(optional.map(t -> new ProductConverter().convert(t)).orElse(null));
    }

    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "更新商家sku", classify  = StoreProductFacadeImpl.class))
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
            if(productQueryVO.getPageSize() == null || productQueryVO.getPageSize() < 1){
                productQueryVO.setPageSize(20);
            }
            BeanUtils.copyProperties(productQueryVO, productQuery);
            productQuery.setVenderId(productQueryVO.getShopId());
            productQuery.setStoreId(-1l);
            productQuery.setOffset((productQueryVO.getPageNum() - 1)*productQueryVO.getPageSize());
            return productQuery;
        }

        @Override
        protected ProductQueryVO doBackward(ProductQuery productQuery) {
            return null;
        }
    }

}
