package com.jd.appoint.service.api.shop.convert;

import com.google.common.base.Converter;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.vsc.soa.domain.bizconfig.SkuResult;

/**
 * Created by yangyuan on 6/20/18.
 */
public class ProductPOConverter extends Converter<SkuResult, ProductPO> {

    @Override
    protected ProductPO doForward(SkuResult skuResult) {
        ProductPO productPO = new ProductPO();
        productPO.setStatus(com.jd.appoint.domain.enums.StatusEnum.ENABLE);
        productPO.setName(skuResult.getSkuName());
        productPO.setSkuId(skuResult.getSkuId());
        productPO.setBusinessCode("" + skuResult.getBusinessType());
        productPO.setVenderId(Long.parseLong(skuResult.getMerchantCode()));
        return productPO;
    }

    @Override
    protected SkuResult doBackward(ProductPO productPO) {
        return null;
    }
}
