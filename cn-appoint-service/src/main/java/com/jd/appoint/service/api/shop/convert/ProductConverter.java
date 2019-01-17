package com.jd.appoint.service.api.shop.convert;

import com.google.common.base.Converter;
import com.jd.appoint.common.utils.Copier;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.shopapi.vo.ProductVO;
import com.jd.appoint.shopapi.vo.StatusEnum;
import org.springframework.beans.BeanUtils;

/**
 * Created by yangyuan on 6/15/18.
 */
public class ProductConverter extends Converter<ProductPO, ProductVO> {

    @Override
    protected ProductVO doForward(ProductPO productPO) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(productPO, productVO);
        if(productPO.getStatus() != null) {
            productVO.setStatus(Copier.enumCopy(productPO.getStatus(), StatusEnum.class));
        }
        return productVO;
    }

    @Override
    protected ProductPO doBackward(ProductVO productVO) {
        ProductPO productPO = new ProductPO();
        BeanUtils.copyProperties(productVO, productPO);
        if(productVO.getStatus() != null) {
            productPO.setStatus(Copier.enumCopy(productVO.getStatus(), com.jd.appoint.domain.enums.StatusEnum.class));
        }
        return productPO;
    }
}