package com.jd.appoint.shop.vo;

import com.jd.appoint.shopapi.vo.ProductVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductConvert {
    public static Product convert2Product(ProductVO productVO){
        if(productVO == null){
            return null;
        }
        Product product = new Product();
        product.setId(productVO.getId());
        product.setName(productVO.getName());
        product.setSkuId(productVO.getSkuId());
        product.setStatus(productVO.getStatus().getValue());
        return product;
    }

    public static List<Product> convert2ProductList(List<ProductVO> productVOList) {
        if(CollectionUtils.isEmpty(productVOList)){
            return new ArrayList<>();
        }
        List<Product> products = new ArrayList<>();

        productVOList.forEach(productVO -> {

            Product product = convert2Product(productVO);
            products.add(product);
        });
        return products;
    }
}
